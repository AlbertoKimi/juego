package com.enriquealberto;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
     

        // Establece el título de la ventana
        stage.setTitle("Mazmorras frutales");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/com/enriquealberto/imagenes/icono.png").toExternalForm()));

        // Inicialización del ManagerEscenas
        ManagerEscenas sm = ManagerEscenas.getInstance();
        sm.init(stage);

        // Registrar TODAS las escenas disponibles (incluyendo Portada y Selection)
        sm.setScene(EscenaID.PORTADA, "Portada");
        sm.setScene(EscenaID.SELECTION, "selection");
        sm.setScene(EscenaID.CONTENEDOR, "contenedor"); // Este carga Juego + Estadísticas internamente

        // Cargar la PORTADA primero (pantalla inicial)
        sm.loadScene(EscenaID.CONTENEDOR);
    }

    public static void main(String[] args) {
        launch();
    }
}