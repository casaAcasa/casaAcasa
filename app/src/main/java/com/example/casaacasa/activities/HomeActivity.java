package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.casaacasa.R;

enum ProviderType{
    BASIC
}


public class HomeActivity extends AppCompatActivity {
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startIntent=getIntent();

        darTextoALasViews();
    }

    private void darTextoALasViews() {
        TextView nombre=findViewById(R.id.nombreRegistradoEjemplo);
        nombre.setText("Enorabuena "+startIntent.getStringExtra("name")+" "+startIntent.getStringExtra("surname")+", te has registrado exitosamente");

        Button entrarEnAplicacion=findViewById(R.id.goPerfilButton);
        entrarEnAplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, BusquedaActivity.class);
                startActivity(intent);
            }
        });
    }
}