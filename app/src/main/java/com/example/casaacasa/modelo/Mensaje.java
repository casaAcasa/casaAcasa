package com.example.casaacasa.modelo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.google.firebase.database.ServerValue;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class Mensaje extends AppCompatActivity {
    private String mensaje;
    private String urlFoto;
    private boolean contieneFoto;
    private String keyEmisor;
    private Object createdTimestamp;

    public Mensaje() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isContieneFoto() {
        return contieneFoto;
    }

    public void setContieneFoto(boolean contieneFoto) {
        this.contieneFoto = contieneFoto;
    }

    public String getKeyEmisor() {
        return keyEmisor;
    }

    public void setKeyEmisor(String keyEmisor) {
        this.keyEmisor = keyEmisor;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }
}