package com.enriquealberto.model;

import com.enriquealberto.interfaces.Interaccion;

/**
 * Clase que representa al héroe del juego, hereda de Personaje e implementa
 * las interfaces Interaccion y Cloneable. Define el comportamiento y atributos
 * específicos del héroe, incluyendo su lógica de ataque.
 *
 * @author Enrique
 * @author Alberto
 */
public class Heroe extends Personaje implements Interaccion, Cloneable {

    /**
     * Constructor para crear un nuevo héroe.
     *
     * @param nombre Nombre del héroe
     * @param imagen Ruta de la imagen del héroe
     * @param vida Puntos de vida iniciales
     * @param ataque Puntos de ataque iniciales
     * @param defensa Puntos de defensa iniciales
     * @param velocidad Valor de velocidad inicial
     * @param porcentaje Porcentaje de éxito en acciones
     */
    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa,
                 int velocidad, int porcentaje) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
    }

    /**
     * Implementación del método de ataque definido en la interfaz Interaccion.
     * Realiza un ataque contra un enemigo, considerando defensa y vida.
     *
     * @param heroe El héroe que realiza el ataque (normalmente será this)
     * @param enemigo El enemigo que recibe el ataque
     */
    @Override
    public void atacar(Heroe heroe, Enemigo enemigo) {
        System.out.println("Atacando con " + heroe.getNombre() + " con " + heroe.getAtaque() + " de daño.");
        int dañoSobrante = 0;

        if (enemigo.getDefensa() > 0 && heroe.getAtaque() > 0) {
            // Si la defensa del enemigo es mayor que 0
            if (heroe.getAtaque() > enemigo.getDefensa()) {
                // Si el ataque del héroe es mayor que la defensa del enemigo
                dañoSobrante = heroe.getAtaque() - enemigo.getDefensa();
                enemigo.setDefensa(0);
                if (dañoSobrante > 0) {
                    System.out.println("La defensa del enemigo ha sido destruida. Daño sobrante: " + dañoSobrante);
                    enemigo.setVida(enemigo.getVida() - dañoSobrante); // Se resta el daño sobrante a la vida
                    if (enemigo.getVida() <= 0) {
                        enemigo.setVida(0); // Asegurarse de que la vida no sea negativa
                        System.out.println("El enemigo no tiene vida.");
                    } else {
                        System.out.println("Vida restante del enemigo: " + enemigo.getVida());
                    }
                } else {
                    System.out.println("La defensa ha parado el golpe.");
                }

            } else if (heroe.getAtaque() == enemigo.getDefensa()) {
                // Si el ataque del héroe es igual a la defensa del enemigo
                enemigo.setDefensa(0);
                System.out.println("La defensa del enemigo ha sido destruida. No hay daño sobrante.");
                System.out.println("Vida restante del enemigo: " + enemigo.getVida());
            } else {
                // Si el ataque del héroe no supera la defensa del enemigo
                enemigo.setDefensa(enemigo.getDefensa() - heroe.getAtaque());
                System.out.println("El ataque fue absorbido por la defensa. Defensa restante: " + enemigo.getDefensa());
            }
        } else {
            // Si la defensa del enemigo es 0
            enemigo.setVida(enemigo.getVida() - heroe.getAtaque());
            if (enemigo.getVida() <= 0) {
                enemigo.setVida(0); // Asegurarse de que la vida no sea negativa
                System.out.println("El enemigo no tiene vida.");
            } else {
                System.out.println("El enemigo no tiene defensa. Vida restante: " + enemigo.getVida());
            }
        }
    }

    /**
     * Implementación del método clone para permitir la clonación del héroe.
     *
     * @return Una copia del héroe actual
     * @throws AssertionError Si la clonación no es soportada
     */
    @Override
    public Heroe clone() {
        try {
            Heroe clone = (Heroe) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}