package com.enriquealberto.Controladores;

import com.enriquealberto.model.Personaje;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.*;
import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.Juego;

/**
 * Controlador FXML para la vista de estadísticas del juego.
 * Muestra información detallada del jugador y estado de todos los personajes.
 * Implementa Observer para actualizarse automáticamente cuando cambia el estado del juego.
 */
public class EstadisticaControlador implements Observer {

    @FXML
    AnchorPane anchorPane; // Panel raíz de la vista

    @FXML
    ImageView fondoDecorado; // Vista de imagen para el fondo decorativo

    @FXML
    VBox cont_principal; // Contenedor principal vertical

    @FXML
    private Label lblTituloJugador; // Etiqueta para mostrar el nombre del jugador

    @FXML
    private VBox contenedorJugador; // Contenedor para la ficha del jugador principal

    @FXML
    private FlowPane contenedorVidas; // Contenedor para las vidas de todos los personajes

    private Juego juego; // Referencia al modelo del juego
    private final Map<String, Image> imagenesCache = new HashMap<>(); // Cache para almacenar imágenes cargadas

    /**
     * Método de inicialización llamado automáticamente por JavaFX.
     * Configura la conexión con el modelo, el fondo y carga los datos iniciales.
     */
    @FXML
    public void initialize() {
        juego = Juego.getInstance();
        if (juego.getJugador() == null) {
            System.err.println("ERROR: El héroe no ha sido inicializado.");
            return;
        }
        juego.suscribe(this); // Suscribirse como observador del juego
        Image imagen = new Image(getClass().getResource("/com/enriquealberto/imagenes/fondoesta.png").toExternalForm());
        fondoDecorado.setImage(imagen);
        fondoDecorado.fitWidthProperty().bind(anchorPane.widthProperty()); // Ajuste responsive
        fondoDecorado.fitHeightProperty().bind(anchorPane.heightProperty()); // Ajuste responsive
        lblTituloJugador.setText(juego.getNombre());
        cargarPersonajes(); // Cargar datos del jugador
        cargarVidas(); // Cargar vidas de los personajes
    }

