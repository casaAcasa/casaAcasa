package com.example.casaacasa.modelo;

import java.util.ArrayList;
import java.util.UUID;

public class Vivienda {
    private String uid;
    private String direccionExacta;
    private String poblacion;
    private String tipoVivienda;
    private String tipoPoblacion;
    private int numHabitaciones;
    private int metrosCuadrados;
    private String descripcion;
    private ArrayList<String> normas;
    private ArrayList<String> servicios;
    private String user_id;
    private ArrayList<String> imagenes;
    private ArrayList<String> valoracionesRecividas;
    private ArrayList<String> solicitudesRecividas;
    private double valoracionMediaA;
    private double valoracionMediaI;
    private double valoracionMediaConjunta;

    public Vivienda() {
        this.imagenes = new ArrayList<String>();
        this.valoracionesRecividas = new ArrayList<String>();
        this.solicitudesRecividas = new ArrayList<String>();
    }

    public Vivienda(String direccion, int numHabitaciones, int metrosCuadrados,
                    String descripcion, String user_id, ArrayList<String> normas,
                    ArrayList<String> servicios, double valoracionMediaA, double valoracionMediaI,
                    double valoracionMediaConjunta, String poblacion, String tipoVivienda,
                    String tipoPoblacion) {
        this.uid = UUID.randomUUID().toString();
        this.direccionExacta = direccion;
        this.numHabitaciones = numHabitaciones;
        this.metrosCuadrados = metrosCuadrados;
        this.descripcion = descripcion;
        this.normas = normas;
        this.servicios = servicios;
        this.user_id = user_id;
        this.imagenes = new ArrayList<String>();
        this.valoracionesRecividas = new ArrayList<String>();
        this.solicitudesRecividas = new ArrayList<String>();
        this.valoracionMediaA = valoracionMediaA;
        this.valoracionMediaI = valoracionMediaI;
        this.valoracionMediaConjunta = valoracionMediaConjunta;
        this.poblacion = poblacion;
        this.tipoVivienda = tipoVivienda + ".";
        this.tipoPoblacion = tipoPoblacion + ".";
    }

    public boolean viviendaNoMostrable(){
        if(this.imagenes.isEmpty()||this.descripcion.equals("")||
                this.metrosCuadrados==0||this.poblacion.equals("")||this.tipoVivienda.equals(".")){
            return true;
        } else{
            return false;
        }
    }

    public void addNorma() {

    }

    public void eliminarNorma() {

    }

    public void addServicio() {

    }

    public void eliminarServicio() {

    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getTipoPoblacion() {
        return tipoPoblacion;
    }

    public void setTipoPoblacion(String tipoPoblacion) {
        this.tipoPoblacion = tipoPoblacion;
    }

    public double getValoracionMediaA() {
        return valoracionMediaA;
    }

    public void setValoracionMediaA(double valoracionMediaA) {
        this.valoracionMediaA = valoracionMediaA;
    }

    public double getValoracionMediaI() {
        return valoracionMediaI;
    }

    public void setValoracionMediaI(double valoracionMediaI) {
        this.valoracionMediaI = valoracionMediaI;
    }

    public double getValoracionMediaConjunta() {
        return valoracionMediaConjunta;
    }

    public void setValoracionMediaConjunta(double valoracionMediaConjunta) {
        this.valoracionMediaConjunta = valoracionMediaConjunta;
    }

    public ArrayList<String> getValoracionesRecividas() {
        return valoracionesRecividas;
    }

    public void setValoracionesRecividas(ArrayList<String> valoracionesRecividas) {
        this.valoracionesRecividas = valoracionesRecividas;
    }

    public ArrayList<String> getSolicitudesRecividas() {
        return solicitudesRecividas;
    }

    public void setSolicitudesRecividas(ArrayList<String> solicitudesRecividas) {
        this.solicitudesRecividas = solicitudesRecividas;
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

    public String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(String direccion) {
        this.direccionExacta = direccion;
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


}
