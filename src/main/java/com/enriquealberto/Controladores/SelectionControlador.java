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

    @FXML
    private ImageView fd1, fd2, fd3, fd4, fd5;

    private int selectedDifficulty = 0; // 0 significa ninguna seleccionada
    private ArrayList<Heroe> heroes;
    private final double ACTIVE_OPACITY = 1.0;
    private final double INACTIVE_OPACITY = 0.3;
    private boolean isHovering = false;
    private int currentHoverLevel = 0;

    @FXML
    public void initialize() {
        heroes = Juego.getInstance().getHeroes();
        cargarPersonajes(heroes);
        setupDifficultySelector();
    }

    private void setupDifficultySelector() {
        // Cargar la imagen de dificultad en todos los ImageView
        Image difficultyImage = new Image(
                getClass().getResource("/com/enriquealberto/imagenes/dificultad.png").toExternalForm());
        fd1.setImage(difficultyImage);
        fd2.setImage(difficultyImage);
        fd3.setImage(difficultyImage);
        fd4.setImage(difficultyImage);
        fd5.setImage(difficultyImage);

        // Establecer transparencia inicial
        resetDifficultyOpacity();

        // Configurar eventos para cada imagen de dificultad
        setupDifficultyHoverEvents();
        setupDifficultyClickEvents();
    }

    private void resetDifficultyOpacity() {
        fd1.setOpacity(INACTIVE_OPACITY);
        fd2.setOpacity(INACTIVE_OPACITY);
        fd3.setOpacity(INACTIVE_OPACITY);
        fd4.setOpacity(INACTIVE_OPACITY);
        fd5.setOpacity(INACTIVE_OPACITY);
    }

    private void setupDifficultyHoverEvents() {
        fd1.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 1;
            updateHoverEffect(1);
        });
        fd2.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 2;
            updateHoverEffect(2);
        });
        fd3.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 3;
            updateHoverEffect(3);
        });
        fd4.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 4;
            updateHoverEffect(4);
        });
        fd5.setOnMouseEntered(e -> {
            isHovering = true;
            currentHoverLevel = 5;
            updateHoverEffect(5);
        });

        // Cuando el ratón sale de una imagen individual
        fd1.setOnMouseExited(e -> checkHoverStatus());
        fd2.setOnMouseExited(e -> checkHoverStatus());
        fd3.setOnMouseExited(e -> checkHoverStatus());
        fd4.setOnMouseExited(e -> checkHoverStatus());
        fd5.setOnMouseExited(e -> checkHoverStatus());
    }

    private void checkHoverStatus() {
        // Verificar si el ratón todavía está en alguna imagen
        isHovering = fd1.isHover() || fd2.isHover() || fd3.isHover() || fd4.isHover() || fd5.isHover();
        if (!isHovering) {
            updateSelectionEffect();
        }
    }

    private void setupDifficultyClickEvents() {
        fd1.setOnMouseClicked(e -> setSelectedDifficulty(1));
        fd2.setOnMouseClicked(e -> setSelectedDifficulty(2));
        fd3.setOnMouseClicked(e -> setSelectedDifficulty(3));
        fd4.setOnMouseClicked(e -> setSelectedDifficulty(4));
        fd5.setOnMouseClicked(e -> setSelectedDifficulty(5));
    }

    private void updateHoverEffect(int hoveredLevel) {
        resetDifficultyOpacity();

        // Activar opacidad para los niveles hasta el hovered
        if (hoveredLevel >= 1)
            fd1.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 2)
            fd2.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 3)
            fd3.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 4)
            fd4.setOpacity(ACTIVE_OPACITY);
        if (hoveredLevel >= 5)
            fd5.setOpacity(ACTIVE_OPACITY);
    }

    private void setSelectedDifficulty(int level) {
        selectedDifficulty = level;
        updateSelectionEffect();

        // Aquí puedes guardar la dificultad seleccionada en tu modelo Juego si es
        // necesario
        // Juego.getInstance().setDificultad(level);
    }

    private void updateSelectionEffect() {
        resetDifficultyOpacity();

        if (selectedDifficulty > 0) {
            // Activar opacidad para los niveles hasta el seleccionado
            if (selectedDifficulty >= 1)
                fd1.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 2)
                fd2.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 3)
                fd3.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 4)
                fd4.setOpacity(ACTIVE_OPACITY);
            if (selectedDifficulty >= 5)
                fd5.setOpacity(ACTIVE_OPACITY);
        }
    }

    public void cargarPersonajes(List<Heroe> personajes) {
        cont_perso.getChildren().clear();

        for (Heroe p : personajes) {
            try {
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
}