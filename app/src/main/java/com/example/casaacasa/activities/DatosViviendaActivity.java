package com.example.casaacasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.casaacasa.R;

public class DatosViviendaActivity extends AppCompatActivity  {
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_vivienda);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater = LayoutInflater.from(DatosViviendaActivity.this);
    }

    public void volverAtras(View v){
        Intent intent=new Intent(DatosViviendaActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

}
