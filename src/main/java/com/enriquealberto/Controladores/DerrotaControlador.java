package com.enriquealberto.Controladores;


import com.enriquealberto.EscenaID;
import javafx.fxml.FXML;
import com.enriquealberto.ManagerEscenas; 
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class DerrotaControlador {
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
            ManagerEscenas.getInstance().loadScene(EscenaID.CONTENEDOR); 
            ((Stage) botonVolverJugar.getScene().getWindow()).close();
        });
        botonSelect.setOnAction(event -> {
            ManagerEscenas.getInstance().loadScene(EscenaID.SELECTION); 
            ((Stage) botonSelect.getScene().getWindow()).close();
        });
    }
}
