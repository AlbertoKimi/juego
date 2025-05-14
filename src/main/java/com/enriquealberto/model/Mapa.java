package com.enriquealberto.model;

/**
 * Clase que representa un mapa del juego.
 * Contiene la estructura del mapa, su apariencia visual y propiedades específicas.
 */
public class Mapa {
    private String nombre;      // Nombre descriptivo del mapa
    private String suelo;       // Textura o representación del suelo
    private String pared;       // Textura o representación de las paredes/obstáculos
    private String trampa;       // Fondo del mapa (no utilizado actualmente)
    private int nivel;         // Número de nivel asociado al mapa
    private int[][] mapa;      // Matriz que representa la estructura del mapa (0 = suelo, 1 = pared)

    /**
     * Constructor completo para crear un nuevo mapa.
     *
     * @param nivel Número de nivel del mapa (determina dificultad y posición en la progresión del juego)
     * @param nombre Nombre descriptivo del mapa
     * @param suelo Representación visual del suelo
     * @param pared Representación visual de las paredes/obstáculos
     * @param mapa Matriz bidimensional que define la estructura del mapa (0 para áreas transitables, 1 para obstáculos)
     */
    public Mapa(int nivel, String nombre, String suelo, String pared, String trampa,int[][] mapa) {
        this.nivel = nivel;
        this.nombre = nombre;
        this.suelo = suelo;
        this.pared = pared;
        this.trampa =trampa;
        this.mapa = mapa;
    }

    /**
     * Obtiene el nombre del mapa.
     * @return Nombre del mapa
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece un nuevo nombre para el mapa.
     * @param nombre Nuevo nombre del mapa
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la representación visual del suelo.
     * @return Cadena que representa el suelo
     */
    public String getSuelo() {
        return this.suelo;
    }

    /**
     * Establece una nueva representación visual para el suelo.
     * @param suelo Nueva representación del suelo
     */
    public void setSuelo(String suelo) {
        this.suelo = suelo;
    }

    /**
     * Obtiene la representación visual de las paredes/obstáculos.
     * @return Cadena que representa las paredes
     */
    public String getPared() {
        return this.pared;
    }

    /**
     * Establece una nueva representación visual para las paredes.
     * @param pared Nueva representación de las paredes
     */
    public void setPared(String pared) {
        this.pared = pared;
    }

    /**
     * Obtiene la matriz que representa la estructura del mapa.
     * @return Matriz bidimensional donde 0 = área transitable y 1 = obstáculo
     */
    public int[][] getMapa() {
        return this.mapa;
    }

    /**
     * Establece una nueva matriz para la estructura del mapa.
     * @param mapa Nueva matriz del mapa (debe seguir el formato 0 = transitable, 1 = obstáculo)
     */
    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    /**
     * Obtiene el número de nivel asociado al mapa.
     * @return Número de nivel (indica dificultad y orden de progresión)
     */
    public int getNivel() {
        return this.nivel;
    }

     public String getTrampa() {
        return this.trampa;
    }

    public void setTrampa(String trampa) {
        this.pared = trampa;
    }
}

    

