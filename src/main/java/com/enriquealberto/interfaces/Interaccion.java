package com.enriquealberto.interfaces;

import com.enriquealberto.model.Enemigo;
import com.enriquealberto.model.Heroe;

public interface Interaccion {
    public void atacar(Heroe heroe, Enemigo enemigo);
    public void desplazarse(int x, int y);
}
