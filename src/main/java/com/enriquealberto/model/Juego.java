package com.enriquealberto.model;

import java.util.ArrayList;

import com.enriquealberto.Lectores.LectorHeroes;
import com.enriquealberto.Lectores.LectorMostruo;

public class Juego {
    private static Juego instance;
    private ArrayList<Heroe> heroes;
    private ArrayList<Enemigo> enemigos;
    private Heroe jugador;
    
    public Juego(ArrayList<Heroe> heroes, ArrayList<Enemigo> enemigos) {
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

    public void setJugador(Heroe jugador) {
        this.jugador = jugador;
     
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



    
}
