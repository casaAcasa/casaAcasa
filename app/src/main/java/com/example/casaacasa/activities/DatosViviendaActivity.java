package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatosViviendaActivity extends AppCompatActivity {
    private Intent startIntent;
    private Vivienda vivienda;
    private LayoutInflater inflater;
    private ArrayList<String> tiposViviendas;
    private ArrayList<String> tiposPoblaciones;
    private ArrayAdapter adpTV;
    private ArrayAdapter adpTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_vivienda);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflater = LayoutInflater.from(DatosViviendaActivity.this);
        setContentView(R.layout.activity_datos_vivienda);

        startIntent=getIntent();

        tiposViviendas=new ArrayList<String>();
        tiposViviendas.add("Casa");
        tiposViviendas.add("Piso");
        tiposViviendas.add("Apartamento");

        tiposPoblaciones=new ArrayList<String>();
        tiposPoblaciones.add("Ciudad");
        tiposPoblaciones.add("Pueblo");

        adpTV=new ArrayAdapter(DatosViviendaActivity.this, R.layout.support_simple_spinner_dropdown_item, tiposViviendas);
        adpTP=new ArrayAdapter(DatosViviendaActivity.this, R.layout.support_simple_spinner_dropdown_item, tiposPoblaciones);

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
        direccion.setText(vivienda.getDireccionExacta());
        Spinner spinnerTV=findViewById(R.id.tipoDeVivienda);
        spinnerTV.setAdapter(adpTV);
        boolean det=false;
        for(int i=0; i<tiposViviendas.size()&&!det; i++){
            if(vivienda.getTipoVivienda().toLowerCase().replace(".","").equals(tiposViviendas.get(i).toLowerCase())){
                spinnerTV.setSelection(i);
                det=true;
            }
        }

        Spinner spinnerTP=findViewById(R.id.tipoDePoblacion);
        spinnerTP.setAdapter(adpTP);
        det=false;
        for(int i=0; i<tiposPoblaciones.size()&&!det; i++){
            if(vivienda.getTipoPoblacion().toLowerCase().replace(".","").equals(tiposPoblaciones.get(i).toLowerCase())){
                spinnerTP.setSelection(i);
                det=true;
            }
        }



        EditText poblacion=findViewById(R.id.poblacion);
        poblacion.setText(vivienda.getPoblacion());
        EditText numH=findViewById(R.id.nHabPerf);
        numH.setText(String.valueOf(vivienda.getNumHabitaciones()));
        EditText m2=findViewById(R.id.m2Perf);
        m2.setText(String.valueOf(vivienda.getMetrosCuadrados()));
        Log.i("TAG", vivienda.getPoblacion()+" "+vivienda.getNumHabitaciones()+" "+vivienda.getMetrosCuadrados());

        Button guardarCambios=findViewById(R.id.guardarDatosVivienda);
        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("direccionExacta").setValue(direccion.getText().toString());
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("poblacion").setValue(poblacion.getText().toString());
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("tipoVivienda").setValue(spinnerTV.getSelectedItem().toString()+".");
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("tipoPoblacion").setValue(spinnerTP.getSelectedItem().toString()+".");
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("numHabitaciones").setValue(Integer.parseInt(numH.getText().toString()));
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("metrosCuadrados").setValue(Integer.parseInt(m2.getText().toString()));
                Toast.makeText(DatosViviendaActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //TODO Llamar al onClick de guardar datos de la vivienda y enviar los cambios al firebase
    // Recoger lo que hay en firebase, si es que hay algo, y ponerlo en sus layouts
    // Me falta lo de cambiar datos de usuario, que debe estar en el menú de opciones
    // Me falta el verificado, que debe estar en el menú de opciones
    // si es que hay algo, hACERLO TAMBIEN EN PERFIL (Los comprobantes me refiero)

    //TODO Otras pantallas:
    // Me falta que no se puedan enviar más de un intercambio si aún no se ha contestado o la respuesta es afirmatíva
    // Que cuando se vaya para atras en las pantallas con tabBar se salga de la aplicación
}