package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class PortadaControlador {

    @FXML
    private Button jugar;

    @FXML
    private MediaView mediaView;

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

        // Acción del botón
        jugar.setOnAction(event -> {
            ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION);
        });
    }
}
