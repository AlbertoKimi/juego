package com.enriquealberto.Controladores;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controlador para la escena de historia/introducción del juego.
 * Muestra una narrativa visual con efectos especiales y transiciones.
 */
public class HistoriaControlador {

    @FXML
    AnchorPane historia; // Panel raíz de la vista

    private TextFlow textFlow; // Contenedor para el texto narrativo

    /**
     * Método de inicialización llamado automáticamente por JavaFX.
     * Configura todos los elementos visuales y efectos de la escena.
     */
    @FXML
    public void initialize() {
        // Configuración del fondo de pergamino
        configurarFondoPergamino();

        // Configuración del video del portal (oculto inicialmente)
        MediaView mediaView = configurarVideoPortal();

        // Configuración del texto narrativo
        configurarTextoHistoria();

        // Configuración de efectos visuales y transición
        configurarEfectosVisuales(mediaView);
    }

    /**
     * Configura la imagen de fondo del pergamino.
     */
    private void configurarFondoPergamino() {
        Image fondoImagen = new Image(getClass().getResourceAsStream("/com/enriquealberto/imagenes/Pergamino.png"));
        ImageView fondo = new ImageView(fondoImagen);
        fondo.setPreserveRatio(false);
        fondo.setCache(true); // Optimización de rendimiento

        // Ajuste responsive del fondo
        fondo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                fondo.fitWidthProperty().bind(newScene.widthProperty());
                fondo.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        historia.getChildren().add(fondo);
    }

    /**
     * Configura el video del portal que se mostrará posteriormente.
     * @return MediaView configurado
     */
    private MediaView configurarVideoPortal() {
        Media videoPortal = new Media(getClass().getResource("/com/enriquealberto/videos/portal.mp4").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(videoPortal);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Ajuste responsive del video
        mediaView.fitWidthProperty().bind(historia.widthProperty());
        mediaView.fitHeightProperty().bind(historia.heightProperty());
        mediaView.setPreserveRatio(false);
        mediaView.setVisible(false); // Oculto inicialmente

        historia.getChildren().add(mediaView);
        return mediaView;
    }

    /**
     * Configura el texto narrativo de la historia con fragmentos interactivos.
     */
    private void configurarTextoHistoria() {
        textFlow = new TextFlow();
        textFlow.getStyleClass().add("text-flow"); // Estilo CSS

        // Fragmentos de la historia
        String[] fragmentos = {
                "Una noche tranquila, mientras la luna velaba el cielo estrellado, la princesa del reino dormía profundamente en su habitación.\n\n",
                "Pero de pronto, unos susurros siniestros y pasos furtivos rompieron el silencio.\n\n",
                "A la mañana siguiente, el rey fue a despertar a su hija… y la encontró desaparecida.\n\n",
                "Las cortinas agitadas, la corona caída… señales de un rapto.\n\n",
                "La alarma se extendió por todo Frutaquia. El reino está en peligro.\n\n",
                "Sin perder tiempo, el rey convoca a sus héroes más valientes: el mago piña, el ninja fruta del dragón, el tanque sandía y el guerrero chirimoya.\n\n",
                "Su misión: encontrar a la princesa y devolver la paz al reino.\n\n",
                "Así comienza la gran aventura...\n\n"
        };

        // Crear nodos Text interactivos para cada fragmento
        for (String fragmento : fragmentos) {
            Text text = new Text(fragmento);
            text.setOnMouseEntered(event -> text.setStyle("-fx-opacity: 1;"));
            textFlow.getChildren().add(text);
        }

        // Posicionamiento del texto
        AnchorPane.setTopAnchor(textFlow, 0.0);
        AnchorPane.setBottomAnchor(textFlow, 0.0);
        AnchorPane.setLeftAnchor(textFlow, 0.0);
        AnchorPane.setRightAnchor(textFlow, 0.0);
        historia.getChildren().add(textFlow);
    }

    /**
     * Configura los efectos visuales adicionales y la transición final.
     * @param mediaView MediaView del portal para la transición
     */
    private void configurarEfectosVisuales(MediaView mediaView) {
        historia.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Cargar CSS
                cargarEstilos(newScene);

                // Configurar cursor personalizado
                configurarCursor(newScene);

                // Configurar efecto de antorcha
                configurarEfectoAntorcha(newScene);

                // Configurar interacción final
                configurarInteraccionFinal(mediaView);
            }
        });
    }

    /**
     * Carga los estilos CSS para la escena.
     */
    private void cargarEstilos(javafx.scene.Scene newScene) {
        String cssPath = getClass().getResource("/com/enriquealberto/css/historia.css").toExternalForm();
        if (cssPath != null) {
            newScene.getStylesheets().add(cssPath);
        }
    }

    /**
     * Configura el cursor personalizado de antorcha.
     */
    private void configurarCursor(javafx.scene.Scene newScene) {
        Image cursorImage = new Image(
                getClass().getResourceAsStream("/com/enriquealberto/imagenes/Antorcha.png"));
        newScene.setCursor(new javafx.scene.ImageCursor(cursorImage));
    }

    /**
     * Configura el efecto visual de luz de antorcha.
     */
    private void configurarEfectoAntorcha(javafx.scene.Scene newScene) {
        Circle foco = new Circle(60);
        foco.setFill(new javafx.scene.paint.RadialGradient(
                0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.rgb(255, 255, 200, 0.4)),
                new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.rgb(255, 255, 200, 0.1))
        ));
        foco.setEffect(new javafx.scene.effect.GaussianBlur(40));
        foco.setMouseTransparent(true);
        historia.getChildren().add(foco);

        // Seguimiento del ratón
        newScene.setOnMouseMoved(event -> {
            foco.setCenterX(event.getSceneX());
            foco.setCenterY(event.getSceneY());
        });
    }

    /**
     * Configura la interacción final para cambiar de escena.
     */
    private void configurarInteraccionFinal(MediaView mediaView) {
        historia.setOnMouseClicked(event -> {
            if (esTodoTextoVisible()) {
                mostrarPortalYTransicion(mediaView);
            }
        });
    }

    /**
     * Verifica si todo el texto está visible.
     */
    private boolean esTodoTextoVisible() {
        return textFlow.getChildren().stream()
                .filter(node -> node instanceof Text)
                .allMatch(node -> "-fx-opacity: 1;".equals(node.getStyle()));
    }

    /**
     * Muestra el portal y configura la transición a la siguiente escena.
     */
    private void mostrarPortalYTransicion(MediaView mediaView) {
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        mediaView.setVisible(true);
        mediaPlayer.play();

        // Reordenar para que el video esté al frente
        historia.getChildren().remove(mediaView);
        historia.getChildren().add(mediaView);

        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.pause());

        // Configurar clic en el video para cambiar de escena
        mediaView.setOnMouseClicked(e -> {
            ManagerEscenas sm = ManagerEscenas.getInstance();
            sm.loadScene(EscenaID.SELECTION);
        });
    }
}
