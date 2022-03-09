package com.example.casaacasa.modelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.R;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    private Usuario receptor;
    private ArrayList<Mensaje> mensajesReceptor;
    private ArrayList<Mensaje> getMensajesEmisor;
    private List<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        init();
    }

    public Chat(Usuario receptor, ArrayList<Mensaje> mensajesReceptor, ArrayList<Mensaje> getMensajesEmisor) {
        this.receptor = receptor;
        this.mensajesReceptor = mensajesReceptor;
        this.getMensajesEmisor = getMensajesEmisor;
    }

    public Chat(){

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
        Intent intent = new Intent(this, Solicitud.class );
        startActivity(intent);
    }

    public void elimnarUsuario(View v){
        for(int i=0; i<this.elements.size(); i++){

        }
    }

    public void escribirUsuario(View v){
        Intent intent = new Intent(this, Mensaje.class );
        startActivity(intent);
    }

    public void addMensajeReceptor(){

    }

    public void eliminarMensajeReceptor(){

    }

    public void addMensajeEmisor(){

    }

    public void eliminarMensajeEmisor(){

    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public ArrayList<Mensaje> getMensajesReceptor() {
        return mensajesReceptor;
    }

    public void setMensajesReceptor(ArrayList<Mensaje> mensajesReceptor) {
        this.mensajesReceptor = mensajesReceptor;
    }

    public ArrayList<Mensaje> getGetMensajesEmisor() {
        return getMensajesEmisor;
    }

    public void setGetMensajesEmisor(ArrayList<Mensaje> getMensajesEmisor) {
        this.getMensajesEmisor = getMensajesEmisor;
    }
}
