package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ViviendaActivity extends AppCompatActivity {

    private Vivienda vivienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        /**
         * Recoger por el inent el UUID de la vivienda que se ha pulsado
         * Me falta:
         *  Enviar solicitud
         *  Valorar
         *  Ajustar tamaño imagenes
         *  Botones cambiar valoraciones
         */
        recogerInformacionVivienda();

    }

    private void darTextoALasViews() {
        MainActivity.db.child("Usuario")
                .child(vivienda.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u=snapshot.getValue(Usuario.class);

                String n=u.getNombreUsuario();
                Log.i("TAG",u.toString());
                TextView nombreUsuario= findViewById(R.id.NomUsu);
                nombreUsuario.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView datosVivienda= findViewById(R.id.poblacion);
        datosVivienda.setText(vivienda.getDireccion()+", "+vivienda.getMetrosCuadrados()+", "+vivienda.getMetrosCuadrados());

        TextView descripcion= findViewById(R.id.contentDescripción);
        descripcion.setText(vivienda.getDescripcion());

        TextView normas=findViewById(R.id.contentNormas);
        normas.setText(getArraySringEnString(vivienda.getNormas()));
        TextView servicios=findViewById(R.id.contentServicios);
        servicios.setText(getArraySringEnString(vivienda.getServicios()));


    }

    private String getArraySringEnString(ArrayList<String> array) {
        String lista="";
        for (int i=0; i< array.size(); i++){
            if(i==array.size()){
                lista+=array.get(i);
            } else{
                lista+=array.get(i)+"\n";
            }
        }
        return lista;
    }

    private void recogerInformacionVivienda() {
        MainActivity.db.child("Vivienda")
                .child("b9d6e172-dbec-4593-ac1c-968985d5760d").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vivienda=snapshot.getValue(Vivienda.class);
                darTextoALasViews();
                LayoutInflater inflater = LayoutInflater.from(ViviendaActivity.this);
                anadirImagenes(inflater);
                anadirValoraciones(inflater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        for (int i=0; i<vivienda.getImagenes().size(); i++){
            StorageReference ruta=MainActivity.storageRef.child(vivienda.getImagenes().get(i));
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            ImageView imageView=view.findViewById(R.id.imageView);

            final long ONE_MEGABYTE = 1024 * 1024;
            ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    //Pasar de bytes a ImageView
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                    gallery.addView(view);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }
}
