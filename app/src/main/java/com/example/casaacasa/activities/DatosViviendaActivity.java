package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DatosViviendaActivity extends AppCompatActivity {
    private Intent startIntent;
    private Vivienda vivienda;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_vivienda);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater = LayoutInflater.from(DatosViviendaActivity.this);
        setContentView(R.layout.activity_datos_vivienda);

        startIntent=getIntent();

        recogerInformacionBBDD();
    }
    public void volverAtras(View v){
        Intent intent=new Intent(DatosViviendaActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    private void recogerInformacionBBDD() {
        Constantes.db.child("Vivienda").child(startIntent.getStringExtra("ViviendaID")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vivienda=snapshot.getValue(Vivienda.class);
                darTextoViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void darTextoViews() {
        EditText direccion=findViewById(R.id.direccion);
        direccion.setText(vivienda.getDescripcion());
        //TODO Tipo vivienda
        // Tipo poblacion
        EditText poblacion=findViewById(R.id.poblacion);
        direccion.setText(vivienda.getPoblacion());
        EditText numH=findViewById(R.id.nHabPerf);
        direccion.setText(String.valueOf(vivienda.getNumHabitaciones()));
        EditText m2=findViewById(R.id.m2Perf);
        direccion.setText(String.valueOf(vivienda.getMetrosCuadrados()));

        Button guardarCambios=findViewById(R.id.guardarDatosVivienda);
        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("direccionExacta").setValue(direccion.getText().toString());
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("polacion").setValue(poblacion.getText().toString());
                //TODO tipoVivienda
                // tipoPoblacion
                // Acordarme de poner un punto en estas dos al final
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("numHabitaciones").setValue(numH.getText().toString());
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("metrosCuadrados").setValue(m2.getText().toString());
            }
        });
    }


    //TODO Llamar al onClick de guardar datos de la vivienda y enviar los cambios al firebase
    // Recoger lo que hay en firebase, si es que hay algo, y ponerlo en sus layouts
    // Me falta lo de cambiar datos de usuario, que debe estar en el menú de opciones
    // Me falta el verificado, que debe estar en el menú de opciones
    // si es que hay algo, hACERLO TAMBIEN EN PERFIL
    // HACER IF( PARA COMPROBAR SI LOS CAMPOS ESTAN VACIOS Y ASI NO HACER EL INTENT)
}