package com.enriquealberto.Controladores;

import com.enriquealberto.model.Personaje;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.*;

import com.enriquealberto.interfaces.Observer;
import com.enriquealberto.model.Heroe;
import com.enriquealberto.model.Juego;

public class EstadisticaControlador implements Observer {

    @FXML
    AnchorPane anchorPane;

    @FXML
    ImageView fondoDecorado;
    @FXML
    VBox cont_principal;
    @FXML
    private Label lblTituloJugador;

    @FXML
    private VBox contenedorJugador;

    @FXML
    private VBox contenedorVidas;


    private Juego juego;
    private final Map<String, Image> imagenesCache = new HashMap<>();

    @FXML
    public void initialize() {
        juego = Juego.getInstance();
        if (juego.getJugador() == null) {
            System.err.println("ERROR: El héroe no ha sido inicializado.");
            return;
        }
        juego.suscribe(this);
        Image imagen = new Image(getClass().getResource("/com/enriquealberto/imagenes/fondoesta.png").toExternalForm());
        fondoDecorado.setImage(imagen);
        fondoDecorado.fitWidthProperty().bind(anchorPane.widthProperty());
        fondoDecorado.fitHeightProperty().bind(anchorPane.heightProperty());
        lblTituloJugador.setText(juego.getNombre());
        cargarPersonajes();
        cargarVidas();



    }

    public void cargarPersonajes() {
        contenedorJugador.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/m_perso.fxml"));
            VBox personajeBox = loader.load();
            personajeBox.setId("personajeJugador");

            Label nombre = (Label) personajeBox.lookup("#p_nombre");
            ImageView foto = (ImageView) personajeBox.lookup("#p_foto");

            nombre.setText(juego.getJugador().getNombre());

            if (juego.getJugador().getImagen() != null) {
                foto.setImage(cargarImagen("/" + juego.getJugador().getImagen()));
            }

            asignarAtributo(personajeBox, "vf", juego.getJugador().getVida(), "/com/enriquealberto/imagenes/vida.png");
            asignarAtributo(personajeBox, "af", juego.getJugador().getAtaque(), "/com/enriquealberto/imagenes/ataque.png");
            asignarAtributo(personajeBox, "df", juego.getJugador().getDefensa(), "/com/enriquealberto/imagenes/defensa.png");
            asignarAtributo(personajeBox, "sf", juego.getJugador().getVelocidad(), "/com/enriquealberto/imagenes/velocidad.png");

            contenedorJugador.getChildren().add(personajeBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarVidas() {
        contenedorVidas.getChildren().clear();
        List<Personaje> personajes = juego.getEntidades();
        Personaje actual = juego.getPersonajeActual();

        for (Personaje p : personajes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/enriquealberto/vistas/min_perso.fxml"));
                HBox personajeBox = loader.load();

                if (p.equals(actual)) {
                    personajeBox.getStyleClass().add("selected-personaje");
                }

                ImageView foto = (ImageView) personajeBox.lookup("#im_per");
                if (p.getImagen() != null) {
                    foto.setImage(cargarImagen("/" + p.getImagen()));
                }

                foto.setFitWidth(40);
                foto.setFitHeight(40);

                Image icono = cargarImagen("/com/enriquealberto/imagenes/vida.png");

                HBox corazones = new HBox(3);
                corazones.setAlignment(Pos.CENTER_LEFT);

                if (p.getVida() <= 5) {
                    for (int i = 0; i < p.getVida(); i++) {
                        ImageView img = new ImageView(icono);
                        img.setFitWidth(16);
                        img.setFitHeight(16);
                        corazones.getChildren().add(img);
                    }
                } else {
                    ImageView img = new ImageView(icono);
                    img.setFitWidth(16);
                    img.setFitHeight(16);
                    Label contador = new Label("×" + p.getVida());
                    contador.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
                    corazones.getChildren().addAll(img, contador);
                }

                personajeBox.getChildren().add(corazones);
                contenedorVidas.getChildren().add(personajeBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Image cargarImagen(String ruta) {
        return imagenesCache.computeIfAbsent(ruta, r -> new Image(getClass().getResource(r).toExternalForm()));
    }

    private void asignarAtributo(VBox personajeBox, String prefijo, int cantidad, String iconoRuta) {
        String nombreAtributo;
        switch (prefijo) {
            case "vf":
                nombreAtributo = "vida";
                break;
            case "af":
                nombreAtributo = "ataque";
                break;
            case "df":
                nombreAtributo = "defensa";
                break;
            case "sf":
                nombreAtributo = "velocidad";
                break;
            default:
                nombreAtributo = null;
                break;
        }

        if (nombreAtributo == null) return;

        HBox contenedorPadre = (HBox) personajeBox.lookup("#p_" + nombreAtributo);
        HBox contenedorImagenes = (HBox) personajeBox.lookup("#" + prefijo);

        if (contenedorPadre == null || contenedorImagenes == null) return;

        contenedorImagenes.getChildren().removeIf(node -> node instanceof ImageView);

        if (prefijo.equals("vf")) {
            manejarVidaMultiFila(contenedorPadre, contenedorImagenes, cantidad, iconoRuta);
        } else {
            agregarIconos(contenedorImagenes, cantidad, iconoRuta);
        }
    }

    private void manejarVidaMultiFila(HBox contenedorPadre, HBox primeraFila, int cantidad, String iconoRuta) {
        primeraFila.getChildren().clear();
        Image icono = cargarImagen(iconoRuta);

        if (cantidad <= 5) {
            for (int i = 0; i < cantidad; i++) {
                ImageView img = new ImageView(icono);
                img.setFitWidth(20);
                img.setFitHeight(20);
                primeraFila.getChildren().add(img);
            }
        } else {
            ImageView img = new ImageView(icono);
            img.setFitWidth(20);
            img.setFitHeight(20);
            Label contador = new Label("×" + cantidad);
            contador.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            primeraFila.getChildren().addAll(img, contador);
        }
    }

    private void agregarIconos(HBox contenedor, int cantidad, String iconoRuta) {
        Image icono = cargarImagen(iconoRuta);
        for (int i = 0; i < cantidad; i++) {
            ImageView img = new ImageView(icono);
            img.setFitWidth(20);
            img.setFitHeight(20);
            contenedor.getChildren().add(img);
        }
    }

    @Override
    public void onChange() {
        cargarPersonajes();
        cargarVidas();
    }
}