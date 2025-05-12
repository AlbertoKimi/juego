package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class PortadaControlador {

    @FXML
    private Label mensaje; // Label que mostrará "PULSA ENTER"

    @FXML
    private MediaView mediaView;

    @FXML
    private AnchorPane portada; // Este es el AnchorPane que contiene todos los elementos

    @FXML
    public void initialize() {
        // Cargar video desde resources
        String videoPath = getClass().getResource("/com/enriquealberto/videos/fondo.mp4").toExternalForm();

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

        // Cargar el archivo CSS
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass().getResource("/com/enriquealberto/css/Portada_estilo.css").toExternalForm());
            }
        });

        // Configurar el parpadeo del Label
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), e -> mensaje.setVisible(false)),
            new KeyFrame(Duration.seconds(1), e -> mensaje.setVisible(true))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play();

        // Centrar el Label en la ventana
        portada.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            mensaje.setLayoutX((newWidth.doubleValue() - mensaje.getWidth()) / 2 -100);
        });

        portada.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            mensaje.setLayoutY((newHeight.doubleValue() - mensaje.getHeight()) / 2 +180);
        });

        // Detectar la pulsación de la tecla Enter en la escena
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    System.out.println("Tecla presionada: " + event.getCode());
                    switch (event.getCode()) {
                        case ENTER:
                            System.out.println("Cambiando a la escena SELECTION...");
                            ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION);
                            break;
                        default:
                            break;
                    }
                });
            }
        });

        // Solicitar el enfoque para que el evento de teclado funcione
        portada.setFocusTraversable(true);
        portada.requestFocus();
    }
}
