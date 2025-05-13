package com.enriquealberto.Controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;
import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.Heroe;
import com.enriquealberto.model.Juego;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Controlador para la escena de selección de personaje y configuración del juego.
 * Implementa la interfaz Observer para reaccionar a cambios en el modelo del juego.
 */
public class SelectionControlador implements Observer {

    @FXML
    private HBox cont_perso; // Contenedor horizontal para los personajes seleccionables

    @FXML
    private ImageView fd1, fd2, fd3, fd4, fd5; // Imágenes para representar los niveles de dificultad
    private int selectedDifficulty = 0; // 0 significa ninguna seleccionada
    private ArrayList<Heroe> heroes; // Lista de héroes disponibles
    private final double ACTIVE_OPACITY = 1.0; // Opacidad para dificultad seleccionada
    private final double INACTIVE_OPACITY = 0.3; // Opacidad para dificultad no seleccionada
    private boolean isHovering = false; // Indica si el ratón está sobre algún nivel de dificultad
    private int currentHoverLevel = 0; // Nivel de dificultad sobre el que está el ratón

    @FXML
    private TextField nom_jugador; // Campo de texto para el nombre del jugador

    @FXML
    private Label c_nombre; // Etiqueta para mostrar el nombre del jugador
    @FXML
    private Label c_PERSONAJE; // Etiqueta para mostrar el personaje seleccionado
    @FXML
    private ImageView fondoDecorado; // Imagen de fondo decorativo
    @FXML
    private Label c_DIFICULTAD; // Etiqueta para mostrar la dificultad seleccionada
    @FXML
    private Label fallo; // Etiqueta para mostrar mensajes de error
    @FXML
    private Button start; // Botón para iniciar el juego
    @FXML
    private MediaView mediaView; // Vista multimedia para el fondo animado

    @FXML
    private AnchorPane formBackground; // Panel de fondo del formulario

    private Juego juego; // Instancia del modelo del juego

    /**
     * Método llamado cuando hay cambios en el modelo observado.
     * Actualiza las etiquetas de la interfaz con los valores actuales del juego.
     */
    @Override
    public void onChange() {
        c_nombre.setText(juego.getNombre() != null ? juego.getNombre() : "");
        Heroe jugador = juego.getJugador();
        c_PERSONAJE.setText(jugador != null ? jugador.getNombre() : "Sin personaje");
        c_DIFICULTAD.setText(String.valueOf(juego.getDificultad()));
    }

    /**
     * Método de inicialización llamado automáticamente después de cargar el FXML.
     * Configura todos los componentes de la interfaz y sus listeners.
     */
    @FXML
    public void initialize() {
        juego = Juego.getInstance();
        juego.suscribe(this);
        heroes = juego.getHeroes();

        onChange();

        cargarPersonajes(heroes);
        setupDifficultySelector();
        String videoPath = getClass().getResource("/com/enriquealberto/videos/fondo.mp4").toExternalForm();

        // Configuración del video de fondo
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetición infinita
        mediaPlayer.setMute(true); // Silenciar
        mediaPlayer.play();

        // Asignar el MediaPlayer a la MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setPreserveRatio(false); // Que se estire al tamaño completo

        // Configuración de efectos visuales para el video de fondo
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3); // oscurece ligeramente (-1 a 1)
        colorAdjust.setContrast(-0.2);   // reduce el contraste (-1 a 1)

        // Crear desenfoque
        GaussianBlur blur = new GaussianBlur(20); // valor entre 10 y 30 suele verse bien

        // Encadenar efectos: primero blur, luego ajuste de color
        colorAdjust.setInput(blur);

        // Asignar el efecto combinado al MediaView
        mediaView.setEffect(colorAdjust);

        // Ajustar tamaño del video según el tamaño de la ventana
        mediaView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mediaView.fitWidthProperty().bind(newScene.widthProperty());
                mediaView.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        // Listener para el campo de nombre del jugador
        nom_jugador.textProperty().addListener((observable, oldValue, newValue) -> {
            juego.setNombre(newValue);
            System.out.println("Cadena actualizada a: " + newValue);
        });

