package com.example.casaacasa.modelo;

public class Valoracion {
    private Usuario emisor;
    private String descripcion;
    private int estrellas;

    public Valoracion(Usuario emisor, String descripcion, int estrellas) {
        this.emisor = emisor;
        this.descripcion = descripcion;
        this.estrellas = estrellas;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    @Override
    public String toString() {
        return "Valoracion{" +
                "emisor=" + emisor +
                ", descripcion='" + descripcion + '\'' +
                ", estrellas=" + estrellas +
                '}';
    }
}
