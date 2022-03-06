package com.example.casaacasa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ViviendaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        LayoutInflater inflater = LayoutInflater.from(this);
        anadirImagenes(inflater);
        anadirValoraciones(inflater);
        MainActivity.db.child("prueba").setValue("Funciona");
    }

    public void valorar(View _){
        System.out.println("Hola");
        Toast.makeText(ViviendaActivity.this, "hola", Toast.LENGTH_SHORT);
        AlertDialog.Builder dialog= new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas a valorar la vivienda de un usuario");
        dialog.setView(R.layout.popup_valorar);


        dialog.setPositiveButton("SIGUIENTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "hola", Toast.LENGTH_SHORT);
                AlertDialog.Builder dialog2= new AlertDialog.Builder(ViviendaActivity.this);
                dialog2.setTitle("Vas a valorar el comportamiento de un usuario en tu vivienda");
                dialog2.setView(R.layout.popup_valorar);

                dialog2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViviendaActivity.this, "Valocarión enviada", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog2.show();
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void solicitud(View _) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas ha enviar una solicitud de intercambio");
        dialog.setView(R.layout.popup_enviar_solicitud);

        dialog.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void anadirValoraciones(LayoutInflater inflater) {
        LinearLayout valorations=findViewById(R.id.valocinesList);
        for (int i=0; i<6;i++){
            View view=inflater.inflate(R.layout.valoracion, valorations,false);
            ImageView imageView=view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.ic_launcher_background);
            valorations.addView(view);
        }
    }

    private void anadirImagenes(LayoutInflater inflater) {
        LinearLayout gallery = findViewById(R.id.gallery);
        for (int i=0; i<6; i++){
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            TextView t= view.findViewById(R.id.text);
            t.setText("item"+i);
            ImageView imageView=view.findViewById(R.id.imageView);
            imageView.setImageResource(R.mipmap.ic_launcher);
            gallery.addView(view);
        }
    }
}
