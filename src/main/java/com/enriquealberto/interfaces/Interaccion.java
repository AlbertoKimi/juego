package com.enriquealberto.interfaces;

import com.enriquealberto.model.Enemigo;
import com.enriquealberto.model.Heroe;

/**
 * Interfaz que define el contrato para las interacciones de combate entre personajes.
 * Especialmente para implementar la mecánica de ataque entre héroes y enemigos.
 */
public interface Interaccion {

    /**
     * Método que define la acción de ataque de un héroe contra un enemigo.
     * Las clases que implementen esta interfaz deben proporcionar la lógica concreta
     * del cálculo de daño y efectos del ataque.
     *
     * @param heroe El héroe que realiza el ataque
     * @param enemigo El enemigo que recibe el ataque
     */
    public void atacar(Heroe heroe, Enemigo enemigo);
}