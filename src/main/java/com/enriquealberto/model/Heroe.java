package com.enriquealberto.model;

import java.util.ArrayList;

import com.enriquealberto.interfaces.Observer;

public class Heroe extends Personaje {
    private ArrayList<Observer> observers;

    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.observers = new ArrayList<>();
    }
    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje, int x , int y) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.observers = new ArrayList<>();
        this.posicion[0] = x;
        this.posicion[1] = y;
    }

    public void suscribe(Observer observer){
        observers.add(observer);
    }
   
    public void unsuscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        observers.forEach(x -> x.onChange());
    }
   
    public void setPosicion(int x, int y) {
        this.posicion[0] = x;
        this.posicion[1] = y;
        notifyObservers();
    }
    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }

    
}
