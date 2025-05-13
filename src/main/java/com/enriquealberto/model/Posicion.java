package com.enriquealberto.model;

/**
 * Clase que representa una posición en un sistema de coordenadas bidimensional.
 * Se utiliza para ubicar personajes y elementos en el mapa del juego.
 * Implementa equals() y hashCode() para permitir su uso como clave en estructuras HashMap.
 */
public class Posicion {
    private int x;  // Coordenada horizontal (columnas)
    private int y;  // Coordenada vertical (filas)

    /**
     * Constructor que crea una nueva posición con las coordenadas especificadas.
     *
     * @param x Coordenada horizontal (columna)
     * @param y Coordenada vertical (fila)
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compara esta posición con otro objeto para determinar igualdad.
     * Dos posiciones son iguales si tienen las mismas coordenadas x e y.
     *
     * @param o Objeto a comparar
     * @return true si son la misma posición, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Misma instancia
        if (!(o instanceof Posicion)) return false;  // No es una Posicion
        Posicion p = (Posicion) o;  // Cast a Posicion
        return x == p.x && y == p.y;  // Comparación de coordenadas
    }

    /**
     * Genera un código hash para esta posición.
     * Este método es consistente con equals() y permite usar Posicion como clave en HashMap.
     *
     * @return Código hash calculado basado en las coordenadas
     */
    @Override
    public int hashCode() {
        return 31 * x + y;  // Fórmula simple para generar hash único
    }

    /**
     * Representación en cadena de la posición en formato "(x,y)".
     *
     * @return Cadena que representa la posición
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Obtiene la coordenada X (horizontal) de la posición.
     *
     * @return Coordenada x
     */
    public int getX() {
        return x;
    }

    /**
     * Establece una nueva coordenada X (horizontal) para la posición.
     *
     * @param x Nueva coordenada x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene la coordenada Y (vertical) de la posición.
     *
     * @return Coordenada y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece una nueva coordenada Y (vertical) para la posición.
     *
     * @param y Nueva coordenada y
     */
    public void setY(int y) {
        this.y = y;
    }
}