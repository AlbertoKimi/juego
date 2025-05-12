package com.enriquealberto.model;

import com.enriquealberto.interfaces.Interaccion;


public class Enemigo extends Personaje implements Cloneable, Interaccion {

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
