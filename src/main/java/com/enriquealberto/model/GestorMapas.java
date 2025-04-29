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
    
    //¿POSIBLE MÉTODO PARA SELECCIONAR EL SIGUIENTE MAPA?

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

    /*public boolean avanzarAlSiguienteMapa() {
        // Obtener el iterador de los mapas
        var iterador = mapas.values().iterator();
        boolean haySiguiente = false;
        
        // Buscar el mapa actual y avanzar al siguiente
        while (iterador.hasNext()) {
            Mapa mapa = iterador.next();
            if (mapa.equals(mapaActual)) {
                if (iterador.hasNext()) {
                    mapaActual = iterador.next();
                    haySiguiente = true;
                } else {
                    haySiguiente = false; // No hay más mapas disponibles
                }
                break;
            }
        }
        
        notifyObservers(); // Notificar a los observadores sobre el cambio
        return haySiguiente;
    }*/
}
