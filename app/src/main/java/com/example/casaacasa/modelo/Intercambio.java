package com.example.casaacasa.modelo;

import java.util.Date;
import java.util.UUID;

public class Intercambio implements Comparable<Intercambio>{
    private String uid;
    private String emisor;
    private String receptor;
    private String emisorYReceptor;
    private Date fechaInicio;
    private Date fechaFinal;

    public Intercambio(){}

    public Intercambio(String emisor, String receptor, Date fechaInicio, Date fechaFinal) {
        this.uid= UUID.randomUUID().toString();
        this.emisor = emisor;
        this.receptor = receptor;
        this.emisorYReceptor=emisor+" "+receptor;
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

    public String getEmisorYReceptor() {
        return emisorYReceptor;
    }

    public void setEmisorYReceptor(String emisorYReceptor) {
        this.emisorYReceptor = emisorYReceptor;
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

    @Override
    public int compareTo(Intercambio o) {
        return this.fechaFinal.compareTo(o.getFechaFinal());
    }

    @Override
    public String toString() {
        return "Intercambio{" +
                "uid='" + uid + '\'' +
                ", emisor='" + emisor + '\'' +
                ", receptor='" + receptor + '\'' +
                ", emisorYReceptor='" + emisorYReceptor + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                '}';
    }
}