    /**
     * Carga y muestra la información del personaje jugador.
     * Utiliza una plantilla FXML para mostrar los datos.
     */
    public void cargarPersonajes() {
        contenedorJugador.getChildren().clear(); // Limpiar contenedor

        try {
            // Cargar plantilla FXML para el personaje
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
            VBox personajeBox = loader.load();
            personajeBox.setId("personajeJugador");

            // Configurar nombre e imagen
            Label nombre = (Label) personajeBox.lookup("#p_nombre");
            ImageView foto = (ImageView) personajeBox.lookup("#p_foto");
            nombre.setText(juego.getJugador().getNombre());

            if (juego.getJugador().getImagen() != null) {
                foto.setImage(cargarImagen("/" + juego.getJugador().getImagen()));
            }

            // Asignar atributos con iconos visuales
            asignarAtributo(personajeBox, "vf", juego.getJugador().getVida(), "/com/enriquealberto/imagenes/vida.png");
            asignarAtributo(personajeBox, "af", juego.getJugador().getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
            asignarAtributo(personajeBox, "df", juego.getJugador().getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
            asignarAtributo(personajeBox, "sf", juego.getJugador().getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");

            contenedorJugador.getChildren().add(personajeBox);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo básico de errores
        }
    }

    /**
     * Carga y muestra las vidas de todos los personajes en el juego.
     * Resalta el personaje cuyo turno es actual.
     */
    public void cargarVidas() {
        contenedorVidas.getChildren().clear(); // Limpiar contenedor
        List<Personaje> personajes = juego.getEntidades(); // Obtener todos los personajes
        Personaje actual = juego.getPersonajeActual(); // Personaje con turno actual

        for (Personaje p : personajes) {
            try {
                // Cargar plantilla FXML para mini personaje
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/min_perso.fxml"));
                HBox personajeBox = loader.load();
                personajeBox.setPrefWidth(250); // Tamaño fijo para organización en grid

                // Resaltar personaje actual
                if (p.equals(actual)) {
                    personajeBox.getStyleClass().add("selected-personaje");
                }

                // Configurar imagen del personaje
                ImageView foto = (ImageView) personajeBox.lookup("#im_per");
                if (p.getImagen() != null) {
                    foto.setImage(cargarImagen("/" + p.getImagen()));
                }
                foto.setFitWidth(40);
                foto.setFitHeight(40);

                // Configurar indicador de vida
                Image icono = cargarImagen("/com/enriquealberto/imagenes/vida.png");
                HBox corazones = new HBox(3);
                corazones.setAlignment(Pos.CENTER_LEFT);

                // Mostrar iconos de vida o contador según cantidad
                if (p.getVida() <= 5) {
                    for (int i = 0; i < p.getVida(); i++) {
                        ImageView img = new ImageView(icono);
                        img.setFitWidth(16);
                        img.setFitHeight(16);
                        corazones.getChildren().add(img);
                    }
                } else {
                    ImageView img = new ImageView(icono);
                    img.setFitWidth(16);
                    img.setFitHeight(16);
                    Label contador = new Label("×" + p.getVida());
                    contador.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
                    corazones.getChildren().addAll(img, contador);
                }

                personajeBox.getChildren().add(corazones);
                contenedorVidas.getChildren().add(personajeBox);

            } catch (IOException e) {
                e.printStackTrace(); // Manejo básico de errores
            }
        }
    }

    /**
     * Carga una imagen desde recursos, usando caché para mejorar rendimiento.
     * @param ruta Ruta relativa de la imagen en los recursos
     * @return Objeto Image cargado
     */
    private Image cargarImagen(String ruta) {
        return imagenesCache.computeIfAbsent(ruta, r -> new Image(getClass().getResource(r).toExternalForm()));
    }

    /**
     * Asigna un atributo visual a un personaje usando iconos.
     * @param personajeBox Contenedor del personaje
     * @param prefijo Prefijo del ID en el FXML
     * @param cantidad Valor del atributo a mostrar
     * @param iconoRuta Ruta del icono a usar
     */
    private void asignarAtributo(VBox personajeBox, String prefijo, int cantidad, String iconoRuta) {
        String nombreAtributo;
        switch (prefijo) {
            case "vf": nombreAtributo = "vida"; break;
            case "af": nombreAtributo = "ataque"; break;
            case "df": nombreAtributo = "defensa"; break;
            case "sf": nombreAtributo = "velocidad"; break;
            default: nombreAtributo = null; break;
        }

        if (nombreAtributo == null) return;

        HBox contenedorPadre = (HBox) personajeBox.lookup("#p_" + nombreAtributo);
        HBox contenedorImagenes = (HBox) personajeBox.lookup("#" + prefijo);

        if (contenedorPadre == null || contenedorImagenes == null) return;

        contenedorImagenes.getChildren().removeIf(node -> node instanceof ImageView);

        if (prefijo.equals("vf")) {
            manejarVidaMultiFila(contenedorPadre, contenedorImagenes, cantidad, iconoRuta);
        } else {
            agregarIconos(contenedorImagenes, cantidad, iconoRuta);
        }
    }

    /**
     * Maneja la visualización de la vida cuando requiere múltiples filas.
     */
    private void manejarVidaMultiFila(HBox contenedorPadre, HBox primeraFila, int cantidad, String iconoRuta) {
        primeraFila.getChildren().clear();
        Image icono = cargarImagen(iconoRuta);

        if (cantidad <= 5) {
            for (int i = 0; i < cantidad; i++) {
                ImageView img = new ImageView(icono);
                img.setFitWidth(20);
                img.setFitHeight(20);
                primeraFila.getChildren().add(img);
            }
        } else {
            ImageView img = new ImageView(icono);
            img.setFitWidth(20);
            img.setFitHeight(20);
            Label contador = new Label("×" + cantidad);
            contador.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            primeraFila.getChildren().addAll(img, contador);
        }
    }

    /**
     * Agrega múltiples iconos para representar un atributo.
     */
    private void agregarIconos(HBox contenedor, int cantidad, String iconoRuta) {
        Image icono = cargarImagen(iconoRuta);
        for (int i = 0; i < cantidad; i++) {
            ImageView img = new ImageView(icono);
            img.setFitWidth(20);
            img.setFitHeight(20);
            contenedor.getChildren().add(img);
        }
    }

    /**
     * Método de actualización llamado cuando el juego notifica cambios.
     */
    @Override
    public void onChange() {
        cargarPersonajes();
        cargarVidas();
    }
}