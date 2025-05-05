package com.enriquealberto.model;

public abstract class Personaje implements Comparable<Personaje> {
    protected String nombre;
    protected String imagen ;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int porcentaje;
    protected Posicion posicion;

    public Personaje(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    @Override
    public int compareTo(Personaje otro) {
        // Primero comparamos por velocidad (mayor primero)
        if (this.velocidad != otro.velocidad) {
            return Integer.compare(otro.velocidad, this.velocidad); // Orden descendente
        } else {
            // Si empatan en velocidad, comparamos por porcentaje (mayor primero)
            return Integer.compare(otro.porcentaje, this.porcentaje); // Orden descendente
        }
    }
    
}
