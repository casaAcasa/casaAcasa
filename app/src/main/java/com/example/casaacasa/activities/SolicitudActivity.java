package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.modelo.Chat;
import com.example.casaacasa.modelo.ListAdaptorSolicitud;
import com.example.casaacasa.modelo.ListElement;
import java.util.ArrayList;
import java.util.List;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.Estado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SolicitudActivity extends AppCompatActivity {
    private List<ListElement> elements;
    private List<Solicitud> solicitudees;
    private List<Usuario> usuariosPendientes;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solicitud);
        solicitudees = new ArrayList<>();
        usuariosPendientes = new ArrayList<>();
        elements = new ArrayList<>();
        readData(new FirebaseCallBack() {
            @Override
            public void onCallBack(List<Usuario> usuariosPendientes) {
                if(usuariosPendientes.size() != 0){
                    elements.add(new ListElement("#785447", usuariosPendientes.get(contador).getNombre(), usuariosPendientes.get(contador).getApellido1()+" "+usuariosPendientes.get(contador).getApellido2()));
                    ListAdaptorSolicitud listAdaptorSolicitud = new ListAdaptorSolicitud(elements, SolicitudActivity.this, contador);
                    RecyclerView recyclerView = findViewById(R.id.SolicitudId);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SolicitudActivity.this));
                    recyclerView.setAdapter(listAdaptorSolicitud);
                    contador++;
                }
            }
        });
    }

    public void paginaChat(View v){
        Intent intent = new Intent(this, ChatActivity.class );
        startActivity(intent);
    }


    public void mirarSolicitud(View v){
        AlertDialog.Builder dialog=new AlertDialog.Builder(SolicitudActivity.this);
        String mensaje = solicitudees.get(0).getMensaje();
        dialog.setTitle(mensaje);
        LayoutInflater layoutInflater = LayoutInflater.from(SolicitudActivity.this);
        View view = layoutInflater.inflate(R.layout.popup_mirar_solicitud, null);
        dialog.setView(view);

        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SolicitudActivity.this, "Has aceptado la solicitud", Toast.LENGTH_SHORT).show();
                Usuario u = usuariosPendientes.get(0);
                Constantes.db.child("Solicitud").child(u.getUid()).child("estado").setValue("ACEPTADA");
                dialog.cancel();
            }
        });

        dialog.setNegativeButton("RECHAZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SolicitudActivity.this, "Has rechazado la solicitud", Toast.LENGTH_SHORT).show();
                Usuario u = usuariosPendientes.get(0);
                Constantes.db.child("Solicitud").child(u.getUid()).child("estado").setValue("DENEGADA");
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    //Limpiar las listas para que solo salga una vez y cambiar el addListener para que recargue la pagina
    private void readData (FirebaseCallBack firebaseCallBack){
        Query query = Constantes.db.child("Solicitud").orderByChild("receptor").equalTo("d5edaee4-9498-48c4-a4c4-baa3978adfeb"); //poner el id de la persona logeada
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    Solicitud solicitud = s.getValue(Solicitud.class);
                    if(solicitud.getEstado().equals(Estado.PENDIENTE)){
                        solicitudees.add(solicitud);
                    }
                }
                for(int i=0; i<solicitudees.size(); i++){
                    Query que = Constantes.db.child("Usuario").orderByChild("uid").equalTo(solicitudees.get(i).getUid());
                    que.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot u: snapshot.getChildren()){
                                Usuario user = u.getValue(Usuario.class);
                                usuariosPendientes.add(user);
                            }
                            firebaseCallBack.onCallBack(usuariosPendientes);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private interface FirebaseCallBack{
        void onCallBack(List<Usuario> usuariosPendientes);
    }
}
