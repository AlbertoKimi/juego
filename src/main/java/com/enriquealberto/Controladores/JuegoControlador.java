package com.enriquealberto.Controladores;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.enriquealberto.interfaces.Observer;

import com.enriquealberto.model.GestorMapas;
import com.enriquealberto.model.Proveedor;
import com.enriquealberto.model.Mapa;

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

    @FXML
    public void initialize() {
        gestorMapas = Proveedor.getInstance().getGestorMapas();
        gestorMapas.subscribe(this);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(7));
        vbox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane();
        Label titulo = new Label("NIVEL " + gestorMapas.getMapaActual().getNivel() + " : " + gestorMapas.getMapaActual().getNombre());
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        stackPane.getChildren().add(titulo);

        gridPane = new GridPane();
        gridPane.setPrefWidth(600); // Ancho fijo
        gridPane.setPrefHeight(600); // Alto fijo

        vbox.getChildren().addAll(stackPane, gridPane);

        anchorPane.setPrefWidth(800); //
        anchorPane.setPrefHeight(600); //

        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        anchorPane.getChildren().add(vbox);

        generarMapa(gestorMapas.getMapas());

        gridPane.setOnMouseClicked(event -> {
            cambiarMapa();
        });
    }

    public void generarMapa(LinkedHashMap<String, Mapa> mapas) {
        gridPane.getChildren().clear();
        Mapa primerMapa = Proveedor.getInstance().getGestorMapas().getMapaActual();
        ArrayList<ArrayList<Integer>> matriz = primerMapa.getMapa();
        int filas = matriz.size();
        int columnas = matriz.get(0).size();

        // Calcular el tamaño de las celdas basado en las dimensiones fijas del GridPane
        double anchoCelda = gridPane.getPrefWidth() / columnas;
        double altoCelda = gridPane.getPrefHeight() / filas;

        Image suelo = new Image(getClass().getResourceAsStream(mapas.values().iterator().next().getSuelo()));
        Image pared = new Image(getClass().getResourceAsStream(mapas.values().iterator().next().getPared()));

        for (int fila = 0; fila < filas; fila++) {
            ArrayList<Integer> filaMapa = matriz.get(fila);
            for (int columna = 0; columna < columnas; columna++) {
                int valor = filaMapa.get(columna);
                ImageView imageView;
                if (valor == 0) {
                    imageView = new ImageView(suelo);
                } else {
                    imageView = new ImageView(pared);
                }
                imageView.setFitWidth(anchoCelda);
                imageView.setFitHeight(altoCelda);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);

                StackPane stackPane = new StackPane(imageView);
                stackPane.setPrefWidth(anchoCelda);
                stackPane.setPrefHeight(altoCelda);

                gridPane.add(stackPane, columna, fila);
            }
        }
    }

    public void cambiarMapa() {
        // Intentar avanzar al siguiente mapa
        boolean haySiguiente = gestorMapas.avanzarAlSiguienteMapa();

        if (haySiguiente) {
            // Si hay un siguiente mapa, simplemente actualiza la vista
            onChange();
        } else {
            // Si no hay más mapas, mostrar un mensaje
            System.out.println("No hay más mapas disponibles.");
        }
    }

    @Override
    public void onChange() {
        // Actualizar el mapa actual
        LinkedHashMap<String, Mapa> mapas = gestorMapas.getMapas();
        /*ArrayList<Mapa> listaMapas = new ArrayList<>(mapas.values());*/
        /*int indiceActual = listaMapas.indexOf(gestorMapas.getMapaActual());*/
        /*Mapa mapaActual = listaMapas.get(indiceActual);*/
        generarMapa(mapas);
    }
}
