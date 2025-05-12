package com.enriquealberto.model;

import java.util.*;

import com.enriquealberto.Lectores.LectorHeroes;
import com.enriquealberto.Lectores.LectorMostruo;
import com.enriquealberto.interfaces.Observer;

public class Juego {
    private static Juego instance;
    private ArrayList<Observer> observers;
    private ArrayList<Heroe> heroes;
    private ArrayList<Enemigo> enemigos;
    private String nombre;
    private int turnoIndex = 0;

    private GestorMapas gestorMapas;

    private ArrayList<Personaje> entidades;
    private Map<Posicion, Personaje> entidadesMapa = new HashMap<>();
    private int dificultad;
    private Heroe jugador;
    private ArrayList<Enemigo> enemigosF = new ArrayList<>();
    private ArrayList<Enemigo> enemigosM = new ArrayList<>();
    private ArrayList<Enemigo> enemigosD = new ArrayList<>();
    private Mapa mapaActual;
    private int[][] MatrizMapa;

    public Juego() {
        this.observers = new ArrayList<>();
        this.heroes = LectorHeroes.leerHeroes();
        this.enemigos = LectorMostruo.leerMostruo();
        clasificarEnemigos();
        this.jugador = null;
        this.gestorMapas = new GestorMapas();
        this.mapaActual = gestorMapas.getMapaActual();
        this.MatrizMapa = mapaActual.getMapa(); // Asegúrate que sea int[][]
        this.entidades = new ArrayList<>();
    }

    public static Juego getInstance() {
        if (instance == null) {
            instance = new Juego();
        }
        return instance;
    }

