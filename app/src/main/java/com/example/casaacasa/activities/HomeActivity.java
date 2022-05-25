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

// TODO Quitar el boton de cerrar sesion, y no se si quitar esta página te¡ambien, proque no sirve de mucho

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
        TextView email=findViewById(R.id.homeEmailTextView);
        email.setText(startIntent.getStringExtra("email"));
        TextView proveedor=findViewById(R.id.homeproviderTextView);
        proveedor.setText(startIntent.getStringExtra("provider"));
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