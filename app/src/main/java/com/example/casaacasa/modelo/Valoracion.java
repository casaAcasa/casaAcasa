package com.example.casaacasa.modelo;

import com.example.casaacasa.utils.TipoValoracion;

import java.util.UUID;

public class Valoracion {
    private String uid;
    private String emisor;
    private String receptor;
    private TipoValoracion tipo;
    private String descripcion;
    private double estrellas;

    public Valoracion(){}

    public Valoracion(String emisor, String receptor, TipoValoracion tipo, String descripcion, double estrellas) {
        this.uid= UUID.randomUUID().toString();
        this.emisor = emisor;
        this.receptor=receptor;
        this.tipo=tipo;
        this.descripcion = descripcion;
        this.estrellas = estrellas;
    }

    public TipoValoracion getTipo() {
        return tipo;
    }

    public void setTipo(TipoValoracion tipo) {
        this.tipo = tipo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(double estrellas) {
        this.estrellas = estrellas;
    }

    @Override
    public String toString() {
        return "Valoracion{" +
                "uid='" + uid + '\'' +
                ", emisor='" + emisor + '\'' +
                ", receptor='" + receptor + '\'' +
                ", tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                ", estrellas=" + estrellas +
                '}';
    }
}
