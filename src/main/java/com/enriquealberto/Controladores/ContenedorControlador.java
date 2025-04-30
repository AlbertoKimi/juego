package com.enriquealberto.Controladores;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class ContenedorControlador {

    @FXML
    private AnchorPane juego;

    @FXML
    private AnchorPane estadistica;

    @FXML
    public void initialize() {
        try {
            // Cargar Juego.fxml directamente (sin usar ManagerEscenas)
            FXMLLoader juegoLoader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/Juego.fxml"));
            Parent juegoRoot = juegoLoader.load();
            
            // Ajustar anclas para que ocupe todo el espacio
            AnchorPane.setTopAnchor(juegoRoot, 0.0);
            AnchorPane.setBottomAnchor(juegoRoot, 0.0);
            AnchorPane.setLeftAnchor(juegoRoot, 0.0);
            AnchorPane.setRightAnchor(juegoRoot, 0.0);
            
            juego.getChildren().setAll(juegoRoot); // Añadir al panel izquierdo

            // Cargar Estadisticas.fxml directamente
            FXMLLoader estLoader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/estadisticas.fxml"));
            Parent estRoot = estLoader.load();
            
            // Ajustar anclas para que ocupe todo el espacio
            AnchorPane.setTopAnchor(estRoot, 0.0);
            AnchorPane.setBottomAnchor(estRoot, 0.0);
            AnchorPane.setLeftAnchor(estRoot, 0.0);
            AnchorPane.setRightAnchor(estRoot, 0.0);
            
            estadistica.getChildren().setAll(estRoot); // Añadir al panel derecho

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar los paneles internos del contenedor.");
        }
    }
}