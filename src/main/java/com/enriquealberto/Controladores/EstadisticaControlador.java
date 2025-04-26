package com.enriquealberto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;

import com.enriquealberto.interfaces.Observer;

public class EstadisticaControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Button pasarNivel;

    @FXML
    public void initialize() {

        /* pasarNivel.setOnAction(event -> cargarSiguienteMapa()); */
    }

    @Override
    public void onChange() {
        System.out.println("El mapa ha cambiado");
    }
}
