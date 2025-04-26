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

public class JuegoControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    GridPane gridPane;
    GestorMapas gestorMapas;

    @FXML
    public void initialize() {
        gestorMapas = Proveedor.getInstance().getGestorMapas();
        gestorMapas.subscribe(this);
        gridPane = new GridPane();

        // Ajustar el GridPane al AnchorPane
        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);
        anchorPane.getChildren().add(gridPane);
        
        // Listener para ajustar dinámicamente las celdas
        gridPane.widthProperty().addListener((obs, oldVal, newVal) -> generarMapa(gestorMapas.getMapas()));
        gridPane.heightProperty().addListener((obs, oldVal, newVal) -> generarMapa(gestorMapas.getMapas()));

        generarMapa(gestorMapas.getMapas());
    }

    public void generarMapa(LinkedHashMap<String, Mapa> mapas) {
        gridPane.getChildren().clear(); 
        Mapa primerMapa = Proveedor.getInstance().getGestorMapas().getMapaActual();
        ArrayList<ArrayList<Integer>> matriz = primerMapa.getMapa();
        int filas = matriz.size();
        int columnas = matriz.get(0).size();
        double anchoCelda = gridPane.getWidth() / columnas;
        double altoCelda = gridPane.getHeight() / filas;

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

    @Override
    public void onChange() {
        System.out.println("El mapa ha cambiado");
    }

}
