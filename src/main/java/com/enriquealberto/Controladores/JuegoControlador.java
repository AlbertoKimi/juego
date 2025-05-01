package com.enriquealberto.Controladores;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.enriquealberto.interfaces.Observer;

import com.enriquealberto.model.GestorMapas;

import com.enriquealberto.model.Proveedor;
import com.enriquealberto.model.Mapa;
import com.enriquealberto.model.Enemigo;
import com.enriquealberto.model.Heroe; // Ensure Heroe is imported
import com.enriquealberto.model.Personaje; // Import Personaje class

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

public class JuegoControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    GridPane gridPane;
    GestorMapas gestorMapas;
    VBox vbox;
    Label titulo;
    Heroe heroe;
    Enemigo enemigo;

    @FXML
    public void initialize() {
        gestorMapas = Proveedor.getInstance().getGestorMapas();
        gestorMapas.subscribe(this);

        heroe = new Heroe("manolo", "/com/enriquealberto/imagenes/cocoTanque.png", 100, 10, 5, 2, 10);
        enemigo = new Enemigo("pepe", "/com/enriquealberto/imagenes/uvaLuchador.png", 100, 10, 5, 2, 0, 2, 2);

        vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(7));
        vbox.setAlignment(Pos.CENTER);

        titulo = new Label();
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        gridPane = new GridPane();
        gridPane.setPrefWidth(600); // Ancho fijo
        gridPane.setPrefHeight(600); // Alto fijo

        vbox.getChildren().addAll(titulo, gridPane);

        anchorPane.setPrefWidth(800);
        anchorPane.setPrefHeight(600);

        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        anchorPane.getChildren().add(vbox);

        generarMapa(gestorMapas.getMapas());
        pintarPersonaje(0, 0, heroe); // Cambia

        pintarPersonaje(0, 1, enemigo);

        anchorPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    System.out.println("Ataque realizado");
                    heroe.atacar(heroe, enemigo);
                    break;
                case UP:
                    System.out.println("Ataque realizado");
                    enemigo.atacar(heroe, enemigo);
                    break;
                default:
                    break;
            }
        });

        // Habilitar el foco en el AnchorPane para recibir eventos de teclado
        anchorPane.setFocusTraversable(true);
        anchorPane.requestFocus();

        gridPane.setOnMouseClicked(event -> {
            cambiarMapa();
        });

    }

    public void generarMapa(LinkedHashMap<String, Mapa> mapas) {
        gridPane.getChildren().clear();
        Mapa mapaActual = gestorMapas.getMapaActual();
        ArrayList<ArrayList<Integer>> matriz = mapaActual.getMapa();
        int filas = matriz.size();
        int columnas = matriz.get(0).size();

        // Actualizar el título del nivel
        titulo.setText("NIVEL " + mapaActual.getNivel() + " : " + mapaActual.getNombre());

        // Calcular el tamaño de las celdas
        double anchoCelda = gridPane.getPrefWidth() / columnas;
        double altoCelda = gridPane.getPrefHeight() / filas;

        Image suelo = new Image(getClass().getResourceAsStream(mapaActual.getSuelo()));
        Image pared = new Image(getClass().getResourceAsStream(mapaActual.getPared()));

        for (int fila = 0; fila < filas; fila++) {
            ArrayList<Integer> filaMapa = matriz.get(fila);
            for (int columna = 0; columna < columnas; columna++) {
                int valor = filaMapa.get(columna);
                ImageView imageView;
                if (valor == 0) {
                    imageView = new ImageView(suelo); // Imagen de suelo
                } else {
                    imageView = new ImageView(pared); // Imagen de pared
                }
                imageView.setFitWidth(anchoCelda);
                imageView.setFitHeight(altoCelda);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);

                // Crear el StackPane con suelo o pared
                StackPane stackPane = new StackPane(imageView);
                stackPane.setPrefWidth(anchoCelda);
                stackPane.setPrefHeight(altoCelda);

                // Añadir el StackPane al GridPane
                gridPane.add(stackPane, columna, fila);
            }
        }
    }

    public void cambiarMapa() {
        boolean haySiguiente = gestorMapas.avanzarAlSiguienteMapa();
        if (haySiguiente) {
            onChange();
        } else {
            System.out.println("No hay más mapas disponibles.");
        }
    }

    public void pintarPersonaje(int x, int y, Personaje personaje) {
        // Obtener la celda correspondiente en el GridPane
        StackPane stackPane = (StackPane) gridPane.getChildren().get(y * gridPane.getColumnCount() + x);

        // Crear un nuevo ImageView para el personaje
        ImageView personajeView = new ImageView(new Image(getClass().getResourceAsStream(personaje.getImagen())));
        personajeView.setFitWidth(stackPane.getPrefWidth());
        personajeView.setFitHeight(stackPane.getPrefHeight());
        personajeView.setPreserveRatio(true);
        personajeView.setSmooth(true);

        // Añadir el ImageView del personaje al StackPane
        stackPane.getChildren().add(personajeView);
    }

    public void eliminarPersonaje(int x, int y) {
        // Obtener la celda correspondiente en el GridPane
        StackPane stackPane = (StackPane) gridPane.getChildren().get(y * gridPane.getColumnCount() + x);

        // Buscar y eliminar solo el ImageView del personaje
        stackPane.getChildren()
                .removeIf(node -> node instanceof ImageView && !node.equals(stackPane.getChildren().get(0)));
    }

    @Override
    public void onChange() {
        // Actualizar el mapa actual
        LinkedHashMap<String, Mapa> mapas = gestorMapas.getMapas();
        generarMapa(mapas);
        pintarPersonaje(0, 0,
                new Enemigo("pepe", "/com/enriquealberto/imagenes/cocoTanque.png", 100, 10, 5, 2, 0, 2, 2));
        pintarPersonaje(0, 1,
                new Enemigo("pepe", "/com/enriquealberto/imagenes/uvaLuchador.png", 100, 10, 5, 2, 0, 2, 2));
        eliminarPersonaje(0, 1);
    }
}
