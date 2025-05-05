package com.enriquealberto;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;



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

        // Registrar todas las escenas disponibles (sin cargar CONTENEDOR)
        sm.setScene(EscenaID.PORTADA, "Portada");
        sm.setScene(EscenaID.SELECTION, "selection");
        sm.setScene(EscenaID.CONTENEDOR, "contenedor"); // Solo configurar, no cargar todavía

        // Cargar la PORTADA primero (pantalla inicial)
        sm.loadScene(EscenaID.PORTADA);
    }

    public static void main(String[] args) {
        launch();
    }
}