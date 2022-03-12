package com.example.casaacasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.modelo.ListAdaptor;
import com.example.casaacasa.modelo.ListElement;
import com.example.casaacasa.modelo.Mensaje;
import com.example.casaacasa.modelo.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    private List<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        init();
    }
    public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("#775447", "Jorge", "Barcelona"));
        elements.add(new ListElement("#607dbB", "Oscar", "Madrid"));
        elements.add(new ListElement("#03a9f4", "Dylan", "Sevilla"));
        elements.add(new ListElement("#f44336", "Guillermo", "Murcia"));
        elements.add(new ListElement("#009688", "Alex", "Galicia"));
        elements.add(new ListElement("#484632", "Mario", "Malaga"));
        elements.add(new ListElement("#745211", "Claudia", "Huesca"));
        elements.add(new ListElement("#115486", "Andres", "Zaragoza"));
        elements.add(new ListElement("#368185", "Pedro", "Asturias"));

        ListAdaptor listAdaptor = new ListAdaptor(elements, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdaptor);
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
}
