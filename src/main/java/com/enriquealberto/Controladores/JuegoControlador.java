package com.enriquealberto.Controladores;


import java.util.LinkedHashMap;
import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;
import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Controlador principal para la pantalla de juego.
 * Maneja la visualización del mapa, movimiento de personajes y lógica de turnos.
 * Implementa Observer para reaccionar a cambios en el estado del juego.
 */
public class JuegoControlador implements Observer {

    // Elementos FXML inyectados
    @FXML private StackPane rootStackPane;  // Panel raíz que contiene todos los elementos
    @FXML private ImageView fondoView;      // Vista para la imagen de fondo

    // Componentes internos
    private GridPane gridPane;      // Grid para mostrar el mapa
    private GestorMapas gestorMapas;// Gestor de mapas del juego
    private VBox vbox;              // Contenedor vertical para título y grid
    private Label titulo;           // Etiqueta para mostrar el nivel actual
    private Juego juego;           // Referencia al modelo del juego
    private Heroe heroe;            // Referencia al héroe controlado por el jugador

    /**
     * Método de inicialización llamado automáticamente por JavaFX.
     * Configura los elementos visuales, eventos y estado inicial del juego.
     */
    @FXML
    public void initialize() {
        // Inicialización del modelo del juego
        juego = Juego.getInstance();
        heroe = juego.getJugador();
        if (heroe == null) {
            System.err.println("ERROR: El héroe no ha sido inicializado en ventana juego.");
            return;
        }

        // Configuración del observer y entidades
        juego.suscribe(this);
        juego.iniciarentidades();
        gestorMapas = juego.getGestorMapas();

        // Configuración del fondo
        Image fondo = new Image(getClass().getResourceAsStream("/com/enriquealberto/imagenes/fondojuego.png"));
        fondoView.setImage(fondo);
        fondoView.fitWidthProperty().bind(rootStackPane.widthProperty());
        fondoView.fitHeightProperty().bind(rootStackPane.heightProperty());

        // Configuración del título del nivel
        titulo = new Label();
        titulo.getStyleClass().add("titulo-mapa");

        // Configuración del grid del mapa
        gridPane = new GridPane();
        gridPane.setPrefWidth(680);
        gridPane.setPrefHeight(680);
        gridPane.getStyleClass().add("grid-centro");

        // Configuración del contenedor principal
        vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("contenedor-juego");
        vbox.setPrefWidth(Double.MAX_VALUE);
        vbox.setPrefHeight(Double.MAX_VALUE);
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setMaxHeight(Double.MAX_VALUE);
        vbox.getChildren().addAll(titulo, gridPane);

        // Añadir al StackPane raíz
        rootStackPane.getChildren().add(vbox);

        // Generación inicial del mapa
        generarMapa();
        pintarPersonajes();
        actualizarTurno();

        // Configuración de eventos de teclado
        rootStackPane.setOnKeyPressed(event -> {
            Personaje actual = juego.getPersonajeActual();
            if (actual instanceof Heroe) { // Solo procesar teclas si es el turno del héroe
                boolean movimientoRealizado = false;

                switch (event.getCode()) {
                    case W:
                        juego.moverArriba(actual);
                        movimientoRealizado = true;
                        break;
                    case A:
                        juego.moverIzquierda(actual);
                        movimientoRealizado = true;
                        break;
                    case S:
                        juego.moverAbajo(actual);
                        movimientoRealizado = true;
                        break;
                    case D:
                        juego.moverDerecha(actual);
                        movimientoRealizado = true;
                        break;
                    default:
                        return;
                }

                if (movimientoRealizado) {
                    pintarPersonajes();
                    juego.pasarTurno();
                    actualizarTurno(); // Pasa el turno a los enemigos
                }
            }
        });

        // Habilitar el foco para eventos de teclado
        rootStackPane.setFocusTraversable(true);
        rootStackPane.requestFocus();
    }

