package com.enriquealberto.model;

import java.util.ArrayList;

import com.enriquealberto.Lectores.LectorHeroes;
import com.enriquealberto.Lectores.LectorMostruo;
import com.enriquealberto.interfaces.Observer;

public class Juego {
    private static Juego instance;
    private ArrayList<Observer> observers;
    private ArrayList<Heroe> heroes;
    private ArrayList<Enemigo> enemigos;
    private Heroe jugador;
    private int dificultad;
    private String nombre;
    
    
    public Juego(ArrayList<Heroe> heroes, ArrayList<Enemigo> enemigos) {
        this.observers = new ArrayList<>();
        this.heroes = LectorHeroes.leerHeroes();
        this.enemigos = LectorMostruo.leerMostruo();
        this.jugador = null;
    
    }
    public static Juego getInstance(){
        if(instance == null){
            instance = new Juego(LectorHeroes.leerHeroes(), LectorMostruo.leerMostruo());
        }
        return instance;
    }
    public static void setInstance(Juego instance) {
        Juego.instance = instance;
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

    public void setJugador(Heroe jugador) {
        this.jugador = jugador;
        notifyObservers();
    }
    public ArrayList<Heroe> getHeroes() {
        return heroes;
    }
    public void setHeroes(ArrayList<Heroe> heroes) {
        this.heroes = heroes;
    }
    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }
    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }
    public Heroe getJugador() {
        return jugador;
    }
    public int getDificultad() {
        return dificultad;
    }
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
        notifyObservers();
    }
    public String getNombre() {
        return nombre; 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }


    
}
