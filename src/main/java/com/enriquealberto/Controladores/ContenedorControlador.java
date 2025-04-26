package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class ContenedorControlador {

    @FXML
    AnchorPane juego;

    @FXML
    AnchorPane estadistica;

    @FXML
    public void initialize() {
        ManagerEscenas sm = ManagerEscenas.getInstance();

        Scene addjuego = sm.getScene(EscenaID.JUEGO);
        AnchorPane.setBottomAnchor(addjuego.getRoot(), 0.0);
        AnchorPane.setTopAnchor(addjuego.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(addjuego.getRoot(), 0.0);
        AnchorPane.setRightAnchor(addjuego.getRoot(), 0.0);
        juego.getChildren().setAll(addjuego.getRoot());

        Scene addest = sm.getScene(EscenaID.ESTADISTICAS);
        AnchorPane.setBottomAnchor(addest.getRoot(), 0.0);
        AnchorPane.setTopAnchor(addest.getRoot(), 0.0);
        AnchorPane.setLeftAnchor(addest.getRoot(), 0.0);
        AnchorPane.setRightAnchor(addest.getRoot(), 0.0);
        estadistica.getChildren().setAll(addest.getRoot());

    }
}