        try {
            // Configuración del fondo decorado (papiro)
            Image imagen = new Image(getClass().getResource("/com/enriquealberto/imagenes/papiro.png").toExternalForm());
            fondoDecorado.setImage(imagen);
            fondoDecorado.setPreserveRatio(false);
            fondoDecorado.fitWidthProperty().bind(formBackground.widthProperty().multiply(0.8));
            fondoDecorado.fitHeightProperty().bind(formBackground.heightProperty().multiply(0.99));
            formBackground.widthProperty().addListener((obs, oldVal, newVal) -> {
                AnchorPane.setLeftAnchor(fondoDecorado, (formBackground.getWidth() - fondoDecorado.getFitWidth()) / 2);
            });
            formBackground.heightProperty().addListener((obs, oldVal, newVal) -> {
                AnchorPane.setTopAnchor(fondoDecorado, (formBackground.getHeight() - fondoDecorado.getFitHeight()) / 2);
            });
        } catch (Exception e) {
            System.err.println("Error cargando fondo decorado: " + e.getMessage());
            e.printStackTrace();
        }

        // Configuración del botón de inicio
        start.setOnAction(event -> {
            // Verificar que todos los campos estén completados
            if (juego.getNombre() != null && !juego.getNombre().isEmpty() &&
                    juego.getDificultad() != 0 && juego.getJugador() != null) {

                // Cargar la escena CONTENEDOR y luego cargar los paneles
                ManagerEscenas.getInstance().loadScene(EscenaID.CONTENEDOR);

                // Llamar al método para cargar los paneles cuando la escena se ha cargado
                ContenedorControlador controlador = (ContenedorControlador) ManagerEscenas.getInstance().getController(EscenaID.CONTENEDOR);
                controlador.cargarPaneles();

                System.out.println("Dificultad seleccionada: " + juego.getDificultad());
                System.out.println("Jugador seleccionado: " + juego.getJugador().getNombre());
            } else {
                // Mostrar un mensaje de error si algo falta
                fallo.setText("Por favor completa todos los campos");
            }
        });
    }

    /**
     * Configura el selector de dificultad con sus imágenes y eventos.
     */
    private void setupDifficultySelector() {
        // Cargar la imagen de dificultad en todos los ImageView
        Image difficultyImage = new Image(
                getClass().getResource("/com/enriquealberto/imagenes/dificultad.png").toExternalForm());
        fd1.setImage(difficultyImage);
        fd2.setImage(difficultyImage);
        fd3.setImage(difficultyImage);

        // Establecer transparencia inicial
        resetDifficultyOpacity();

        // Configurar eventos para cada imagen de dificultad
        setupDifficultyHoverEvents();
        setupDifficultyClickEvents();
    }

    /**
     * Restablece la opacidad de todas las imágenes de dificultad al valor inactivo.
     */
    private void resetDifficultyOpacity() {
        fd1.setOpacity(INACTIVE_OPACITY);
        fd2.setOpacity(INACTIVE_OPACITY);
        fd3.setOpacity(INACTIVE_OPACITY);
    }

    /**
     * Configura los eventos de hover (ratón sobre) para las imágenes de dificultad.
     */
    private void setupDifficultyHoverEvents() {
        fd1.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 1;
            updateHoverEffect(1);
        });
        fd2.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 2;
            updateHoverEffect(2);
        });
        fd3.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 3;
            updateHoverEffect(3);
        });

        // Cuando el ratón sale de una imagen individual
        fd1.setOnMouseExited(e -> checkHoverStatus());
        fd2.setOnMouseExited(e -> checkHoverStatus());
        fd3.setOnMouseExited(e -> checkHoverStatus());
    }

    /**
     * Verifica si el ratón todavía está sobre alguna imagen de dificultad.
     * Si no está sobre ninguna, actualiza los efectos de selección.
     */
    private void checkHoverStatus() {
        // Verificar si el ratón todavía está en alguna imagen
        isHovering = fd1.isHover() || fd2.isHover() || fd3.isHover();
        if (!isHovering) {
            updateSelectionEffect();
        }
    }

    /**
     * Configura los eventos de clic para las imágenes de dificultad.
     */
    private void setupDifficultyClickEvents() {
        fd1.setOnMouseClicked(e -> setSelectedDifficulty(1));
        fd2.setOnMouseClicked(e -> setSelectedDifficulty(2));
        fd3.setOnMouseClicked(e -> setSelectedDifficulty(3));
    }

    /**
     * Actualiza el efecto visual cuando el ratón pasa sobre los niveles de dificultad.
     * @param hoveredLevel Nivel de dificultad sobre el que está el ratón
     */
    private void updateHoverEffect(int hoveredLevel) {
        resetDifficultyOpacity();

        // Activar opacidad para los niveles hasta el hovered
        if (hoveredLevel >= 1)
            fd1.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 2)
            fd2.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 3)
            fd3.setOpacity(ACTIVE_OPACITY);
    }

    /**
     * Establece la dificultad seleccionada y actualiza el modelo del juego.
     * @param level Nivel de dificultad seleccionado (1-3)
     */
    private void setSelectedDifficulty(int level) {
        selectedDifficulty = level;
        updateSelectionEffect();
        Juego.getInstance().setDificultad(level);
    }

    /**
     * Actualiza el efecto visual según la dificultad seleccionada.
     */
    private void updateSelectionEffect() {
        resetDifficultyOpacity();

        if (selectedDifficulty > 0) {
            // Activar opacidad para los niveles hasta el seleccionado
            if (selectedDifficulty >= 1)
                fd1.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 2)
                fd2.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 3)
                fd3.setOpacity(ACTIVE_OPACITY);
        }
    }

    /**
     * Carga los personajes disponibles en el contenedor de selección.
     * @param personajes Lista de héroes disponibles para seleccionar
     */
    public void cargarPersonajes(List<Heroe> personajes) {
        cont_perso.getChildren().clear();

        for (Heroe p : personajes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
                VBox personajeBox = loader.load();

                Label nombre = (Label) personajeBox.lookup("#p_nombre");
                ImageView foto = (ImageView) personajeBox.lookup("#p_foto");

                nombre.setText(p.getNombre());

                if (p.getImagen() != null) {
                    foto.setImage(new Image(getClass().getResource("/" + p.getImagen()).toExternalForm()));
                }

                asignarAtributo(personajeBox, "vf", p.getVida(), "/com/enriquealberto/imagenes/vida.png");
                asignarAtributo(personajeBox, "af", p.getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
                asignarAtributo(personajeBox, "df", p.getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
                asignarAtributo(personajeBox, "sf", p.getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");
                cont_perso.getChildren().add(personajeBox);

                // Configuración del evento de clic para seleccionar personaje
                personajeBox.setOnMouseClicked(e -> {
                    // Deseleccionar todos los estilos previos
                    cont_perso.getChildren().forEach(node -> node.getStyleClass().remove("selected-personaje"));

                    // Aplicar la clase de estilo al personaje seleccionado
                    personajeBox.getStyleClass().add("selected-personaje");
                    vibrarImagen(foto);

                    // Guardar el personaje seleccionado en el juego
                    Juego.getInstance().setJugador(p.clone());
                    // Actualizar etiquetas
                    onChange();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asigna los atributos visuales a un personaje (vida, ataque, defensa, velocidad).
     * @param personajeBox Contenedor del personaje
     * @param prefijo Prefijo del identificador del atributo
     * @param cantidad Valor numérico del atributo
     * @param iconoRuta Ruta del icono a mostrar
     */
    private void asignarAtributo(VBox personajeBox, String prefijo, int cantidad, String iconoRuta) {
        // Buscamos el contenedor HBox que contiene las imágenes
        HBox contenedor = (HBox) personajeBox.lookup("#" + prefijo.substring(0, 2)); // "vf", "af", etc.
        if (contenedor == null) {
            System.err.println("No se encontró el contenedor para: " + prefijo);
            return;
        }

        // Limpiamos las imágenes existentes (excepto la etiqueta)
        contenedor.getChildren().removeIf(node -> node instanceof ImageView);

        // Cargamos la imagen del icono
        Image icono = new Image(getClass().getResource(iconoRuta).toExternalForm());

        // Añadimos tantas imágenes como indique la cantidad
        for (int i = 0; i < cantidad; i++) {
            ImageView img = new ImageView(icono);
            img.setFitWidth(25);
            img.setFitHeight(25);
            contenedor.getChildren().add(img);
        }
    }

    /**
     * Aplica una animación de vibración a una imagen cuando se selecciona.
     * @param imageView Imagen a la que aplicar la animación
     */
    public void vibrarImagen(ImageView imageView) {
        TranslateTransition vibracionX = new TranslateTransition(Duration.millis(50), imageView);
        vibracionX.setByX(3);
        vibracionX.setCycleCount(4);
        vibracionX.setAutoReverse(true);

        TranslateTransition vibracionY = new TranslateTransition(Duration.millis(50), imageView);
        vibracionY.setByY(3);
        vibracionY.setCycleCount(4);
        vibracionY.setAutoReverse(true);

        vibracionX.play();
        vibracionY.play();
    }
}