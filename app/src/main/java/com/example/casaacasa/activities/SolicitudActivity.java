package com.example.casaacasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.modelo.ListAdaptorSolicitud;
import com.example.casaacasa.modelo.ListElement;

import java.util.ArrayList;
import java.util.List;

import com.example.casaacasa.R;

public class SolicitudActivity extends AppCompatActivity {
    private List<ListElement> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solicitud);
        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("#785447", "Francisco", "Madrid"));
        elements.add(new ListElement("#455247", "Miguel", "Teruel"));
        elements.add(new ListElement("#685457", "Antonio", "Lugo"));
        elements.add(new ListElement("#785200", "Sergio", "Cartagena"));
        elements.add(new ListElement("#123447", "Daniel", "Melilla"));
        elements.add(new ListElement("#788747", "Angel", "Cadiz"));
        elements.add(new ListElement("#785527", "Sara", "Zaragoza"));
        elements.add(new ListElement("#712347", "Cristian", "Huesca"));
        elements.add(new ListElement("#788957", "Eric", "Barcelona"));

        ListAdaptorSolicitud listAdaptorSolicitud = new ListAdaptorSolicitud(elements, this);
        RecyclerView recyclerView = findViewById(R.id.SolicitudId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdaptorSolicitud);
    }

    public void paginaChat(View v){
        Intent intent = new Intent(this, ChatActivity.class );
        startActivity(intent);
    }

    public void aceptarSolicitud(View v){

    }

    public void cancelarSolicitud(View v){

    }
}
