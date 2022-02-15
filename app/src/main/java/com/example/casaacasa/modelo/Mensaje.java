package com.example.casaacasa.modelo;

import java.util.Date;

public class Mensaje {
    private String texto;
    private Date fechaEmision;

    public Mensaje(String texto, Date fechaEmision) {
        this.texto = texto;
        this.fechaEmision = fechaEmision;
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
