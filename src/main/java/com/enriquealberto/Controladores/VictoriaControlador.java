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

/**
 * Controlador para la escena de victoria del juego.
 * Maneja la visualización de la pantalla de victoria y las acciones de los botones.
 */
public class VictoriaControlador {
    @FXML
    private MediaView mediaView; // Vista multimedia para el video de victoria

    @FXML
    private AnchorPane portada; // Panel principal que contiene los elementos de la escena

    @FXML
    Button botonVolverJugar; // Botón para reiniciar el juego

    @FXML
    Button botonSelect; // Botón para volver a la selección de personaje

    /**
     * Método de inicialización llamado automáticamente después de cargar el FXML.
     * Configura el video de fondo y los eventos de los botones.
     */
    @FXML
    public void initialize() {
        // Esperar a que la escena esté disponible para cargar el CSS
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

        // Cargar video de victoria desde resources
        String videoPath = getClass().getResource("/com/enriquealberto/videos/Victoria.mp4").toExternalForm();

        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Reproducción en bucle
        mediaPlayer.setMute(true); // Silenciar el video
        mediaPlayer.play();

        // Asignar el MediaPlayer a la MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setPreserveRatio(false); // Ajustar al tamaño del contenedor

        // Ajustar tamaño del video según el tamaño de la ventana
        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(newScene.widthProperty());
                mediaView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        // Configuración del botón "Volver a Jugar"
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

        // Configuración del botón "Selección de Personaje"
        botonSelect.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();

            Juego.getInstance().resetearJuego();

            manager.removeScene(EscenaID.SELECTION);
            manager.setScene(EscenaID.SELECTION, "selection");
            manager.loadScene(EscenaID.SELECTION);
        });
    }
}