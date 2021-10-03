package com.example.taller2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.taller2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*
    public void eventCamara(View view) {
        Intent intent = new Intent(this, .class);
        startActivity(intent);
    }*/

    public void eventContactos(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void eventMapa(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}