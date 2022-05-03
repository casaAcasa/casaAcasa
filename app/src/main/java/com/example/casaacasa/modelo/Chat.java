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
    private String  Uidreceptor;
    private ArrayList<Mensaje> mensajesReceptor;
    private ArrayList<Mensaje> MensajesEmisor;



    public Chat(String Uidreceptor, ArrayList<Mensaje> mensajesReceptor, ArrayList<Mensaje> getMensajesEmisor) {
        this.Uidreceptor = Uidreceptor;
        this.mensajesReceptor = mensajesReceptor;
        this.MensajesEmisor = getMensajesEmisor;
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

    public String getUidreceptor() {
        return Uidreceptor;
    }

    public void setUidreceptor(String Uidreceptor) {
        this.Uidreceptor = Uidreceptor;
    }

    public ArrayList<Mensaje> getMensajesReceptor() {
        return mensajesReceptor;
    }

    public void setMensajesReceptor(ArrayList<Mensaje> mensajesReceptor) {
        this.mensajesReceptor = mensajesReceptor;
    }

    public ArrayList<Mensaje> getGetMensajesEmisor() {
        return MensajesEmisor;
    }

    public void setGetMensajesEmisor(ArrayList<Mensaje> getMensajesEmisor) {
        this.MensajesEmisor = getMensajesEmisor;
    }
}
