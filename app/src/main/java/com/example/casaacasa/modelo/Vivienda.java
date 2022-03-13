package com.example.casaacasa.modelo;

import java.util.ArrayList;
import java.util.UUID;

public class Vivienda {
    private String uid;
    private String direccion;
    private String tipo;
    private int numHabitaciones;
    private int metrosCuadrados;
    private String descripcion;
    private ArrayList<String> normas;
    private ArrayList<String> servicios;
    private String user_id;
    private ArrayList<String> imagenes;

    public Vivienda(){

    }
    public Vivienda(String direccion, String tipo, int numHabitaciones, int metrosCuadrados, String descripcion, String user_id, ArrayList<String> normas, ArrayList<String> servicios) {
        this.uid= UUID.randomUUID().toString();
        this.direccion = direccion;
        this.numHabitaciones=numHabitaciones;
        this.metrosCuadrados=metrosCuadrados;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.normas = normas;
        this.servicios = servicios;
        this.user_id=user_id;
        this.imagenes=new ArrayList<String>();
    }

    public void addNorma(){

    }

    public void eliminarNorma(){

    }

    public void addServicio(){

    }

    public void eliminarServicio(){

    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<String> servicios) {
        this.servicios = servicios;
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
