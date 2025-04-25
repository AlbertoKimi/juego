package com.enriquealberto.model;

public class Proveedor {
    private static Proveedor instance;
    private GestorMapas gestorMapas;

    private Proveedor() {
        this.gestorMapas = new GestorMapas();
    }

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    public GestorMapas getGestorMapas() {
        return gestorMapas;
    }

    public void setGestorMapas(GestorMapas gestorMapas) {
        this.gestorMapas = gestorMapas;
    }
}
