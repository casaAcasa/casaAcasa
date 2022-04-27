package com.example.casaacasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Mensaje;
import com.example.casaacasa.utils.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeSet;
import java.util.zip.Inflater;

public class MensajeActivity extends AppCompatActivity {

    private Intent startIntent;
    private TreeSet<Mensaje> mensajes;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_mensajes_emisor);

        inflater=LayoutInflater.from(MensajeActivity.this);
        startIntent=getIntent();

        obtenerMensajesUsuarioLogueado();

    }

    private void obtenerMensajesUsuarioLogueado(){
        //TODO Añadir un atributo que sea una combinatoria del id del emisor y elklk receptor, así solo cojeré los mensajes que de los dos y no sobrecargo la apluicacióin
        // tambien tendré que hacer dos búscaquedas diferentes, una receptor emisor y otra emisor receptor, para coger los deo tipos de mensaje
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo("26a08f75-5967-434d-a283-a8b60e70135a "+startIntent.getStringExtra("UsuarioContrario"));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Añadirlos a un arraylist
                // Buscar los mensajes en orden receptor emisor
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    Mensaje m=mensaje.getValue(Mensaje.class);
                    mensajes.add(m);
                }
                obtenerMensajesUsuarioContrario();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setContentView(R.layout.activity_mensajeria);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void obtenerMensajesUsuarioContrario() {
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo(startIntent.getStringExtra("UsuarioContrario")+" 26a08f75-5967-434d-a283-a8b60e70135a");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    Mensaje m=mensaje.getValue(Mensaje.class);
                    mensajes.add(m);
                }
               // anadirMensajesAlLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anadirMensajesAlLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaMensajes);
        linearLayout.removeAllViewsInLayout();
        linearLayout.removeAllViews();

        for(Mensaje m: mensajes){
            View v;
            if(m.getEmisor().equals("26a08f75-5967-434d-a283-a8b60e70135a")) v = inflater.inflate(R.layout.card_view_mensajes_emisor, linearLayout, false);
            else v = inflater.inflate(R.layout.card_view_mensajes_receptor, linearLayout, false);
            rellenarMensaje(linearLayout,v);
        }
    }

    private void rellenarMensaje(LinearLayout linearLayout, View v) {

    }

}
