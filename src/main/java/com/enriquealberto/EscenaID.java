package com.enriquealberto;

/**
 * Enumeración que define los identificadores únicos para las diferentes escenas/pantallas
 * de la aplicación. Cada constante representa una escena específica en el juego.
 *
 * @author Enrique
 * @author Alberto
 */
public enum EscenaID {
    /** Escena de portada/presentación del juego */
    PORTADA,
    /** Escena de selección de personaje o nivel */
    SELECTION,
    /** Escena principal donde transcurre el juego */
    JUEGO,
    /** Escena contenedora de otros elementos del juego */
    CONTENEDOR,
    /** Escena que muestra estadísticas del juego */
    ESTADISTICAS,
    /** Escena que se muestra cuando el jugador gana */
    VICTORIA,
    /** Escena que se muestra cuando el jugador pierde */
    DERROTA,
    /** Escena que muestra la historia o narrativa del juego */
    HISTORIA
}