    public static void setInstance(Juego instance) {
        Juego.instance = instance;
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

    public void setJugador(Heroe jugador) {
        this.jugador = jugador;
        notifyObservers();
    }

    public ArrayList<Heroe> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Heroe> heroes) {
        this.heroes = heroes;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public Heroe getJugador() {
        return jugador;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
        notifyObservers();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    public void clasificarEnemigos() {
        for (Enemigo enemigo : enemigos) {
            switch (enemigo.getT_enemigo()) {
                case 1:
                    enemigosF.add(enemigo);
                    break;
                case 2:
                    enemigosM.add(enemigo);
                    break;
                case 3:
                    enemigosD.add(enemigo);
                    break;
            }
        }
    }

    public void iniciarentidades() {
        Random random = new Random();
        entidades.clear();
        entidadesMapa.clear();
        jugador.setPosicion(new Posicion(0, 0));
        entidades.add(jugador);
        entidadesMapa.put(jugador.getPosicion(), jugador);
        int cantidadF = (int) Math.ceil((2.0 + mapaActual.getNivel()) / 2.0) + (dificultad - 1);
        int cantidadM = (int) (mapaActual.getNivel() / 2.0) + dificultad;
        int cantidadD = (int) (mapaActual.getNivel() / 2.0) + (dificultad - 2);
        for (int i = 0; i < cantidadF; i++) {
            Enemigo copia = enemigosF.get(random.nextInt(enemigosF.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);
        }
        for (int i = 0; i < cantidadM; i++) {
            Enemigo copia = enemigosM.get(random.nextInt(enemigosM.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);
        }
        for (int i = 0; i < cantidadD; i++) {
            Enemigo copia = enemigosD.get(random.nextInt(enemigosD.size())).clone();
            colocarEnemigoEnPosicionAleatoria(copia);
            entidades.add(copia);
        }
        Collections.sort(entidades);
    }

    public void colocarEnemigoEnPosicionAleatoria(Enemigo enemigo) {
        Random random = new Random();
        int x, y;
        do {
            y = random.nextInt(MatrizMapa.length);
            x = random.nextInt(MatrizMapa[0].length);
        } while (comprobarposicion(x, y) != 2);
        enemigo.setPosicion(new Posicion(x, y));
        entidadesMapa.put(enemigo.getPosicion(), enemigo);
    }

    public boolean moverDerecha(Personaje p) {
        return mover(p, 1, 0);
    }

    public boolean moverIzquierda(Personaje p) {
        return mover(p, -1, 0);
    }

    public boolean moverArriba(Personaje p) {
        return mover(p, 0, -1);
    }

    public boolean moverAbajo(Personaje p) {
        return mover(p, 0, 1);
    }

    public int comprobarposicion(int x, int y) {
        if (x < 0 || x >= MatrizMapa[0].length || y < 0 || y >= MatrizMapa.length) {
            return 0;
        }
        if (MatrizMapa[y][x] == 1) {
            return 0;
        }
        if (entidadesMapa.containsKey(new Posicion(x, y))) {
            return 1;
        }
        if (MatrizMapa[y][x] == 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public boolean mover(Personaje p, int x, int y) {
        Posicion antigua = p.getPosicion();
        int xNueva = antigua.getX() + x;
        int yNueva = antigua.getY() + y;
        int resultado = comprobarposicion(xNueva, yNueva);

        switch (resultado) {
            case 0:
                System.out.println("Colisión de personaje: " + p.getNombre());

                // Verificar si es un héroe y estamos en los niveles 3 o 5
                if (p instanceof Heroe) {
                    Heroe heroe = (Heroe) p;

                    if (mapaActual.getNivel() == 3 || mapaActual.getNivel() == 5) {
                        // Verificar si la colisión es contra una pared
                        if (xNueva >= 0 && xNueva < MatrizMapa[0].length && yNueva >= 0 && yNueva < MatrizMapa.length) {
                            if (MatrizMapa[yNueva][xNueva] == 1) { // 1 representa una pared
                                int vidaPerdida = (mapaActual.getNivel() == 3) ? 1 : 2; // 1 vida en nivel 3, 2 vidas en
                                                                                        // nivel 5
                                heroe.setVida(heroe.getVida() - vidaPerdida);

                                // Mensajes personalizados según el nivel
                                if (mapaActual.getNivel() == 3) {
                                    System.out.println(
                                            "El héroe " + heroe.getNombre() + " se ha caído al agua y ha perdido "
                                                    + vidaPerdida + " de vida. Su vida es " + heroe.getVida());
                                } else if (mapaActual.getNivel() == 5) {
                                    System.out.println(
                                            "El héroe " + heroe.getNombre() + " ha caído a la lava y ha perdido "
                                                    + vidaPerdida + " de vida. Su vida es " + heroe.getVida());
                                }
                            }
                        }
                    }

                    // Verificar si el héroe ha sido derrotado
                    if (heroe.getVida() <= 0) {
                        System.out.println("El héroe " + heroe.getNombre() + " ha sido derrotado.");
                        entidadesMapa.remove(antigua); // Eliminar al héroe del mapa
                        entidades.remove(heroe); // Eliminar al héroe de la lista de entidades
                        if (verificarDerrota()) {
                            notifyObservers();
                        }
                    }
                }
                return false;

            case 1:
                // Si hay un enemigo en la posición a la que intenta moverse
                Posicion posicionEnemigo = new Posicion(xNueva, yNueva);
                Personaje enemigo = entidadesMapa.get(posicionEnemigo);

                if (enemigo instanceof Enemigo && p instanceof Heroe) {
                    Heroe heroe = (Heroe) p;
                    Enemigo enemigoAtacado = (Enemigo) enemigo;

                    // El héroe ataca al enemigo
                    heroe.atacar(heroe, enemigoAtacado);

                    // Verificar si el enemigo ha sido derrotado
                    if (enemigoAtacado.getVida() <= 0) {
                        System.out.println("El enemigo " + enemigoAtacado.getNombre() + " ha sido derrotado.");
                        entidadesMapa.remove(posicionEnemigo); // Eliminar al enemigo del mapa
                        entidades.remove(enemigoAtacado); // Eliminar al enemigo de la lista de entidades
                        if (verificarVictoria()) {
                            ganarVida(heroe);
                            notifyObservers();
                        } else {
                            ganarVida(heroe);
                        }
                    }
                }
                return true;

            case 2:
                // Movimiento válido, actualizar la posición
                Posicion nueva = new Posicion(xNueva, yNueva);
                p.setPosicion(nueva);
                entidadesMapa.remove(antigua);
                entidadesMapa.put(nueva, p);
                return true;

            default:
                return false;
        }
    }

    public void ganarVida(Personaje p) {
        if (p instanceof Heroe) {
            Heroe heroe = (Heroe) p;
            if (heroe.getVida() < 10) {
                heroe.setVida(heroe.getVida() + 1);
                System.out.println("El Héroe ha ganado uno de vida. Su vida es " + heroe.getVida());
            } else {
                System.out.println("El Héroe no puede ganar más vida. Su vida es " + heroe.getVida());
            }
        }
    }

    public void moverAleatorio(Personaje p) {
        Random rand = new Random();
        boolean movido = false;
        do {
            int numero = rand.nextInt(4);
            switch (numero) {
                case 0:
                    movido = moverAbajo(p);
                    break;
                case 1:
                    movido = moverArriba(p);
                    break;
                case 2:
                    movido = moverDerecha(p);
                    break;
                case 3:
                    movido = moverIzquierda(p);
                    break;
            }
        } while (!movido);
    }

    public void moverGuiado(Personaje p) {
        Posicion posActual = p.getPosicion();
        Posicion posJugador = jugador.getPosicion();

        int dx = posJugador.getX() - posActual.getX();
        int dy = posJugador.getY() - posActual.getY();
        boolean movido = false;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                movido = moverDerecha(p);
                if (!movido)
                    movido = dy > 0 ? moverAbajo(p) : moverArriba(p);
            } else {
                movido = moverIzquierda(p);
                if (!movido)
                    movido = dy > 0 ? moverAbajo(p) : moverArriba(p);
            }
        } else {
            if (dy > 0) {
                movido = moverAbajo(p);
                if (!movido)
                    movido = dx > 0 ? moverDerecha(p) : moverIzquierda(p);
            } else {
                movido = moverArriba(p);
                if (!movido)
                    movido = dx > 0 ? moverDerecha(p) : moverIzquierda(p);
            }
        }

        if (!movido)
            moverAleatorio(p);
    }

    public void moverenemigo(Enemigo e) {
        Posicion posActual = e.getPosicion();
        Posicion posJugador = jugador.getPosicion();

        int dx = posJugador.getX() - posActual.getX();
        int dy = posJugador.getY() - posActual.getY();

        if ((Math.abs(dx) == 1 && Math.abs(dy) == 0) || (Math.abs(dx) == 0 && Math.abs(dy) == 1)) {

            e.atacar(jugador, e);
            if (jugador.getVida() <= 0) {
                System.out.println("El heroe " + jugador.getNombre() + " ha sido derrotado.");
                entidadesMapa.remove(posJugador); // Eliminar al heroe del mapa
                entidades.remove(jugador); // Eliminar al heroe de la lista de entidades
                if (verificarDerrota()) {
                    notifyObservers();
                }
            }

        } else {
            if (Math.abs(dx) <= e.getPercepcion() && Math.abs(dy) <= e.getPercepcion()) {
                moverGuiado(e);
            } else {
                moverAleatorio(e);
            }
        }
    }

    public boolean verificarVictoria() {
        for (Personaje p : entidadesMapa.values()) {
            if (p instanceof Enemigo) {
                return false; // Si hay al menos un enemigo, no hay victoria
            }
        }
        // Si no quedan enemigos, cambiar al siguiente mapa
        System.out.println("¡Victoria! Cambiando al siguiente mapa...");
        boolean haySiguienteMapa = gestorMapas.avanzarAlSiguienteMapa();
        if (haySiguienteMapa) {
            mapaActual = gestorMapas.getMapaActual(); // Actualizar el mapa actual
            MatrizMapa = mapaActual.getMapa(); // Actualizar la matriz del mapa
            iniciarentidades(); // Reiniciar las entidades en el nuevo mapa

        } else {
            System.out.println("¡Has completado todos los mapas! Fin del juego.");
        }

        return true;
    }

    public boolean verificarDerrota() {
        for (Personaje p : entidadesMapa.values()) {
            if (p instanceof Heroe) {
                return false; // Si hay heroe, no hay derrota
            }
        }

        return true;
    }

    public GestorMapas getGestorMapas() {
        return gestorMapas;
    }

    public void setGestorMapas(GestorMapas gestorMapas) {
        this.gestorMapas = gestorMapas;
    }

    public ArrayList<Personaje> getEntidades() {
        return entidades;
    }

    public void setEntidades(ArrayList<Personaje> entidades) {
        this.entidades = entidades;
    }

    public Personaje getPersonajeActual() {
        if (entidades.isEmpty())
            return null;
        return entidades.get(turnoIndex);
    }

    public void pasarTurno() {
        if (entidades.isEmpty())
            return;
        turnoIndex = (turnoIndex + 1) % entidades.size(); // Actualiza primero
        notifyObservers(); // Luego notifica
    }

    public void resetearJuego() {
        this.gestorMapas.clearMapas();
        this.gestorMapas = new GestorMapas();
        this.mapaActual = gestorMapas.getMapaActual();
        this.MatrizMapa = mapaActual.getMapa(); // Asegúrate que sea int[][]
        this.entidades = new ArrayList<>();
        for (Heroe p : heroes) {
            if (p.getNombre().equals(jugador.getNombre())) {
                jugador = p.clone();

            }
        }
        iniciarentidades();
    }

}
