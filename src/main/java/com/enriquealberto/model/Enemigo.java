package com.enriquealberto.model;

import java.util.ArrayList;

import com.enriquealberto.interfaces.Interaccion;
import com.enriquealberto.interfaces.Observer;

public class Enemigo extends Personaje implements Cloneable {

     private int t_enemigo;
     private int percepcion;

    public Enemigo(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje, int t_enemigo, int percepcion) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.t_enemigo = t_enemigo;
        this.percepcion = percepcion;
    }

   public int getPercepcion() {
        return percepcion;
   }
   public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
   }

    public void setVida(int vida) {
        this.vida = vida;

    }
    public int getT_enemigo() {
        return t_enemigo;
    }

    public void setT_enemigo(int t_enemigo) {
        this.t_enemigo = t_enemigo;
    }


    //@Override
    public void atacar(Heroe heroe, Enemigo enemigo) {
        System.out.println("Atacando con " + enemigo.getNombre() + " con " + enemigo.getAtaque() + " de daño.");
        int dañoSobrante = 0;

        if (heroe.getDefensa() > 0) {
            // Si la defensa del h es mayor que 0
            if (enemigo.getAtaque() > heroe.getDefensa()) {
                // Si el ataque del héroe es mayor que la defensa del enemigo
                dañoSobrante = enemigo.getAtaque() - heroe.getDefensa();
                heroe.setDefensa(0); // La defensa del enemigo pasa a 0
                heroe.setVida(heroe.getVida() - dañoSobrante); // Se resta el daño sobrante a la vida
                System.out.println("La defensa del heroe ha sido destruida. Daño sobrante: " + dañoSobrante);
            } else {
                // Si el ataque del héroe no supera la defensa del enemigo
                heroe.setDefensa(heroe.getDefensa() - enemigo.getAtaque());
                System.out.println("El ataque fue absorbido por la defensa. Defensa restante: " + heroe.getDefensa());
            }
        } else {
            // Si la defensa del enemigo es 0
            heroe.setVida(heroe.getVida() - enemigo.getAtaque());
            System.out.println("El heroe no tiene defensa. Vida restante: " + heroe.getVida());
        }

        // Verificar si el enemigo ha sido derrotado
        if (heroe.getVida() <= 0) {
            heroe.setVida(0);
            System.out.println("El heroe ha sido derrotado.");
        } else {
            System.out.println("El heroe tiene " + heroe.getVida() + " de vida restante.");
        }
    }


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
