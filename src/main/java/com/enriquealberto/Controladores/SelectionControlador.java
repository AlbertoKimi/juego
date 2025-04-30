package com.enriquealberto.Controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.enriquealberto.model.Heroe;
import com.enriquealberto.model.Juego;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SelectionControlador {

    @FXML
    private HBox cont_perso;

    private ArrayList<Heroe> heroes;

    @FXML
    public void initialize() {
        heroes = Juego.getInstance().getHeroes();
        cargarPersonajes(heroes); // Cargar personajes al iniciar
    }

    public void cargarPersonajes(List<Heroe> personajes) {
        cont_perso.getChildren().clear();

        for (Heroe p : personajes) {
            try {
                System.out.println("Buscando imagen: /" + p.getImagen());
                System.out.println("URL resultante: " + getClass().getResource("/" + p.getImagen()));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
                VBox personajeBox = loader.load();

                Label nombre = (Label) personajeBox.lookup("#p_nombre");
                ImageView foto = (ImageView) personajeBox.lookup("#p_foto");

                nombre.setText(p.getNombre());

                if (p.getImagen() != null) {
                    foto.setImage(new Image(getClass().getResource("/" + p.getImagen()).toExternalForm()));
                }

                asignarAtributo(personajeBox, "vf", p.getVida(), "/com/enriquealberto/imagenes/vida.png");
                asignarAtributo(personajeBox, "af", p.getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
                asignarAtributo(personajeBox, "df", p.getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
                asignarAtributo(personajeBox, "sf", p.getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");

                cont_perso.getChildren().add(personajeBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void asignarAtributo(VBox personajeBox, String prefijo, int cantidad, String iconoRuta) {
        System.out.println("Asignando atributo " + prefijo + " con cantidad " + cantidad);
        
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
            System.out.println("Imagen " + id + " - visible: " + visible);
            
            img.setVisible(visible);
            img.setManaged(visible);
            
            if (visible) {
                img.setImage(icono);
            } else {
                img.setImage(null); // Limpiar la imagen si no es visible
            }
        }
    }
}