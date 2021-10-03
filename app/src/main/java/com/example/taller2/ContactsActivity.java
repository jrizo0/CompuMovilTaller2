package com.example.taller2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.taller2.databinding.ActivityContactsBinding;

import java.util.logging.Logger;

public class ContactsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityContactsBinding binding;

    String [] mProjection;
    Cursor mCursor;
    ContactsAdapter mContactsAdapter;
    ListView mContactListView;
    String permContacts = Manifest.permission.READ_CONTACTS;

    private static final int CONTACTS_PERM_ID = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mContactListView = findViewById(R.id.contactList);

        mProjection = new String[]{
                ContactsContract.Profile._ID,
                ContactsContract.Profile.DISPLAY_NAME_PRIMARY
        };
        //el cursor esta en null hasta que tengamos el permiso
        mContactsAdapter = new ContactsAdapter(this, null, 0);
        mContactListView.setAdapter(mContactsAdapter);

        requestPermissions(this,
                permContacts,
                "Contacts are required to display the list",
                CONTACTS_PERM_ID);


        initView();

    }

    private void requestPermissions(Activity context, String permission, String justification, int id) {
        if(ContextCompat.checkSelfPermission(context, permission)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(context, justification, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String []{permission}, id);
        }
    }

    private void initView(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){
            mCursor = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI,
                    mProjection,
                    null,
                    null,
                    null);
            mContactsAdapter.changeCursor(mCursor);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults  ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACTS_PERM_ID){
            initView();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_contacts);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}