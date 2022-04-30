package com.example.casaacasa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviarSolicitud;
    private Button btnValorar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);

        LinearLayout gallery = findViewById(R.id.gallery);
        //Hacer los overlays de cada boton
        //Tengo que ajustar los bordes del xml
        //Tengo que meter en layout los textos para que queden bien
        //Meter rayitas de separación para que se vea bien
        //Mirar lo que ahce le inflater
        //Ultima estrellita de las valoraciones
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i=0; i<6; i++){
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            TextView t= view.findViewById(R.id.text);
            t.setText("item"+i);
            ImageView imageView=view.findViewById(R.id.imageView);
            imageView.setImageResource(R.mipmap.ic_launcher);
            gallery.addView(view);
        }


        LinearLayout valorations=findViewById(R.id.valocinesList);

        for (int i=0; i<6;i++){
            View view=inflater.inflate(R.layout.valoracion, valorations,false);
            ImageView imageView=view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.ic_launcher_background);
            valorations.addView(view);
        }

        btnEnviarSolicitud=findViewById(R.id.enviarSolicitud); //A lo mejor no es esta

        btnEnviarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Vas ha enviar una solicitud de intercambio");
                dialog.setView(R.layout.popup_enviar_solicitud);

                dialog.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
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
        });

        btnValorar=findViewById(R.id.btnValorar);
        btnValorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hola");
                Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT);
                AlertDialog.Builder myDçdialog= new AlertDialog.Builder(MainActivity.this);
                myDçdialog.setTitle("Vas a valorar la vivienda de un usuario");
                myDçdialog.setView(R.layout.popup_valorar);


                myDçdialog.setPositiveButton("SIGUIENTE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                        System.out.println("Hola");
                        Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT);
                        AlertDialog.Builder myDçdialog= new AlertDialog.Builder(MainActivity.this);
                        myDçdialog.setTitle("Vas a valorar el comportamiento de un usuario en tu vivienda");
                        myDçdialog.setView(R.layout.popup_valorar);
                        /*RatingBar rb= findViewById(R.id.rbpopup);
                        rb.setRating(5);*/

                        myDçdialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                            }
                        });

                        myDçdialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        myDçdialog.show();
                    }
                });

                myDçdialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                myDçdialog.show();
            }
        });
    }
}