package com.enriquealberto.model;

public class Mapa {
    private String nombre;
    private String suelo;
    private String pared;
    private int nivel;
    private static int contador=0;
    private  int [][] mapa;

    /*public Mapa(){
        this.nivel=++contador;
        this.nombre = "Mapa por defecto";
        this.suelo = "suelo por defecto";
        this.pared = "pared por defecto";
        this.mapa = new int [0][0];
    }*/

    public Mapa(String nombre, String suelo, String pared, int [][] mapa) {
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

    public  int[][] getMapa() {
        return this.mapa;
    }

    public void setMapa( int[][] mapa) {
        this.mapa = mapa;
    }

    public int getNivel() {
        return this.nivel;
    }

}
