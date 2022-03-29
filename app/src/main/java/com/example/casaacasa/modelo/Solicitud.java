package com.example.casaacasa.modelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.casaacasa.activities.MainActivity;
import com.example.casaacasa.R;
import com.example.casaacasa.utils.Estado;

import java.util.UUID;

public class Solicitud {
    private String uid;
    private String emisor;
    private String receptor;
    private Estado estado;
    private String mensaje;

    public Solicitud(){

    }

    public Solicitud(String emisor, String receptor, Estado estado, String mensaje) {
        this.uid= UUID.randomUUID().toString();
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = estado;
        this.mensaje=mensaje;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "emisor=" + emisor +
                ", receptor=" + receptor +
                ", estado=" + estado +
                '}';
    }
}
