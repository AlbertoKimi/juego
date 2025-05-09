package com.enriquealberto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;


import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.Heroe;

import com.enriquealberto.model.Juego;
import javafx.scene.shape.Sphere;

public class EstadisticaControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    VBox vBox;
    Label titulo;
    private Juego juego;
    @FXML
    public void initialize() {
        juego = Juego.getInstance();
        if (juego.getJugador() == null) {
            System.err.println("ERROR: El héroe no ha sido inicializado.");
            return;
        }
        juego.suscribe(this);

        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(7));
        vBox.setAlignment(Pos.CENTER);

        titulo = new Label();
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        vBox.getChildren().add(titulo);
        anchorPane.getChildren().add(vBox);
        cargarPersonajes();

    }

    public void cargarPersonajes() {
        vBox.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
            VBox personajeBox = loader.load();
            Label nombre = (Label) personajeBox.lookup("#p_nombre");
            ImageView foto = (ImageView) personajeBox.lookup("#p_foto");


            if (juego.getJugador().getImagen() != null) {
                foto.setImage(new Image(getClass().getResource("/" + juego.getJugador().getImagen()).toExternalForm()));
            }

            asignarAtributo(personajeBox, "vf", juego.getJugador().getVida(), "/com/enriquealberto/imagenes/vida.png");
            asignarAtributo(personajeBox, "af", juego.getJugador().getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
            asignarAtributo(personajeBox, "df", juego.getJugador().getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
            asignarAtributo(personajeBox, "sf", juego.getJugador().getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");

            vBox.getChildren().add(personajeBox);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void asignarAtributo(VBox personajeBox, String prefijo, int cantidad, String iconoRuta) {
        // Cargar la imagen una sola vez
        Image icono = new Image(getClass().getResource(iconoRuta).toExternalForm());

        for (int i = 1; i <= 5; i++) {
            String id = "#" + prefijo + i;
            ImageView img = (ImageView) personajeBox.lookup(id);

            if (img == null) {
                System.err.println("No se encontró ImageView con ID: " + id);
                continue;
            }

            boolean visible = i <= cantidad;
            img.setVisible(visible);
            img.setManaged(visible);

            if (visible) {
                img.setImage(icono);
            } else {
                img.setImage(null); // Limpiar la imagen si no es visible
            }
        }
    }

    @Override
    public void onChange() {
        cargarPersonajes();
        System.out.println("El mapa ha cambiado");
    }
}
