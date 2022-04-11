package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.casaacasa.R;

import java.util.zip.Inflater;

public class BusquedaActivity extends AppCompatActivity {
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
//Seguir con el xml ded filtros
        inflater= LayoutInflater.from(BusquedaActivity.this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.gallery);
        //TODO Podria hacer una tabla de dos columnas en vez del Linear layout. El número de filas seria = al número de viviendas / entre columnas
        // f=v/c; Redondeado al mayor, 12,3==13;
        //TODO Buscar como hacer una tabla variable
        //TODO El layout de buscar se ve mal, el de la lupa me refiero.
        // No se ve el contenido de la descripciopn
        for(int i=0; i<6; i++){
            View v= inflater.inflate(R.layout.vivienda_busqueda, linearLayout, false);
            TextView nombre= v.findViewById(R.id.vbNombreUsuario);
            TextView descripcion=v.findViewById(R.id.vbDescripcion);
            TextView datosVivienda=v.findViewById(R.id.vbDatosVivienda);

            nombre.setText("PRUEBA");
            descripcion.setText("Descripcion de prueba");
            datosVivienda.setText("Datos de prueba");
            linearLayout.addView(v);
        }
        //TODO para los botones de filtro poner interruptores en los botones para qube si unmo está pulsado no se pueda pulsar el otro y biceversa
    }
}