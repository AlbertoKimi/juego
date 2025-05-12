package com.enriquealberto.model;


import com.enriquealberto.interfaces.Interaccion;

public class Heroe extends Personaje implements Interaccion,Cloneable {
  

    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        
    }

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
                }else{
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
    public Heroe clone() {
        try {
            Heroe clone = (Heroe) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
