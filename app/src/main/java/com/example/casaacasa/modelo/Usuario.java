package com.example.casaacasa.modelo;

import java.util.ArrayList;
import java.util.UUID;

public class Usuario {
    private String uid;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String mail;
    private int telefono;
    private String nombreUsuario;
    private String password;
    private Vivienda vivienda;
    private boolean verificado;

    public Usuario(){

    }

    public Usuario(String nombre, String apellidos, String fechaNacimiento, String mail, int telefono, String nombreUsuario, String password) {
        this.uid=UUID.randomUUID().toString();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.mail = mail;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.vivienda = null;
        this.verificado = false;
    }



    public void pedirSolicitud(){

    }

    public void aceptarSolicitud(){

    }

    public void denegarSolicitud(){

    }

    public void valorarUsuario(){

    }

    public void valorarPuntoInteres(){

    }

    public void enviarMensaje(){

    }

    public void calcularValoracionMedia(){

    }

    public void verificarIdentidad(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public void setFechaDeNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }


}
