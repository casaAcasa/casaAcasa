package com.example.casaacasa.modelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.activities.MainActivity;
import com.example.casaacasa.R;

public class Solicitud extends AppCompatActivity {
    private Usuario emisor;
    private Usuario receptor;
    private Estado estado;

    public Solicitud(Usuario emisor, Usuario receptor, Estado estado) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = estado;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
