package com.enriquealberto.Controladores;


import com.enriquealberto.EscenaID;
import javafx.fxml.FXML;
import com.enriquealberto.ManagerEscenas; 
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class VictoriaControlador {
    @FXML
    VBox vbox;

    @FXML
    Label titulo;

    @FXML
    HBox hbox;

    @FXML
    Button botonVolverJugar;

    @FXML
    Button botonSelect;

    @FXML
    public void initialize() {
         botonVolverJugar.setOnAction(event -> {
            ManagerEscenas.getInstance().loadScene(EscenaID.CONTENEDOR); // Cambiar a CajaFuerte.fxml
        });
        botonSelect.setOnAction(event -> {
            ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION); // Cambiar a CajaFuerte.fxml
        });
    }
}
