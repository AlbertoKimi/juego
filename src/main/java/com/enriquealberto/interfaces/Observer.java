package com.enriquealberto.interfaces;

/**
 * Interfaz que define el contrato para el patrón Observer.
 * Permite a los objetos suscritos recibir notificaciones cuando ocurren cambios
 * en el sujeto observado.
 */
public interface Observer {

    /**
     * Método llamado cuando ocurre un cambio en el sujeto observado.
     * Las clases implementadoras deben definir la lógica de respuesta a estos cambios.
     */
    public void onChange();
}