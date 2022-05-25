package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.casaacasa.R;


public class CambioDatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_datos);
        // cambioar password
        cambiarPassword();
    }


    //POSIBLE DESUSO DE ESTE INTENT
    private void cambiarPassword() {
        Intent changePsswdIntent = new Intent(CambioDatosActivity.this, ChangePsswdActivity.class);
        startActivity(changePsswdIntent);
    }

}