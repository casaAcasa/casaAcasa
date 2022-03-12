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



    public Chat(Usuario receptor, ArrayList<Mensaje> mensajesReceptor, ArrayList<Mensaje> getMensajesEmisor) {
        this.receptor = receptor;
        this.mensajesReceptor = mensajesReceptor;
        this.getMensajesEmisor = getMensajesEmisor;
    }

    public Chat(){

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
