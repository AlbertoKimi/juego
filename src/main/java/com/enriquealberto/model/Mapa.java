package com.enriquealberto.model;

import java.util.ArrayList;

public class Mapa {
    private String nombre;
    private String suelo;
    private String pared;
    private int nivel;
    private static int contador=0;
    private  ArrayList<ArrayList<Integer>> mapa;

    public Mapa(){
        this.nivel=++contador;
        this.nombre = "Mapa por defecto";
        this.suelo = "suelo por defecto";
        this.pared = "pared por defecto";
        this.mapa = new ArrayList<>();
    }

    public Mapa(String nombre, String suelo, String pared, ArrayList<ArrayList<Integer>> mapa) {
        this.nivel=++contador;
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

}
