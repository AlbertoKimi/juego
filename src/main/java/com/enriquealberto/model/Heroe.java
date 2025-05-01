package com.enriquealberto.model;

import java.util.ArrayList;
import com.enriquealberto.interfaces.Interaccion;

import com.enriquealberto.interfaces.Observer;

public class Heroe extends Personaje implements Interaccion {
    private ArrayList<Observer> observers;

    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.observers = new ArrayList<>();
    }

    public Heroe(String nombre, String imagen, int vida, int ataque, int defensa, int velocidad, int porcentaje, int x,
            int y) {
        super(nombre, imagen, vida, ataque, defensa, velocidad, porcentaje);
        this.observers = new ArrayList<>();
        this.posicion[0] = x;
        this.posicion[1] = y;
    }

    public void suscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(x -> x.onChange());
    }

    public void setPosicion(int x, int y) {
        this.posicion[0] = x;
        this.posicion[1] = y;
        notifyObservers();
    }

    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }

    @Override
    public void atacar(Heroe heroe, Enemigo enemigo) {
        System.out.println("Atacando con " + heroe.getNombre() + " con " + heroe.getAtaque() + " de daño.");
        int dañoSobrante = 0;

        if (enemigo.getDefensa() > 0) {
            // Si la defensa del enemigo es mayor que 0
            if (heroe.getAtaque() > enemigo.getDefensa()) {
                // Si el ataque del héroe es mayor que la defensa del enemigo
                dañoSobrante = heroe.getAtaque() - enemigo.getDefensa();
                enemigo.setDefensa(0); // La defensa del enemigo pasa a 0
                enemigo.setVida(enemigo.getVida() - dañoSobrante); // Se resta el daño sobrante a la vida
                System.out.println("La defensa del enemigo ha sido destruida. Daño sobrante: " + dañoSobrante);
            } else {
                // Si el ataque del héroe no supera la defensa del enemigo
                enemigo.setDefensa(enemigo.getDefensa() - heroe.getAtaque());
                System.out.println("El ataque fue absorbido por la defensa. Defensa restante: " + enemigo.getDefensa());
            }
        } else {
            // Si la defensa del enemigo es 0
            enemigo.setVida(enemigo.getVida() - heroe.getAtaque());
            System.out.println("El enemigo no tiene defensa. Vida restante: " + enemigo.getVida());
        }

        // Verificar si el enemigo ha sido derrotado
        if (enemigo.getVida() <= 0) {
            enemigo.setVida(0);
            System.out.println("El enemigo ha sido derrotado.");
        } else {
            System.out.println("El enemigo tiene " + enemigo.getVida() + " de vida restante.");
        }
    }

    @Override
    public void desplazarse(int x, int y) {

    }

}
