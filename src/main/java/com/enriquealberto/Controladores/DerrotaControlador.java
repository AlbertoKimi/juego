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
 * Controlador para la escena de derrota del juego.
 * Maneja la visualización de un video de derrota y proporciona opciones
 * para volver a jugar o regresar a la selección de personaje.
 */
public class DerrotaControlador {
    @FXML
    private MediaView mediaView;       // Componente para mostrar el video de derrota
    @FXML
    private AnchorPane portada;        // Panel contenedor principal
    @FXML
    private Button botonVolverJugar;   // Botón para reiniciar el juego
    @FXML
    private Button botonSelect;        // Botón para regresar a selección de personaje

    /**
     * Método de inicialización llamado automáticamente por JavaFX.
     * Configura los estilos CSS, carga el video de derrota y establece los manejadores de eventos.
     */
    @FXML
    public void initialize() {
        configurarEstilosCSS();
        configurarVideoDerrota();
        configurarAccionesBotones();
    }

    /**
     * Configura los estilos CSS para la escena.
     * Espera a que la escena esté disponible antes de aplicar los estilos.
     */
    private void configurarEstilosCSS() {
        portada.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                URL cssUrl = getClass().getResource("/com/enriquealberto/css/botones_estilo.css");
                if (cssUrl == null) {
                    System.err.println("Error: Archivo CSS no encontrado en /com/enriquealberto/css/botones_estilo.css");
                } else {
                    newScene.getStylesheets().add(cssUrl.toExternalForm());
                }
            }
        });
    }

    /**
     * Configura y reproduce el video de derrota.
     * El video se ajusta automáticamente al tamaño de la ventana y se reproduce en bucle.
     */
    private void configurarVideoDerrota() {
        try {
            String videoPath = getClass().getResource("/com/enriquealberto/videos/Derrota.mp4").toExternalForm();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setMute(true);
            mediaPlayer.play();

            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setPreserveRatio(false);

            // Ajuste responsive del video
            mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    mediaView.fitWidthProperty().bind(newScene.widthProperty());
                    mediaView.fitHeightProperty().bind(newScene.heightProperty());
                }
            });
        } catch (Exception e) {
            System.err.println("Error al cargar el video de derrota: " + e.getMessage());
        }
    }

    /**
     * Configura las acciones para los botones de la interfaz.
     * - botonVolverJugar: Reinicia el juego y carga la escena principal
     * - botonSelect: Regresa a la pantalla de selección de personaje
     */
    private void configurarAccionesBotones() {
        // Acción para reiniciar el juego
        botonVolverJugar.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();
            reiniciarJuego(manager);

            // Recargar escena principal
            manager.removeScene(EscenaID.CONTENEDOR);
            manager.setScene(EscenaID.CONTENEDOR, "contenedor");
            manager.loadScene(EscenaID.CONTENEDOR);

            // Cargar paneles internos
            cargarPanelesContenedor(manager);
        });

        // Acción para regresar a selección de personaje
        botonSelect.setOnAction(event -> {
            ManagerEscenas manager = ManagerEscenas.getInstance();
            reiniciarJuego(manager);

            manager.removeScene(EscenaID.SELECTION);
            manager.setScene(EscenaID.SELECTION, "selection");
            manager.loadScene(EscenaID.SELECTION);
        });
    }

    /**
     * Reinicia el estado del juego.
     */
    private void reiniciarJuego(ManagerEscenas manager) {
        Juego.getInstance().resetearJuego();
    }

    /**
     * Carga los paneles internos del contenedor principal.
     *
     * @param manager Instancia del administrador de escenas
     */
    private void cargarPanelesContenedor(ManagerEscenas manager) {
        ContenedorControlador controladorContenedor = (ContenedorControlador) manager
                .getController(EscenaID.CONTENEDOR);
        if (controladorContenedor != null) {
            controladorContenedor.cargarPaneles();
        } else {
            System.err.println("Error: Controlador del contenedor no disponible");
        }
    }
}