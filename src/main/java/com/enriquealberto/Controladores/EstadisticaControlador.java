package com.enriquealberto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
        // Mapeo de prefijos a nombres completos de atributos
        String nombreAtributo;
        switch(prefijo) {
            case "vf": nombreAtributo = "vida"; break;
            case "af": nombreAtributo = "ataque"; break;
            case "df": nombreAtributo = "defensa"; break;
            case "sf": nombreAtributo = "velocidad"; break;
            default:
                System.err.println("Prefijo no reconocido: " + prefijo);
                return;
        }

        // Buscamos el contenedor padre (HBox que contiene la etiqueta y el contenedor de imágenes)
        HBox contenedorPadre = (HBox) personajeBox.lookup("#p_" + nombreAtributo);
        if (contenedorPadre == null) {
            System.err.println("No se encontró el contenedor padre para: p_" + nombreAtributo);
            return;
        }

        // Buscamos el contenedor de imágenes (HBox dentro del contenedor padre)
        HBox contenedorImagenes = (HBox) personajeBox.lookup("#" + prefijo);
        if (contenedorImagenes == null) {
            System.err.println("No se encontró el contenedor de imágenes para: " + prefijo);
            return;
        }

        // Limpiamos las imágenes existentes (excepto la etiqueta)
        contenedorImagenes.getChildren().removeIf(node -> node instanceof ImageView);

        // Caso especial para la vida (vf)
        if (prefijo.equals("vf")) {
            manejarVidaMultiFila(contenedorPadre, contenedorImagenes, cantidad, iconoRuta);
        } else {
            // Para otros atributos, comportamiento normal
            agregarIconos(contenedorImagenes, cantidad, iconoRuta);
        }
    }

    private void manejarVidaMultiFila(HBox contenedorPadre, HBox primeraFila, int cantidad, String iconoRuta) {
        // Limpiar el contenedor de imágenes
        primeraFila.getChildren().clear();

        // Cargar la imagen del corazón
        Image icono = new Image(getClass().getResource(iconoRuta).toExternalForm());

        // Mostrar siempre al menos 1 corazón
        ImageView img = new ImageView(icono);
        img.setFitWidth(25);
        img.setFitHeight(25);
        primeraFila.getChildren().add(img);

        // Si la vida es 5 o menos, mostrar todos los corazones
        if (cantidad <= 5) {
            for (int i = 1; i < cantidad; i++) {
                ImageView corazonExtra = new ImageView(icono);
                corazonExtra.setFitWidth(25);
                corazonExtra.setFitHeight(25);
                primeraFila.getChildren().add(corazonExtra);
            }
        }
        // Si la vida es más de 5, mostrar el contador
        else {
            Label contador = new Label("×" + cantidad);
            contador.setStyle("-fx-text-fill: black; -fx-font-family: 'Hoefler Text Black'; -fx-font-size: 16px;");
            primeraFila.getChildren().add(contador);
        }
    }

    private void agregarIconos(HBox contenedor, int cantidad, String iconoRuta) {
        Image icono = new Image(getClass().getResource(iconoRuta).toExternalForm());

        for (int i = 0; i < cantidad; i++) {
            ImageView img = new ImageView(icono);
            img.setFitWidth(25);
            img.setFitHeight(25);
            contenedor.getChildren().add(img);
        }
    }

    @Override
    public void onChange() {
        cargarPersonajes();
    }
}
