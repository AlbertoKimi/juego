package com.enriquealberto.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.enriquealberto.Lectores.LectorMapa;
import com.enriquealberto.interfaces.Observer;

/**
 * Clase que gestiona los mapas del juego, implementando el patrón Observer
 * para notificar cambios en los mapas. Permite añadir, eliminar, obtener
 * y cambiar entre mapas del juego.
 *
 * @author Enrique
 * @author Alberto
 */
public class GestorMapas {
    private LinkedHashMap<String, Mapa> mapas; // Colección de mapas indexados por nombre
    private Mapa mapaActual; // Mapa actualmente activo
    private ArrayList<Observer> observers; // Lista de observadores registrados

    /**
     * Constructor que inicializa el gestor de mapas, cargando los mapas
     * disponibles a través del LectorMapa y estableciendo el primer mapa
     * como mapa actual.
     */
    public GestorMapas() {
        this.mapas = new LinkedHashMap<>();
        LectorMapa.leerMapas().forEach(mapa -> this.mapas.put(mapa.getNombre(), mapa));
        this.mapaActual = this.mapas.values().iterator().next();
        this.observers = new ArrayList<>();
    }

    /**
     * Registra un observador para recibir notificaciones de cambios.
     *
     * @param observer Observador a registrar
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observador de la lista de notificaciones.
     *
     * @param observer Observador a eliminar
     */
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio.
     */
    public void notifyObservers() {
        observers.forEach(item -> item.onChange());
    }

    /**
     * Obtiene el mapa actualmente activo.
     *
     * @return El mapa actual
     */
    public Mapa getMapaActual() {
        return mapaActual;
    }

    /**
     * Establece un nuevo mapa como actual y notifica a los observadores.
     *
     * @param mapaActual Nuevo mapa a establecer como actual
     */
    public void setMapaActual(Mapa mapaActual) {
        this.mapaActual = mapaActual;
        notifyObservers();
    }

    /**
     * Obtiene todos los mapas disponibles.
     *
     * @return Colección de mapas indexados por nombre
     */
    public LinkedHashMap<String, Mapa> getMapas() {
        return mapas;
    }

    /**
     * Establece una nueva colección de mapas y notifica a los observadores.
     *
     * @param mapas Nueva colección de mapas
     */
    public void setMapas(LinkedHashMap<String, Mapa> mapas) {
        this.mapas = mapas;
        notifyObservers();
    }

    /**
     * Añade un nuevo mapa a la colección y notifica a los observadores.
     *
     * @param mapa Mapa a añadir
     */
    public void addMapa(Mapa mapa) {
        this.mapas.put(mapa.getNombre(), mapa);
        notifyObservers();
    }

    /**
     * Elimina un mapa por nombre y notifica a los observadores.
     *
     * @param nombreMapa Nombre del mapa a eliminar
     */
    public void removeMapa(String nombreMapa) {
        this.mapas.remove(nombreMapa);
        notifyObservers();
    }

    /**
     * Obtiene un mapa por su nombre.
     *
     * @param nombreMapa Nombre del mapa a obtener
     * @return El mapa correspondiente o null si no existe
     */
    public Mapa getMapa(String nombreMapa) {
        return this.mapas.get(nombreMapa);
    }

    /**
     * Reemplaza o añade un mapa por nombre y notifica a los observadores.
     *
     * @param nombreMapa Nombre del mapa a establecer
     * @param mapa Mapa a asociar con el nombre
     */
    public void setMapa(String nombreMapa, Mapa mapa) {
        this.mapas.put(nombreMapa, mapa);
        notifyObservers();
    }

    /**
     * Limpia todos los mapas y notifica a los observadores.
     */
    public void clearMapas() {
        this.mapas.clear();
        notifyObservers();
    }

    /**
     * Avanza al siguiente mapa en la secuencia si existe.
     *
     * @return true si se pudo avanzar al siguiente mapa, false si ya se estaba
     *         en el último mapa
     */
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