package com.enriquealberto.Controladores;

import java.net.URL;

import com.enriquealberto.EscenaID;
import javafx.fxml.FXML;
import com.enriquealberto.ManagerEscenas;


import com.enriquealberto.model.Juego;

import javafx.scene.control.Button;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;

public class DerrotaControlador {
    @FXML
    private MediaView mediaView;

    @FXML
    private AnchorPane portada;

    /*@FXML
    Label titulo;*/

    @FXML
    Button botonVolverJugar;

    @FXML
    Button botonSelect;

    @FXML
    public void initialize() {
        // Esperar a que la escena esté disponible
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                URL cssUrl = getClass().getResource("/com/enriquealberto/css/botones_estilo.css");
                if (cssUrl == null) {
                    System.err.println("El archivo CSS no se encontró en la ruta especificada.");
                } else {
                    portada.getScene().getStylesheets().add(cssUrl.toExternalForm());
                }
            }
        });

        // Cargar video desde resources
        String videoPath = getClass().getResource("/com/enriquealberto/videos/Derrota.mp4").toExternalForm();

        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetición infinita
        mediaPlayer.setMute(true); // Silenciar
        mediaPlayer.play();

        // Asignar el MediaPlayer a la MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setPreserveRatio(false); // Que se estire al tamaño completo

        // Ajustar tamaño del video según el tamaño de la ventana
        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(newScene.widthProperty());
                mediaView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        botonVolverJugar.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();

            Juego.getInstance().resetearJuego(); // Limpia estado anterior

            manager.removeScene(EscenaID.CONTENEDOR);
            manager.setScene(EscenaID.CONTENEDOR, "contenedor");
            manager.loadScene(EscenaID.CONTENEDOR);

            // Cargar paneles internos del contenedor
            ContenedorControlador controladorContenedor = (ContenedorControlador) manager
                    .getController(EscenaID.CONTENEDOR);
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
