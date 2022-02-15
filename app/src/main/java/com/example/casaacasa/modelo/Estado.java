package com.example.casaacasa.modelo;

public class Estado {
    private boolean aceptada;
    private boolean denegada;
    private boolean enEspera;

    public Estado(boolean aceptada, boolean denegada, boolean enEspera) {
        this.aceptada = false;
        this.denegada = false;
        this.enEspera = false;
    }

    public void cambiarEstado(){
        if(this.aceptada){
            this.denegada= false;
            this.enEspera= false;
        }
        if(this.denegada){
            this.aceptada= false;
            this.enEspera= false;
        }
        if(enEspera){
            this.aceptada= false;
            this.denegada= false;
        }

    }

    public boolean isAceptada() {
        return aceptada;
    }
    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }
    public boolean isDenegada() {
        return denegada;
    }
    public void setDenegada(boolean denegada) {
        this.denegada = denegada;
    }
    public boolean isEnEspera() {
        return enEspera;
    }
    public void setEnEspera(boolean enEspera) {
        this.enEspera = enEspera;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "aceptada=" + aceptada +
                ", denegada=" + denegada +
                ", enEspera=" + enEspera +
                '}';
    }
}
