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

/**
 * Controlador para la pantalla de portada del juego.
 * Maneja la reproducción del video de fondo, efectos visuales
 * y la transición a la siguiente pantalla al presionar Enter.
 */
public class PortadaControlador {

    @FXML
    private Label mensaje; // Label con mensaje interactivo que parpadea

    @FXML
    private MediaView mediaView; // Componente para mostrar el video de fondo

    @FXML
    private AnchorPane portada; // Panel contenedor principal

    /**
     * Método de inicialización llamado automáticamente por JavaFX.
     * Configura todos los elementos multimedia y efectos visuales.
     */
    @FXML
    public void initialize() {
        configurarVideoFondo();
        configurarEstilos();
        configurarParpadeoTexto();
        configurarPosicionTexto();
        configurarEventosTeclado();
    }

    /**
     * Configura el video de fondo con reproducción en bucle.
     */
    private void configurarVideoFondo() {
        String videoPath = getClass().getResource("/com/enriquealberto/videos/fondo.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setMute(true);
        mediaPlayer.play();

        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setPreserveRatio(false);

        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(newScene.widthProperty());
                mediaView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });
    }

    /**
     * Carga los estilos CSS para la pantalla.
     */
    private void configurarEstilos() {
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(
                        getClass().getResource("/com/enriquealberto/css/Portada_estilo.css").toExternalForm()
                );
            }
        });
    }

    /**
     * Configura el efecto de parpadeo para el mensaje de bienvenida.
     */
    private void configurarParpadeoTexto() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> mensaje.setVisible(false)),
                new KeyFrame(Duration.seconds(1), e -> mensaje.setVisible(true))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Centra dinámicamente el texto cuando cambia el tamaño de la ventana.
     */
    private void configurarPosicionTexto() {
        portada.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            mensaje.setLayoutX((newWidth.doubleValue() - mensaje.getWidth()) / 2 -20);
        });

        portada.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            mensaje.setLayoutY((newHeight.doubleValue() - mensaje.getHeight()) / 2 +200);
        });
    }

    /**
     * Configura el evento para cambiar de pantalla al presionar Enter.
     */
    private void configurarEventosTeclado() {
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                        ManagerEscenas.getInstance().loadScene(EscenaID.HISTORIA);
                    }
                });
            }
        });

        portada.setFocusTraversable(true);
        portada.requestFocus();
    }
}