package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.Estado;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private String IDUsuarioLogueado;

    //TODO Hacer que las listas esén ordenadas sengun la fecha del último mensaje enviado o recivido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        inflater = LayoutInflater.from(ChatActivity.this);

        IDUsuarioLogueado=Constantes.getIdUsuarioLogueado();

        listadoDeConversacionesSolicitudesRecibidas();
    }

    public void paginaSolicitudes(View v){
        Intent intent = new Intent(this, SolicitudActivity.class );
        startActivity(intent);
    }


    public void conversar(View v, String receptorOEmisor){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(ChatActivity.this, MensajeActivity.class);
               intent.putExtra("UsuarioContrario", receptorOEmisor);
               startActivity(intent);
            }
        });
    }

    private void listadoDeConversacionesSolicitudesRecibidas(){
        Query query = Constantes.db.child("Solicitud").orderByChild("receptor").equalTo(IDUsuarioLogueado);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaChats);
                linearLayout.removeAllViewsInLayout();
                linearLayout.removeAllViews();

                for(DataSnapshot s: snapshot.getChildren()){
                    Solicitud solicitud = s.getValue(Solicitud.class);
                    if(solicitud.getEstado().equals(Estado.ACEPTADA)){
                        rellenarSolicitud(solicitud, linearLayout);
                    }
                }
                listadoDeConversacionesSolicitudesEnviadas(linearLayout);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void listadoDeConversacionesSolicitudesEnviadas(LinearLayout linearLayout) {
        Query query = Constantes.db.child("Solicitud").orderByChild("emisor").equalTo(IDUsuarioLogueado);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s: snapshot.getChildren()){
                    Solicitud solicitud = s.getValue(Solicitud.class);
                    if(solicitud.getEstado().equals(Estado.ACEPTADA)){
                        rellenarSolicitud(solicitud, linearLayout);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rellenarSolicitud(Solicitud solicitud, LinearLayout linearLayout) {
        View v = inflater.inflate(R.layout.usuario_mensaje, linearLayout, false);

        String receptorOEmisor;
        if(solicitud.getEmisor().equals(IDUsuarioLogueado)){
            receptorOEmisor=solicitud.getReceptor();
        } else receptorOEmisor=solicitud.getEmisor();

        nombreUsuario(v, receptorOEmisor);
        recogerImagenYCiuda(v, receptorOEmisor);
        linearLayout.addView(v);
        conversar(v, receptorOEmisor);

    }

    private void recogerImagenYCiuda(View v, String receptorOEmisor) {
        ImageView imageView = v.findViewById(R.id.iconImagen);
        TextView poblacion= v.findViewById(R.id.nombrePoblacion);
        Constantes.db.child("Vivienda").orderByChild("user_id").equalTo(receptorOEmisor)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot v: snapshot.getChildren()){
                    Vivienda vi = v.getValue(Vivienda.class);

                    poblacion.setText(vi.getPoblacion());

                    StorageReference ruta = Constantes.storageRef.child(vi.getImagenes().get(0));
                    final long ONE_MEGABYTE = 1024 * 1024;
                    ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nombreUsuario(View v, String receptorOEmisor) {
        Query que = Constantes.db.child("Usuario").orderByChild("uid").equalTo(receptorOEmisor);
        que.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot us: snapshot.getChildren()){
                    Usuario u = us.getValue(Usuario.class);
                    TextView nombre = v.findViewById(R.id.nombreUsuario);
                    nombre.setText(u.getNombreUsuario());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void irPerfil(View v){
        Intent intent=new Intent(ChatActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    public void irBusqueda (View v){
        Intent intent=new Intent(ChatActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }

    public void irQuedadas (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(ChatActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    public void irMap (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(ChatActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ChatActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }

}
