package com.enriquealberto.Controladores;

import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.ArrayList;

import com.enriquealberto.interfaces.Observer;

import com.enriquealberto.model.GestorMapas;
import com.enriquealberto.model.Proveedor;
import com.enriquealberto.model.Mapa;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
        gridPane.setGridLinesVisible(true);
        anchorPane.getChildren().add(gridPane);
        int tamano= gestorMapas.getMapaActual().getMapa().get(0).size();
        double[] columnPercents = new double[tamano];
        for (int i = 0; i < tamano; i++) {
            columnPercents[i] = 100.0 / tamano; // Ajuste proporcional
        }
        
        for (double percent : columnPercents) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(percent); // Ajuste proporcional
            col.setHgrow(Priority.ALWAYS); // Permitir que crezca con el GridPane
            col.setHalignment(HPos.LEFT);
            gridPane.getColumnConstraints().add(col);
        }
        generarMapa(gestorMapas.getMapas());
       
    }

    public void generarMapa(LinkedHashMap<String, Mapa> mapas) {
        gridPane.getChildren().clear(); 
        //Ajustar el tamaño de las imágenes de acuerdo al tamaño de la celda
        Image suelo= new Image(getClass().getResourceAsStream(mapas.values().iterator().next().getSuelo()), 100, 100, true, false);
        Image pared= new Image(getClass().getResourceAsStream(mapas.values().iterator().next().getPared()));
        Mapa primerMapa = Proveedor.getInstance().getGestorMapas().getMapaActual(); 
        ArrayList<ArrayList<Integer>> matriz = primerMapa.getMapa();
        for (int fila = 0; fila < matriz.size(); fila++) {
            ArrayList<Integer> filaMapa = matriz.get(fila); 
            for (int columna = 0; columna < filaMapa.size(); columna++) {
                int valor = filaMapa.get(columna); 
                ImageView imageView;
                if (valor == 0) {
                    imageView = new ImageView(suelo);
                } else {
                    imageView = new ImageView(pared);
                }

                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                StackPane stackPane = new StackPane(imageView);
                gridPane.add(stackPane, columna, fila);
                
            }
        }
    }

    @Override
    public void onChange() {
        System.out.println("El mapa ha cambiado");
    }

}
