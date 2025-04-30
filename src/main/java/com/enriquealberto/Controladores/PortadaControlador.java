package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class PortadaControlador {

    @FXML
    private Button jugar;

    @FXML
    private MediaView mediaView;

    @FXML
    private AnchorPane portada; // Este es el AnchorPane que contiene todos los elementos

    @FXML
    public void initialize() {
        
        // Cargar video desde resources
        String videoPath = getClass().getResource("/com/enriquealberto/imagenes/fondo.mp4").toExternalForm();

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

        // Hacer que el botón se ajuste al tamaño de la ventana
        portada.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            jugar.setLayoutX(newWidth.doubleValue() / 2 - jugar.getPrefWidth() / 2);
        });
        
        portada.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            jugar.setLayoutY(newHeight.doubleValue() / 2 - jugar.getPrefHeight() / 2);
        });

        // Acción del botón
        jugar.setOnAction(event -> {
            ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION);
        });
    }
}
