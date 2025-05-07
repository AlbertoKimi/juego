package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import javafx.fxml.FXML;
import com.enriquealberto.ManagerEscenas;
import com.enriquealberto.model.GestorMapas;
import com.enriquealberto.model.Juego;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class DerrotaControlador {
    @FXML
    VBox vbox;

    @FXML
    Label titulo;

    @FXML
    HBox hbox;

    @FXML
    Button botonVolverJugar;

    @FXML
    Button botonSelect;

    @FXML
    public void initialize() {
        /*Juego juego = Juego.getInstance();*/
        
        botonVolverJugar.setOnAction(event -> {
            /*juego.reiniciarMapas();*/
            if (ManagerEscenas.getInstance().getCurrentScene() != EscenaID.CONTENEDOR) {
                ManagerEscenas.getInstance().removeScene(EscenaID.DERROTA); // Descarga la vista actual
                ManagerEscenas.getInstance().loadScene(EscenaID.CONTENEDOR); // Carga la nueva vista
            }
        });
        botonSelect.setOnAction(event -> {
            /*juego.reiniciarMapas();*/
            if (ManagerEscenas.getInstance().getCurrentScene() != EscenaID.SELECTION) {
                ManagerEscenas.getInstance().removeScene(EscenaID.DERROTA); // Descarga la vista actual
                ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION); // Carga la nueva vista
            }
        });
    }
}
