package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.casaacasa.R;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }/*
    //Perfil
    public void irPerfil(View view) {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }
    //Chat
    public void irChat(View view) {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
    //Busqueda
    public void irBusqueda(View view) {
        Intent intent = new Intent(this, Busqueda.class);
        startActivity(intent);
    }
    //Quedadas
    public void irQuedadas(View view) {
        Intent intent = new Intent(this, Quedadas.class);
        startActivity(intent);
    }
    //Map
    public void irMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }*/
}

//TODO : A;adir scroll y dentro el linearlayout para agregar la valoracion
//TODO : PENDIENTE LOGICA DYLAN + OSCAR
//TODO : En clase hablar de los colores para asignarlos en xml colors