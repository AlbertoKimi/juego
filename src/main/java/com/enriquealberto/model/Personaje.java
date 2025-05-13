package com.enriquealberto.model;

/**
 * Clase abstracta que representa un personaje en el juego.
 * Define las propiedades y comportamientos básicos que comparten todos los personajes (héroes y enemigos).
 * Implementa Comparable para permitir la ordenación de personajes según su velocidad y porcentaje.
 */
public abstract class Personaje implements Comparable<Personaje> {
    protected String nombre;      // Nombre identificativo del personaje
    protected String imagen;     // Ruta o identificador de la imagen/representación visual
    protected int vida;          // Puntos de vida actuales (0 = personaje derrotado)
    protected int ataque;        // Poder de ataque del personaje
    protected int defensa;       // Capacidad de defensa contra ataques
    protected int velocidad;     // Determina orden de turnos (mayor velocidad actúa primero)
    protected int porcentaje;    // Porcentaje usado como criterio de desempate para turnos
    protected Posicion posicion; // Posición actual del personaje en el mapa

    /**
     * Constructor base para todos los personajes.
     *
     * @param nombre Nombre del personaje
     * @param imagen Ruta o identificador de la imagen/representación visual
     * @param vida Puntos de vida iniciales
     * @param ataque Valor de ataque inicial
     * @param defensa Valor de defensa inicial
     * @param velocidad Valor de velocidad inicial (afecta orden de turnos)
     * @param porcentaje Porcentaje inicial (usado como criterio secundario para orden de turnos)
     */
    public Personaje(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.porcentaje = porcentaje;
    }

    // Métodos getters y setters con documentación

    /**
     * Obtiene el nombre del personaje.
     * @return Nombre del personaje
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el personaje.
     * @param nombre Nuevo nombre del personaje
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ruta o identificador de la imagen del personaje.
     * @return Ruta/identificador de la imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece una nueva imagen para el personaje.
     * @param imagen Nueva ruta/identificador de la imagen
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene los puntos de vida actuales del personaje.
     * @return Puntos de vida actuales (0 indica que el personaje está derrotado)
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece los puntos de vida del personaje.
     * @param vida Nuevo valor de vida (debe manejarse para no ser negativo)
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene el valor de ataque del personaje.
     * @return Valor de ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece un nuevo valor de ataque para el personaje.
     * @param ataque Nuevo valor de ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Obtiene el valor de defensa del personaje.
     * @return Valor de defensa
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece un nuevo valor de defensa para el personaje.
     * @param defensa Nuevo valor de defensa
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Obtiene el valor de velocidad del personaje.
     * @return Valor de velocidad (determina orden de turnos)
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece un nuevo valor de velocidad para el personaje.
     * @param velocidad Nuevo valor de velocidad
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Obtiene el porcentaje del personaje (usado como criterio secundario para orden de turnos).
     * @return Valor de porcentaje
     */
    public int getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece un nuevo porcentaje para el personaje.
     * @param porcentaje Nuevo valor de porcentaje
     */
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene la posición actual del personaje en el mapa.
     * @return Objeto Posicion con las coordenadas actuales
     */
    public Posicion getPosicion() {
        return posicion;
    }

    /**
     * Establece una nueva posición para el personaje en el mapa.
     * @param posicion Nueva posición del personaje
     */
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    /**
     * Implementación de compareTo para ordenar personajes.
     * Ordena primero por velocidad (mayor primero) y luego por porcentaje (mayor primero) en caso de empate.
     *
     * @param otro Otro personaje con el que comparar
     * @return Valor negativo si este personaje va primero, positivo si va después, 0 si son iguales
     */
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