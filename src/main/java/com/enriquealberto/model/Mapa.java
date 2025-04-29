package com.enriquealberto.model;

import java.util.ArrayList;

public class Mapa {
    private String nombre;
    private String suelo;
    private String pared;
    private String sueloLava;
    private String lava;
    private int nivel;
    private  ArrayList<ArrayList<Integer>> mapa;

    public Mapa(){
        this.nivel=1;
        this.nombre = "Mapa por defecto";
        this.suelo = "/com/enriquealberto/imagenes/SueloCiudad.jpg";
        this.pared = "/com/enriquealberto/imagenes/casa1.jpg";
        this.lava= "/com/enriquealberto/imagenes/lavapared.jpg";
        this.sueloLava= "/com/enriquealberto/imagenes/lava.jpg";
        this.mapa = new ArrayList<>();
    }

    public Mapa(String nombre, String suelo, String pared, ArrayList<ArrayList<Integer>> mapa) {
        this.nivel++;
        this.nombre = nombre;
        this.suelo = suelo;
        this.pared = pared;
        this.mapa = mapa;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSuelo() {
        return this.suelo;
    }

    public void setSuelo(String suelo) {
        this.suelo = suelo;
    }

    public String getPared() {
        return this.pared;
    }

    public void setPared(String pared) {
        this.pared = pared;
    }

    public  ArrayList<ArrayList<Integer>> getMapa() {
        return this.mapa;
    }

    public void setMapa( ArrayList<ArrayList<Integer>> mapa) {
        this.mapa = mapa;
    }

    public int getNivel() {
        return this.nivel;
    }

    public String getSueloLava() {
        return this.sueloLava;
    }

    public void setSueloLava(String sueloLava) {
        this.sueloLava = sueloLava;
    }

    public String getLava() {
        return this.lava;
    }

    public void setLava(String lava) {
        this.lava = lava;
    }

}
