package com.example.taller2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class CamaraActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Logger logger = Logger.getLogger(TAG);
    //Definir el id del permiso
    private static final int CAMARA_PERMISO_ID = 101;
    private final int GALLERY_PERMISSION_ID = 102;
    String camaraPerm = Manifest.permission.CAMERA;

    ImageView imagen;
    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        pedirPermisos(this, camaraPerm, "Permiso para utilizar la camara", CAMARA_PERMISO_ID);
        imagen = findViewById(R.id.imagenMostrar);
    }

    private void pedirPermisos(Activity context, String permiso, String justificacion, int id){
        if (ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(context, justificacion, Toast.LENGTH_SHORT).show();
            }
            // request the permission.
            ActivityCompat.requestPermissions(context, new String[]{permiso}, id);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case CAMARA_PERMISO_ID:
                    Bundle extras = data.getExtras();
                    Bitmap imgBit = (Bitmap) extras.get("data");
                    imagen.setImageBitmap(imgBit);
                    //imagen.setImageURI(Uri.parse(currentPhotoPath));
                    logger.info("Image capture successfully.");
                    break;
                case GALLERY_PERMISSION_ID:
                    Uri imageUri = data.getData();
                    imagen.setImageURI(imageUri);
                    logger.info("Image loaded successfully.");
                    break;
            }

        }
    }


    public void abrirGaleria(View view) {
        Intent pickGalleryImage = new Intent(Intent.ACTION_PICK);
        pickGalleryImage.setType("image/*");
        startActivityForResult(pickGalleryImage, GALLERY_PERMISSION_ID);
    }

    public void abrirCamara(View view) {
        if (ContextCompat.checkSelfPermission(this, camaraPerm)
                == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMARA_PERMISO_ID);
        }else {
            logger.warning("Failed to getting the permission :(");
        }

    }

}