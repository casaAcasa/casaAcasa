package com.example.casaacasa.modelo;


import java.util.Date;
import java.util.UUID;


public class Mensaje implements Comparable<Mensaje>{
    private String uid;
    private String mensaje;
    private Date fechaCreacion;
    private String emisor;
    private String receptor;
    private String emisorYReceptor;
    private boolean mensajeIntercambio;

    public Mensaje(String mensaje, String emisor, String receptor, boolean mensajeIntercambio) {
        this.uid= UUID.randomUUID().toString();
        this.mensaje = mensaje;
        this.emisor = emisor;
        this.receptor = receptor;
        this.emisorYReceptor=emisor+" "+receptor;
        this.fechaCreacion = new Date();
        this.mensajeIntercambio=mensajeIntercambio;
    }

    public Mensaje() {}

    public boolean isMensajeIntercambio() {
        return mensajeIntercambio;
    }

    public void setMensajeIntercambio(boolean mensajeIntercambio) {
        this.mensajeIntercambio = mensajeIntercambio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEmisorYReceptor() {
        return emisorYReceptor;
    }

    public void setEmisorYReceptor(String emisorYReceptor) {
        this.emisorYReceptor = emisorYReceptor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    @Override
    public int compareTo(Mensaje o) {
        return this.fechaCreacion.compareTo(o.getFechaCreacion());
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "uid='" + uid + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", emisor='" + emisor + '\'' +
                ", receptor='" + receptor + '\'' +
                ", emisorYReceptor='" + emisorYReceptor + '\'' +
                ", mensajeIntercambio=" + mensajeIntercambio +
                '}';
    }
}