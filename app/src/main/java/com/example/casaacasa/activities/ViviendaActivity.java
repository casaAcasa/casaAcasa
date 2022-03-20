package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Valoracion;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Estado;
import com.example.casaacasa.utils.TipoValoracion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
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
         * MIRAR PORQUE NO ME PASAN EL TEXTO LAS VALORACIONES!!!
         *  Enviar solicitud
         *  Valorar
         *  Ajustar tamaño imagenes
         *  Botones cambiar valoraciones
         *  También hace falta poner condicionantes a los botones para que no se puedan realizar según las condiciones
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
        //Aquí tendré que poner el uid de la vivienda que haya seleccionado
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

        LayoutInflater layoutInflater = LayoutInflater.from(ViviendaActivity.this);

        View view = layoutInflater.inflate(R.layout.popup_valorar, null);
        dialog.setView(view);

        dialog.setPositiveButton("SIGUIENTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et=view.findViewById(R.id.mensajeValoracion);
                RatingBar rb=view.findViewById(R.id.rbpopup);

                Valoracion v=new Valoracion("c68c97e6-4233-43cf-88cb-d6e2a8481a09",
                        vivienda.getUser_id(), TipoValoracion.ANFITRION,
                        et.getText().toString(), rb.getRating());
                MainActivity.db.child("Valoracion").child(v.getUid()).setValue(v);

                AlertDialog.Builder dialog2= new AlertDialog.Builder(ViviendaActivity.this);
                dialog2.setTitle("Vas a valorar el comportamiento de un usuario en tu vivienda");

                LayoutInflater layoutInflater = LayoutInflater.from(ViviendaActivity.this);

                View view = layoutInflater.inflate(R.layout.popup_valorar, null);
                dialog2.setView(view);

                dialog2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et=view.findViewById(R.id.mensajeValoracion);
                        RatingBar rb=view.findViewById(R.id.rbpopup);

                        Valoracion v=new Valoracion("c68c97e6-4233-43cf-88cb-d6e2a8481a09",
                                vivienda.getUser_id(), TipoValoracion.ANFITRION,
                                et.getText().toString(), rb.getRating());
                        MainActivity.db.child("Valoracion").child(v.getUid()).setValue(v);

                        Toast.makeText(ViviendaActivity.this, "Valocarión enviada", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViviendaActivity.this, "La valoración no se ha enviado", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                dialog2.show();
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "La valoración no se ha enviado", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void solicitud(View _) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas ha enviar una solicitud de intercambio");
        LayoutInflater layoutInflater = LayoutInflater.from(ViviendaActivity.this);

        //Hay que crear el view para luego acceder a su contenido
        View view = layoutInflater.inflate(R.layout.popup_enviar_solicitud, null);
        dialog.setView(view);

        dialog.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText=(EditText) view.findViewById(R.id.contMensaje);

                Toast.makeText(ViviendaActivity.this, "Solicitud enviada"
                        , Toast.LENGTH_SHORT).show();
                //Emisor=Usuario logueado
                //Receptor=Usuario al que el logueado visita
                Solicitud s=new Solicitud("26a08f75-5967-434d-a283-a8b60e70135a",
                        vivienda.getUser_id(), Estado.PENDIENTE, editText.getText().toString());
                MainActivity.db.child("Solicitud").child(s.getUid()).setValue(s);
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "La solicitud no se ha enviado", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void anadirValoraciones(LayoutInflater inflater) {
       Query query= MainActivity.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id());
       query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout valorations=findViewById(R.id.valocinesList);
                for(DataSnapshot v:snapshot.getChildren()){
                    //Mirar si lo pilla bien antes de hacer el código.
                    Valoracion vo=v.getValue(Valoracion.class);
                    if(vo.getTipo().equals(TipoValoracion.ANFITRION)){
                        View view=inflater.inflate(R.layout.valoracion, valorations,false);
                        ImageView imageView=view.findViewById(R.id.imageView2);

                        imageView.setImageResource(R.drawable.ic_launcher_background);
                        valorations.addView(view);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