    /**
     * Genera y muestra el mapa actual en el grid.
     * Crea celdas para cada posición del mapa usando las imágenes correspondientes.
     */
    public void generarMapa() {
        gridPane.getChildren().clear();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        Mapa mapaActual = juego.getGestorMapas().getMapaActual();
        int[][] matriz = mapaActual.getMapa();
        int filas = matriz.length;
        int columnas = matriz[0].length;

        // Actualizar el título del nivel
        titulo.setText("NIVEL " + mapaActual.getNivel() + " : " + mapaActual.getNombre());

        double anchoCelda = Math.floor(gridPane.getPrefWidth() / columnas);
        double altoCelda = Math.floor(gridPane.getPrefHeight() / filas);

        // Cargar imágenes de suelo y pared
        Image suelo = new Image(getClass().getResourceAsStream(mapaActual.getSuelo()), anchoCelda, altoCelda, false, true);
        Image pared = new Image(getClass().getResourceAsStream(mapaActual.getPared()), anchoCelda, altoCelda, false, true);
        Image trampa = new Image(getClass().getResourceAsStream(mapaActual.getTrampa()), anchoCelda, altoCelda, false, true);

        // Crear celdas del mapa
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                int valor = matriz[fila][columna];
                /*ImageView imageView = new ImageView(valor == 0 ? suelo : pared);*/
                ImageView imageView;
                if (valor == 0) {
                    imageView = new ImageView(suelo);
                } else if (valor == 1) {
                    imageView = new ImageView(pared);
                } else {
                    imageView = new ImageView(trampa);
                }
                imageView.setFitWidth(anchoCelda);
                imageView.setFitHeight(altoCelda);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(false);

                StackPane stackPane = new StackPane(imageView);
                stackPane.setPrefWidth(anchoCelda);
                stackPane.setPrefHeight(altoCelda);

                gridPane.add(stackPane, columna, fila);
            }
        }
    }

    /**
     * Avanza al siguiente mapa o muestra la pantalla de victoria si no hay más mapas.
     */
    public void cambiarMapa() {
        boolean haySiguiente = gestorMapas.avanzarAlSiguienteMapa();

        if (haySiguiente) {
            LinkedHashMap<String, Mapa> mapas = juego.getGestorMapas().getMapas();
            mapas.clear();
            generarMapa();
            juego.iniciarentidades();
            pintarPersonajes();
            actualizarTurno();
        } else {
            juego.resetearJuego();
            ManagerEscenas.getInstance().loadScene(EscenaID.VICTORIA);
        }
    }

    /**
     * Dibuja un personaje en la posición especificada.
     * @param x Coordenada horizontal
     * @param y Coordenada vertical
     * @param personaje Personaje a dibujar
     */
    public void pintarPersonaje(int x, int y, Personaje personaje) {
        StackPane stackPane = (StackPane) gridPane.getChildren().get(y * gridPane.getColumnCount() + x);

        // Crear vista de imagen para el personaje
        Image pj = new Image(getClass().getResourceAsStream("/" + personaje.getImagen()));
        ImageView personajeView = new ImageView(pj);
        personajeView.setFitWidth(stackPane.getPrefWidth());
        personajeView.setFitHeight(stackPane.getPrefHeight());
        personajeView.setPreserveRatio(true);
        personajeView.setSmooth(true);

        // Aplicar efecto de sombra
        DropShadow sombra = new DropShadow();
        sombra.setRadius(6);
        sombra.setOffsetX(4);
        sombra.setOffsetY(4);
        sombra.setColor(Color.color(0, 0, 0, 0.6));
        personajeView.setEffect(sombra);

        stackPane.getChildren().add(personajeView);
    }

    /**
     * Dibuja todos los personajes en sus posiciones actuales.
     * Regenera el mapa antes de pintar los personajes.
     */
    public void pintarPersonajes() {
        generarMapa();
        for (Personaje p : juego.getEntidades()) {
            pintarPersonaje(p.getPosicion().getX(), p.getPosicion().getY(), p);
        }
    }

    /**
     * Gestiona el cambio de turno entre jugador y enemigos.
     * Los enemigos se mueven automáticamente durante su turno.
     */
    private void actualizarTurno() {
        Personaje actual = juego.getPersonajeActual();

        if (actual instanceof Enemigo) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
                juego.moverenemigo((Enemigo) actual);
                pintarPersonajes();
                juego.pasarTurno();
                actualizarTurno();
            }));
            timeline.play();
        }
        // Si es el héroe, no hacemos nada y esperamos input del usuario
    }

    /**
     * Verifica y maneja la condición de victoria.
     */
    public void notificarVictoria() {
        if (juego.verificarVictoria()) {
            System.out.println("¡Has derrotado a todos los enemigos! Cambiando al siguiente mapa...");
            cambiarMapa();
        }
    }

    /**
     * Método de Observer que reacciona a cambios en el juego.
     * Verifica condiciones de victoria/derrota y actualiza la vista.
     */
    @Override
    public void onChange() {
        if (juego.getEntidades().size() == 1 && juego.getEntidades().get(0) instanceof Heroe) {
            notificarVictoria();
        }

        if (juego.verificarDerrota()) {
            System.out.println("Has sido derrotado. Fin del juego.");
            juego.resetearJuego();
            ManagerEscenas.getInstance().loadScene(EscenaID.DERROTA);
        }
    }
}
