package com.enriquealberto.model;
import java.util.ArrayList;

import com.enriquealberto.interfaces.Observer;

public class Fruta {
    protected ArrayList<Observer> observers;
    protected String nombre;
    protected String imagen;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int pocentaje;
    protected int PosicionActual;
    protected int PosicionFinal;

    public Fruta() {
        this.nombre = "";
        this.imagen = "";
        this.vida = 0;
        this.ataque = 0;
        this.defensa = 0;
        this.velocidad = 0;
        this.pocentaje = 0;
        this.observers = new ArrayList<>();
    }
    
}
