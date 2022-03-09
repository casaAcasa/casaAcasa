package com.example.casaacasa.modelo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;

import java.util.Date;

public class Mensaje extends AppCompatActivity {
    private String texto;
    private Date fechaEmision;

    public Mensaje(String texto, Date fechaEmision) {
        this.texto = texto;
        this.fechaEmision = fechaEmision;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensaje);
    }

    public void init(){

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "texto='" + texto + '\'' +
                ", fechaEmision=" + fechaEmision +
                '}';
    }
}
