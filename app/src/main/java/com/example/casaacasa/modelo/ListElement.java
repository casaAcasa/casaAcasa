package com.example.casaacasa.modelo;

public class ListElement {
    public String color;
    public String name;
    public String ciudad;
    public String estadoPeticion;

    public ListElement(String color, String name, String ciudad) {
        this.color = color;
        this.name = name;
        this.ciudad = ciudad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadoPeticion() {
        return estadoPeticion;
    }

    public void setEstadoPeticion(String estadoPeticion) {
        this.estadoPeticion = estadoPeticion;
    }
}
