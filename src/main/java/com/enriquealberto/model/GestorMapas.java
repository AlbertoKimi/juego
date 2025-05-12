package com.enriquealberto.model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.enriquealberto.Lectores.LectorMapa;
import com.enriquealberto.interfaces.Observer;

public class GestorMapas {
    LinkedHashMap<String,Mapa> mapas;
    Mapa mapaActual;
    ArrayList<Observer> observers;

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.add(observer);
    }

    public void notifyObservers(){
        observers.forEach(item -> item.onChange());
    }

    public GestorMapas(){
        this.mapas = new LinkedHashMap<>();
        LectorMapa.leerMapas().forEach(mapa -> this.mapas.put(mapa.getNombre(), mapa));
        this.mapaActual = this.mapas.values().iterator().next(); 
        this.observers = new ArrayList<>();
    }

    public Mapa getMapaActual() {
        return mapaActual;
    }

    public void setMapaActual(Mapa mapaActual) {
        this.mapaActual = mapaActual;
        notifyObservers();
    }

    public LinkedHashMap<String, Mapa> getMapas() {
        return mapas;
    }

    public void setMapas(LinkedHashMap<String, Mapa> mapas) {
        this.mapas = mapas;
    }
    public void addMapa(Mapa mapa){
        this.mapas.put(mapa.getNombre(), mapa);
        notifyObservers();
    }
    public void removeMapa(String nombreMapa){
        this.mapas.remove(nombreMapa);
        notifyObservers();
    }
    public Mapa getMapa(String nombreMapa){
        return this.mapas.get(nombreMapa);
    }
    public void setMapa(String nombreMapa, Mapa mapa){
        this.mapas.put(nombreMapa, mapa);
        notifyObservers();
    }
    public void clearMapas(){
        this.mapas.clear();
        notifyObservers();
    }
    
    public boolean avanzarAlSiguienteMapa() {
        ArrayList<Mapa> listaMapas = new ArrayList<>(mapas.values());
        int indiceActual = listaMapas.indexOf(mapaActual);
    
        if (indiceActual < listaMapas.size() - 1) {
            mapaActual = listaMapas.get(indiceActual + 1);
            notifyObservers(); 
            return true;
        } else {
            return false;
             
        }
    }

}
