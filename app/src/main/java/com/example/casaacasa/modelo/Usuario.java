package com.example.casaacasa.modelo;

import java.util.ArrayList;
import java.util.UUID;

public class Usuario {
    private String uid;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String fechaNacimiento;
    private String mail;
    private int telefono;
    private String nombreUsuario;
    private String password;
    private Vivienda vivienda;
    private ArrayList<Valoracion> valoracionesInquilino;
    private ArrayList<Valoracion> valoracionesAnfitrion;
    private ArrayList<Solicitud> solicitudesRecibidas;
    private ArrayList<Chat> chats;
    private boolean verificado;

    public Usuario(String nombre, String apellido1, String apellido2, String fechaNacimiento, String mail, int telefono, String nombreUsuario, String password) {
        this.uid=UUID.randomUUID().toString();
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.mail = mail;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.vivienda = null;
        this.valoracionesInquilino = new ArrayList<>();
        this.valoracionesAnfitrion = new ArrayList<>();
        this.solicitudesRecibidas = new ArrayList<>();
        this.chats = new ArrayList<>();
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
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

    public ArrayList<Valoracion> getValoracionesInquilino() {
        return valoracionesInquilino;
    }

    public void setValoracionesInquilino(ArrayList<Valoracion> valoracionesInquilino) {
        this.valoracionesInquilino = valoracionesInquilino;
    }

    public ArrayList<Valoracion> getValoracionesAnfitrion() {
        return valoracionesAnfitrion;
    }

    public void setValoracionesAnfitrion(ArrayList<Valoracion> valoracionesAnfitrion) {
        this.valoracionesAnfitrion = valoracionesAnfitrion;
    }

    public ArrayList<Solicitud> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public void setSolicitudesRecibidas(ArrayList<Solicitud> solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", getApellido2='" + apellido2 + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", mail='" + mail + '\'' +
                ", telefono=" + telefono +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", vivienda=" + vivienda +
                ", valoracionesInquilino=" + valoracionesInquilino +
                ", valoracionesAnfitrion=" + valoracionesAnfitrion +
                ", solicitudesRecibidas=" + solicitudesRecibidas +
                ", chats=" + chats +
                ", verificado=" + verificado +
                '}';
    }
}
