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
        jugador.setPosicion(new Posicion(0, 0));
        entidades.add(jugador);
        entidadesMapa.put(jugador.getPosicion(), jugador);
        int cantidadF = (int) Math.ceil((2.0 + mapaActual.getNivel()) / 2.0) + (dificultad - 1);
        int cantidadM = (int) (mapaActual.getNivel() / 2.0) + dificultad;
        int cantidadD = (int) (mapaActual.getNivel() / 2.0) + (dificultad - 2);
        for (int i = 0; i < cantidadF; i++) {
            Enemigo copia = enemigosF.get(random.nextInt(enemigosF.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);

        }
        for (int i = 0; i < cantidadM; i++) {
            Enemigo copia = enemigosM.get(random.nextInt(enemigosM.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);
        }
        for (int i = 0; i < cantidadD; i++) {
            Enemigo copia = enemigosD.get(random.nextInt(enemigosD.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);
        }
        Collections.sort(entidades);
    }
    public void colocarEnemigoEnPosicionAleatoria(Enemigo enemigo) {
        Random random = new Random();
        int x, y;
        do {
            y = random.nextInt(MatrizMapa.size());
            x = random.nextInt(MatrizMapa.get(0).size());
        } while (comprobarposicion(x, y) != 2);
        enemigo.setPosicion(new Posicion(x, y));
        entidadesMapa.put(enemigo.getPosicion(), enemigo);
    }

    public boolean moverDerecha(Personaje p){
      return   mover(p, 1, 0);
    }
    public boolean moverIzquierda(Personaje p){
       return mover(p, -1, 0);
    }
    public boolean moverArriba(Personaje p){
       return mover(p, 0, 1);
    }
    public boolean moverAbajo(Personaje p){
        return mover(p, 0, -1);
    }

    public boolean mover(Personaje p, int x, int y) {
        Posicion antigua = p.getPosicion();
        int xNueva = antigua.getX() + x;
        int yNueva = antigua.getY() + y;
        int resultado = comprobarposicion(xNueva, yNueva);

        switch (resultado) {
            case 0:
                System.out.println("Colisión");
                return false;
            case 1:
                // atacar
                return true;
            case 2:
                Posicion nueva = new Posicion(xNueva, yNueva);
                p.setPosicion(nueva);
                entidadesMapa.remove(antigua);
                entidadesMapa.put(nueva, p);
                return true;

                default:
                return false;
        }
    }

    public void moverAleatorio(Personaje p) {
        Random rand = new Random();
        boolean movido = false;
        do {
            int numero = rand.nextInt(4);
            switch (numero) {
                case 0:
                    movido = moverAbajo(p);
                    break;

                case 1:
                    movido = moverArriba(p);
                    break;
                case 2:
                    movido = moverDerecha(p);
                    break;

                case 3:
                    movido = moverIzquierda(p);
                    break;
            }
        }while (!movido);
    }

    public void moverGuiado(Personaje p) {
        Posicion posActual = p.getPosicion();
        Posicion posJugador = jugador.getPosicion();

        int dx = posJugador.getX() - posActual.getX();
        int dy = posJugador.getY() - posActual.getY();
        boolean movido = false;
        if(Math.abs(dx) > Math.abs(dy)){
            if(dx > 0){
                movido=moverDerecha(p);
                if(!movido){
                    if(dy>0){
                        movido= moverAbajo(p);
                    }else{
                        movido= moverArriba(p);
                    }
                }
            }else{
                movido=moverIzquierda(p);
                if(!movido){
                    if(dy>0){
                        movido= moverAbajo(p);
                    }else{
                        movido= moverArriba(p);
                    }
                }
            }
        }else{
            if(dy>0){
               movido= moverAbajo(p);
               if(!movido){
                   if(dx>0){
                       movido=  moverDerecha(p);
                   }else{
                       movido=  moverIzquierda(p);
                   }
               }
            }else{
                movido= moverArriba(p);
                if(!movido){
                    if(dx>0){
                        movido=  moverDerecha(p);
                    }else{
                        movido=  moverIzquierda(p);
                    }
                }
            }
        }
        if(!movido){
            moverAleatorio(p);
        }
    }

    public void moverenemigo(Enemigo e){
        Posicion posActual = e.getPosicion();
        Posicion posJugador = jugador.getPosicion();

        int dx = posJugador.getX() - posActual.getX();
        int dy = posJugador.getY() - posActual.getY();
        
        if(Math.abs(dx)==1 || Math.abs(dx) ==1) {
            //atacar
        }else{
            if(Math.abs(dx) <= e.getPercepcion() || Math.abs(dy) <= e.getPercepcion()) {
                moverGuiado(e);
            }else{
                moverAleatorio(e);
            }
        }
    }
}
