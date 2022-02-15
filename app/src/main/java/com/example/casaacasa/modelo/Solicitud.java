package com.example.casaacasa.modelo;

public class Solicitud {
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
