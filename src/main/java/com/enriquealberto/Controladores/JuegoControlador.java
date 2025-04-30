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
    VBox vbox; 
    Label titulo; 

    @FXML
    public void initialize() {
        gestorMapas = Proveedor.getInstance().getGestorMapas();
        gestorMapas.subscribe(this);

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
        boolean haySiguiente = gestorMapas.avanzarAlSiguienteMapa();
        if (haySiguiente) {
            onChange();
        } else {
            System.out.println("No hay más mapas disponibles.");
        }
    }

    @Override
    public void onChange() {
        // Actualizar el mapa actual
        LinkedHashMap<String, Mapa> mapas = gestorMapas.getMapas();
        generarMapa(mapas);
    }
}

