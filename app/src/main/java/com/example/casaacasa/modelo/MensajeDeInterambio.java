package com.example.casaacasa.modelo;

import java.util.Date;

public class MensajeDeInterambio extends Mensaje {
    private Date fechaInicio;
    private Date fechaFinal;
    private boolean aceptado;

    public MensajeDeInterambio(String mensaje, String emisor, String receptor, boolean mensajeIntercambio, Date fechaInicio, Date fechaFinal, boolean aceptado) {
        super(mensaje, emisor, receptor, mensajeIntercambio);
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.aceptado = aceptado;
    }

    public MensajeDeInterambio() {
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

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
}
