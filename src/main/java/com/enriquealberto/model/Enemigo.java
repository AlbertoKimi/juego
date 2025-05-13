package com.enriquealberto.model;

import com.enriquealberto.interfaces.Interaccion;

/**
 * Clase que representa un enemigo en el juego, hereda de Personaje e implementa
 * las interfaces Cloneable e Interaccion. Define el comportamiento y atributos
 * específicos de los enemigos.
 *
 * @author Enrique
 * @author Alberto
 */
public class Enemigo extends Personaje implements Cloneable, Interaccion {

    private int t_enemigo; // Tipo de enemigo
    private int percepcion; // Rango de percepción del enemigo

    /**
     * Constructor para crear un nuevo enemigo.
     *
     * @param nombre Nombre del enemigo
     * @param imagen Ruta de la imagen del enemigo
     * @param vida Puntos de vida del enemigo
     * @param ataque Puntos de ataque del enemigo
     * @param defensa Puntos de defensa del enemigo
     * @param velocidad Velocidad del enemigo
     * @param porcentaje Porcentaje de aparición del enemigo
     * @param t_enemigo Tipo de enemigo
     * @param percepcion Rango de percepción del enemigo
     */
    public Enemigo(String nombre, String imagen, int vida, int ataque, int defensa,
                   int velocidad, int porcentaje, int t_enemigo, int percepcion) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.t_enemigo = t_enemigo;
        this.percepcion = percepcion;
    }

    /**
     * Obtiene el rango de percepción del enemigo.
     *
     * @return El valor de percepción del enemigo
     */
    public int getPercepcion() {
        return percepcion;
    }

    /**
     * Establece el rango de percepción del enemigo.
     *
     * @param percepcion Nuevo valor de percepción
     */
    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    /**
     * Establece los puntos de vida del enemigo.
     *
     * @param vida Nuevo valor de vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene el tipo de enemigo.
     *
     * @return El tipo de enemigo
     */
    public int getT_enemigo() {
        return t_enemigo;
    }

    /**
     * Establece el tipo de enemigo.
     *
     * @param t_enemigo Nuevo tipo de enemigo
     */
    public void setT_enemigo(int t_enemigo) {
        this.t_enemigo = t_enemigo;
    }

    /**
     * Implementación del método de ataque definido en la interfaz Interaccion.
     * Realiza un ataque contra un héroe, considerando defensa y vida.
     *
     * @param heroe El héroe que recibe el ataque
     * @param enemigo El enemigo que realiza el ataque
     */
    @Override
    public void atacar(Heroe heroe, Enemigo enemigo) {
        System.out.println("Atacando con " + enemigo.getNombre() + " con " + enemigo.getAtaque() + " de daño.");
        int dañoSobrante = 0;

        if (heroe.getDefensa() > 0 && enemigo.getAtaque() > 0) {
            // Si la defensa del héroe es mayor que 0
            if (enemigo.getAtaque() > heroe.getDefensa()) {
                // Si el ataque del enemigo es mayor que la defensa del héroe
                dañoSobrante = enemigo.getAtaque() - heroe.getDefensa();
                heroe.setDefensa(0);
                if (dañoSobrante > 0) {
                    System.out.println("La defensa del héroe ha sido destruida. Daño sobrante: " + dañoSobrante);
                    heroe.setVida(heroe.getVida() - dañoSobrante); // Se resta el daño sobrante a la vida
                    if (heroe.getVida() <= 0) {
                        heroe.setVida(0); // Asegurarse de que la vida no sea negativa
                        System.out.println("El héroe no tiene vida.");
                    } else {
                        System.out.println("Vida restante del héroe: " + heroe.getVida());
                    }
                } else {
                    System.out.println("La defensa ha parado el golpe.");
                }

            } else if (enemigo.getAtaque() == heroe.getDefensa()) {
                // Si el ataque del enemigo es igual a la defensa del héroe
                heroe.setDefensa(0);
                System.out.println("La defensa del héroe ha sido destruida. No hay daño sobrante.");
                System.out.println("Vida restante del héroe: " + heroe.getVida());
            } else {
                // Si el ataque del enemigo no supera la defensa del héroe
                heroe.setDefensa(heroe.getDefensa() - enemigo.getAtaque());
                System.out.println("El ataque fue absorbido por la defensa. Defensa restante: " + heroe.getDefensa());
            }
        } else {
            // Si la defensa del héroe es 0
            heroe.setVida(heroe.getVida() - enemigo.getAtaque());
            if (heroe.getVida() <= 0) {
                heroe.setVida(0); // Asegurarse de que la vida no sea negativa
                System.out.println("El héroe no tiene vida.");
            } else {
                System.out.println("El héroe no tiene defensa. Vida restante: " + heroe.getVida());
            }
        }
    }

    /**
     * Implementación del método clone para permitir la clonación de enemigos.
     *
     * @return Una copia del enemigo actual
     * @throws AssertionError Si la clonación no es soportada
     */
    @Override
    public Enemigo clone() {
        try {
            Enemigo clone = (Enemigo) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}