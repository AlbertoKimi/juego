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
import java.util.List;

import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.Heroe;

import com.enriquealberto.model.Juego;

public class EstadisticaControlador implements Observer {
    @FXML
    AnchorPane anchorPane;

    VBox vBox;
    Label titulo;
    Heroe heroe;

    @FXML
    public void initialize() {
        heroe = Juego.getInstance().getJugador();
        heroe.suscribe(this);

        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(7));
        vBox.setAlignment(Pos.CENTER);

        titulo = new Label();
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        vBox.getChildren().add(titulo);
        anchorPane.getChildren().add(vBox);

        cargarPersonajes(heroe);

    }

    public void cargarPersonajes(Heroe personaje) {
        vBox.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
            VBox personajeBox = loader.load();
            Label nombre = (Label) personajeBox.lookup("#p_nombre");
            ImageView foto = (ImageView) personajeBox.lookup("#p_foto");

            nombre.setText("Héroe: " + heroe.getNombre());

            if (personaje.getImagen() != null) {
                URL imagenUrl = getClass().getResource(personaje.getImagen());
                if (imagenUrl == null) {
                    System.err.println("No se encontró la imagen: " + personaje.getImagen());
                } else {
                    foto.setImage(new Image(imagenUrl.toExternalForm()));
                }
            }

            asignarAtributo(personajeBox, "vf", personaje.getVida(), "/com/enriquealberto/imagenes/vida.png");
            asignarAtributo(personajeBox, "af", personaje.getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
            asignarAtributo(personajeBox, "df", personaje.getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
            asignarAtributo(personajeBox, "sf", personaje.getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");

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
        System.out.println("El mapa ha cambiado");
    }
}
