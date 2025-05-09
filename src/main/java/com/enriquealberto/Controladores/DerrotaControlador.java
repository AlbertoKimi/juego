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

        botonVolverJugar.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();

            Juego.getInstance().resetearJuego(); // Limpia estado anterior

            manager.removeScene(EscenaID.CONTENEDOR);
            manager.setScene(EscenaID.CONTENEDOR, "contenedor");
            manager.loadScene(EscenaID.CONTENEDOR);

            // Cargar paneles internos del contenedor
            ContenedorControlador controladorContenedor = (ContenedorControlador) manager.getController(EscenaID.CONTENEDOR);
            if (controladorContenedor != null) {
                controladorContenedor.cargarPaneles();
            } else {
                System.err.println("No se pudo obtener el controlador del contenedor.");
            }
        });

        botonSelect.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();

            Juego.getInstance().resetearJuego();

            manager.removeScene(EscenaID.SELECTION);
            manager.setScene(EscenaID.SELECTION, "selection");
            manager.loadScene(EscenaID.SELECTION);


        });
    }
}
