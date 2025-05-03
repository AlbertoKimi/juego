package com.enriquealberto.model;

import java.util.*;

import com.enriquealberto.Lectores.LectorHeroes;
import com.enriquealberto.Lectores.LectorMostruo;
import com.enriquealberto.interfaces.Observer;

public class Juego {
    private static Juego instance;
    private ArrayList<Observer> observers;
    private ArrayList<Heroe> heroes;
    private ArrayList<Enemigo> enemigos;
    private String nombre;
    private GestorMapas gestorMapas;


    //atributos para cada partida
    private ArrayList<Personaje> entidades;
    private Map<Posicion, Personaje> entidadesMapa = new HashMap<>();
    private int dificultad;
    private Heroe jugador;
    private ArrayList<Enemigo> enemigosF= new ArrayList<>();
    private ArrayList<Enemigo> enemigosM= new ArrayList<>();
    private ArrayList<Enemigo> enemigosD= new ArrayList<>();
    private Mapa mapaActual;
    private ArrayList<ArrayList<Integer>> MatrizMapa;


    
    public Juego(ArrayList<Heroe> heroes, ArrayList<Enemigo> enemigos) {
        this.observers = new ArrayList<>();
        this.heroes = LectorHeroes.leerHeroes();
        this.enemigos = LectorMostruo.leerMostruo();
        clasificarEnemigos();
        this.jugador = null;
        this.gestorMapas=new GestorMapas();
        this.mapaActual=gestorMapas.getMapaActual();
        this.MatrizMapa= mapaActual.getMapa();
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

    public void clasificarEnemigos(){
        for(Enemigo enemigo: enemigos){
            switch (enemigo.getT_enemigo()){
                case 1:
                    enemigosF.add(enemigo);
                    break;
                case 2:
                    enemigosM.add(enemigo);
                    break;
                case 3:
                    enemigosD.add(enemigo);
            };
        }
    }
    public int comprobarposicion(int x, int y){
        if (x < 0 || x >= MatrizMapa.get(0).size() || y < 0 || y >= MatrizMapa.size()) {
            return 0;
        }
        if (MatrizMapa.get(y).get(x) == 1) {
            return 0;
        }
        if (entidadesMapa.containsKey(new Posicion(x, y))) {
            return 1;
        }
        if(MatrizMapa.get(y).get(x) == 0){
            return 2;
        }else{
            return 0;
        }
    }
    public void iniciarentidades(){
        Random random = new Random();
        entidades.clear();
        entidades.add(jugador);
        int cantidadF = (int) Math.ceil((2.0 + mapaActual.getNivel()) / 2.0) + (dificultad - 1);
        int cantidadM = (int) (mapaActual.getNivel() / 2.0) + dificultad;
        int cantidadD = (int) (mapaActual.getNivel() / 2.0) + (dificultad - 2);
        for (int i = 0; i < cantidadF; i++) {
            Enemigo original = enemigosF.get(random.nextInt(enemigosF.size()));
            entidades.add(original.clone());
        }
        for (int i = 0; i < cantidadM; i++) {
            Enemigo original = enemigosM.get(random.nextInt(enemigosM.size()));
            entidades.add(original.clone());
        }
        for (int i = 0; i < cantidadD; i++) {
            Enemigo original = enemigosD.get(random.nextInt(enemigosD.size()));
            entidades.add(original.clone());
        }
        Collections.sort(entidades);



    }

    
}
