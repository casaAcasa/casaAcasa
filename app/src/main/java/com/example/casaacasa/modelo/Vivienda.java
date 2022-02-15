package com.example.casaacasa.modelo;

import java.util.ArrayList;

public class Vivienda {
    private String direccion;
    private String tipo;
    private String descripcion;
    private ArrayList<String> normas;

    public Vivienda(String direccion, String tipo, String descripcion, ArrayList<String> normas) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.normas = normas;
    }
    public void addNorma(){

    }

    public void eliminarNorma(){

    }

    public void addServicio(){

    }

    public void eliminarServicio(){

    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getNormas() {
        return normas;
    }

    public void setNormas(ArrayList<String> normas) {
        this.normas = normas;
    }

    @Override
    public String toString() {
        return "Vivienda{" +
                "direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", normas=" + normas +
                '}';
    }
}
