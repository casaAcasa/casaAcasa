package com.example.casaacasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.ListAdaptorChat;
import com.example.casaacasa.modelo.ListAdaptorSolicitud;
import com.example.casaacasa.modelo.ListElement;
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.utils.Estado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    private List<ListElement> elements;
    private ArrayList<Solicitud> solicitudes;
    private ArrayList<Usuario> usuariosAceptados;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        solicitudes = new ArrayList<>();
        usuariosAceptados = new ArrayList<>();
        elements = new ArrayList<>();
        contador=0;
        readData(new FirebaseCallBack() {
            @Override
            public void onCallBack(List<Usuario> usuariosAceptados) {
                if(usuariosAceptados.size() != 0){
                    elements.add(new ListElement("#785447", usuariosAceptados.get(contador).getNombre(), usuariosAceptados.get(contador).getApellido1()+" "+usuariosAceptados.get(contador).getApellido2()));
                    ListAdaptorSolicitud listAdaptorSolicitud = new ListAdaptorSolicitud(elements, ChatActivity.this, contador);
                    RecyclerView recyclerView = findViewById(R.id.MensajeriaId);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    recyclerView.setAdapter(listAdaptorSolicitud);
                    contador++;
                }
            }
        });
        //init();
    }
    public void init(){
        /*elements.add(new ListElement("#775447", "Jorge", "Barcelona"));
        elements.add(new ListElement("#607dbB", "Oscar", "Madrid"));
        elements.add(new ListElement("#03a9f4", "Dylan", "Sevilla"));
        elements.add(new ListElement("#f44336", "Guillermo", "Murcia"));
        elements.add(new ListElement("#009688", "Alex", "Galicia"));
        elements.add(new ListElement("#484632", "Mario", "Malaga"));
        elements.add(new ListElement("#745211", "Claudia", "Huesca"));
        elements.add(new ListElement("#115486", "Andres", "Zaragoza"));
        elements.add(new ListElement("#368185", "Pedro", "Asturias"));*/


    }

    public void paginaSolicitudes(View v){
        Intent intent = new Intent(this, SolicitudActivity.class );
        startActivity(intent);
    }

    public void elimnarUsuario(View v){
        for(int i=0; i<this.elements.size(); i++){

        }
    }

    public void escribirUsuario(View v){
        Intent intent = new Intent(this, MensajeActivity.class );
        startActivity(intent);
    }

    private void readData (ChatActivity.FirebaseCallBack firebaseCallBack){
        Query query = MainActivity.db.child("Solicitud").orderByChild("receptor").equalTo("d5edaee4-9498-48c4-a4c4-baa3978adfeb"); //poner el id de la persona logeada
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    Solicitud solicitud = s.getValue(Solicitud.class);
                    if(solicitud.getEstado().equals(Estado.ACEPTADA)){
                        solicitudes.add(solicitud);
                    }
                }
                for(int i=0; i<solicitudes.size(); i++){
                    Query que = MainActivity.db.child("Usuario").orderByChild("uid").equalTo(solicitudes.get(i).getUid());
                    que.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot u: snapshot.getChildren()){
                                Usuario user = u.getValue(Usuario.class);
                                usuariosAceptados.add(user);
                            }
                            firebaseCallBack.onCallBack(usuariosAceptados);
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
        void onCallBack(List<Usuario> usuariosAceptados);
    }
}
