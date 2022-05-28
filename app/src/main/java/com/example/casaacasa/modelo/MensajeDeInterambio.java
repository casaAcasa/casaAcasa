package com.example.casaacasa.modelo;

import java.util.Date;

public class MensajeDeInterambio extends Mensaje {
    private Date fechaInicio;
    private Date fechaFinal;
    private boolean aceptado;
    private boolean rechazado;

    public MensajeDeInterambio(String mensaje, String emisor, String receptor, boolean mensajeIntercambio, Date fechaInicio, Date fechaFinal) {
        super(mensaje, emisor, receptor, mensajeIntercambio);
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.aceptado = false;
        this.rechazado=false;
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

    public boolean isRechazado() {
        return rechazado;
    }

    public void setRechazado(boolean rechazado) {
        this.rechazado = rechazado;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    @Override
    public String toString() {
        return "MensajeDeInterambio{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                ", aceptado=" + aceptado +
                ", rechazado=" + rechazado +
                '}';
    }
}
