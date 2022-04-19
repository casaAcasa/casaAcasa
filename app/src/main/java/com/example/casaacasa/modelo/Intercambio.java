package com.example.casaacasa.modelo;

import java.util.Date;
import java.util.UUID;

public class Intercambio {
    private String uid;
    private String emisor;
    private String receptor;
    private Date fechaInicio; //Se tendrá que poner en Date y formatear, pero es lo mas facil por ahora.
    private Date fechaFinal; //Lo mismo que fechaInicio

    public Intercambio(){}

    public Intercambio(String emisor, String receptor, Date fechaInicio, Date fechaFinal) {
        this.uid= UUID.randomUUID().toString();
        this.emisor = emisor;
        this.receptor = receptor;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}