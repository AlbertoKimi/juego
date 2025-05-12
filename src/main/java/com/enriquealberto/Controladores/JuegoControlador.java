package com.enriquealberto.Controladores;

import java.io.InputStream;
import java.util.LinkedHashMap;
import com.enriquealberto.EscenaID;
import com.enriquealberto.ManagerEscenas;
import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class JuegoControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    GridPane gridPane;
    GestorMapas gestorMapas;
    VBox vbox;
    Label titulo;
    Enemigo enemigo;
    Juego juego;

    private Heroe heroe;
    private int contador = 0;

    @FXML
    public void initialize() {
        juego = Juego.getInstance();
        heroe = juego.getJugador();
        if (heroe == null) {
            System.err.println("ERROR: El héroe no ha sido inicializado en ventana juego.");
            return;
        }
        juego.suscribe(this);
        juego.iniciarentidades();
        System.out.println("controlador de juego inicializado");
        gestorMapas = juego.getGestorMapas();

        vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(7));
        vbox.setAlignment(Pos.CENTER);

        titulo = new Label();
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        gridPane = new GridPane();
        gridPane.setPrefWidth(710); // Ancho fijo
        gridPane.setPrefHeight(710); // Alto fijo

        vbox.getChildren().addAll(titulo, gridPane);

        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(700);

        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        anchorPane.getChildren().add(vbox);

        pintarPersonajes();
        actualizarTurno(); // empieza el primer turno

        anchorPane.setOnKeyPressed(event -> {
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
                        movimientoRealizado =true;
                        break;
                    case S:
                        juego.moverAbajo(actual);
                        movimientoRealizado =true;
                        break;
                    case D:
                        juego.moverDerecha(actual);
                        movimientoRealizado =true;
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
        System.out.println(contador);

        // Habilitar el foco en el AnchorPane para recibir eventos de teclado
        anchorPane.setFocusTraversable(true);
        anchorPane.requestFocus();

    }

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

        // Cargar imágenes una vez
        Image suelo = new Image(getClass().getResourceAsStream(mapaActual.getSuelo()), anchoCelda, altoCelda, false, true);
        Image pared = new Image(getClass().getResourceAsStream(mapaActual.getPared()), anchoCelda, altoCelda, false, true);

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                int valor = matriz[fila][columna];
                ImageView imageView = new ImageView(valor == 0 ? suelo : pared);
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

    public void pintarPersonaje(int x, int y, Personaje personaje) {
        // Obtener la celda correspondiente en el GridPane
        StackPane stackPane = (StackPane) gridPane.getChildren().get(y * gridPane.getColumnCount() + x);

        // Crear un nuevo ImageView para el personaje
        InputStream is = getClass().getResourceAsStream("/" + personaje.getImagen());
        if (is == null) {
            System.err.println("No se encontró la imagen: " + personaje.getImagen());
            return;
        }
        ImageView personajeView = new ImageView(new Image(is));
        personajeView.setFitWidth(stackPane.getPrefWidth());
        personajeView.setFitHeight(stackPane.getPrefHeight());
        personajeView.setPreserveRatio(true);
        personajeView.setSmooth(true);

        // Añadir el ImageView del personaje al StackPane
        stackPane.getChildren().add(personajeView);
    }

    public void pintarPersonajes() {
        generarMapa();
        for (Personaje p : juego.getEntidades()) {
            pintarPersonaje(p.getPosicion().getX(), p.getPosicion().getY(), p);
        }
    }

    public void eliminarPersonaje(int x, int y) {
        // Obtener la celda correspondiente en el GridPane
        StackPane stackPane = (StackPane) gridPane.getChildren().get(y * gridPane.getColumnCount() + x);

        // Buscar y eliminar solo el ImageView del personaje
        stackPane.getChildren()
                .removeIf(node -> node instanceof ImageView && !node.equals(stackPane.getChildren().get(0)));
    }

    private void actualizarTurno() {

        Personaje actual = juego.getPersonajeActual();

        if (actual instanceof Enemigo) {
            new Timeline(new KeyFrame(Duration.millis(100), e -> {
                juego.moverenemigo((Enemigo) actual);
                pintarPersonajes();
                juego.pasarTurno();
                actualizarTurno();

            })).play();
        }
        // Si es el héroe, no hacemos nada y esperamos input del usuario
    }

    public void notificarVictoria() {
        if (juego.verificarVictoria()) {
            System.out.println("¡Has derrotado a todos los enemigos! Cambiando al siguiente mapa...");
            cambiarMapa();
        }
    }

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
