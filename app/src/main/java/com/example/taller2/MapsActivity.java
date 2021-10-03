package com.example.taller2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationRequest;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.taller2.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    double latitud;
    double longitud;
    TextView txtLat;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    String permLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERM_ID = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mLocationRequest = createLocationRequest();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(this,
                    permLocation,
                    "Location is required",
                    LOCATION_PERM_ID);
            return;
        }


        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("LOCATION", "onSuccess location");
                if (location != null) {
                    Log.i("LOCATION ", "Longitud: " + location.getLongitude());
                    latitud = location.getLatitude();
                    Log.i("LOCATION ", "Latitud: " + location.getLatitude());
                    longitud = location.getLongitude();
                }
            }
        });


        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //setUpMapIfNeeded();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults  ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERM_ID){
            reiniciarActivity(this);
        }
    }

    private void requestPermissions(Activity context, String permission, String justification, int id) {
        if(ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(context, justification, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String []{permission}, id);
            //reiniciarActivity(this);
        }
    }

    public static void reiniciarActivity(Activity actividad){
        Intent intent=new Intent();
        intent.setClass(actividad, actividad.getClass());
        actividad.startActivity(intent);
        actividad.finish();
    }

    /*
    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest. PRIORITY_HIGH_ACCUARCY);
        return locationRequest;
    }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if(latitud == 0 && longitud == 0){
            reiniciarActivity(this);
        }
        LatLng posActual = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(posActual).title("Marcador en posicion actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posActual));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latitud = location.getLatitude() ;
            longitud = location.getLongitude();
        }
    };

    /*
    private void setUpMapIfNeeded() {
        Integer permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck.equals(PackageManager.PERMISSION_GRANTED)){
            Logger.getGlobal().info("Success granting the permission :)");
        }
        else if(permissionCheck.equals(PackageManager.PERMISSION_DENIED)){
            Logger.getGlobal().info("Failed granting the permission :(");
        }
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setOnMyLocationChangeListener(new GoogleMap.
                        OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    }
                });
            }
        }

    }
    */


    private boolean tengoPermisos(){
        //pedir permiso
        Integer permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck.equals(PackageManager.PERMISSION_GRANTED)){
            Logger.getGlobal().info("Success granting the permission :)");
            return true;
        } else if(permissionCheck.equals(PackageManager.PERMISSION_DENIED)){
            Logger.getGlobal().info("Failed granting the permission :(");
            return false;
        }
        return false;
    }
}