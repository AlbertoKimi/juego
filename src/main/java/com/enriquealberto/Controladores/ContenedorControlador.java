package com.enriquealberto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controlador principal que gestiona el contenedor de las vistas del juego.
 * Se encarga de cargar y mostrar dinámicamente los paneles de juego y estadísticas.
 */
public class ContenedorControlador {

    @FXML
    private AnchorPane juego;         // Panel contenedor para la vista principal del juego
    @FXML
    private AnchorPane estadistica;  // Panel contenedor para la vista de estadísticas

    /**
     * Carga y muestra los paneles de juego y estadísticas dentro del contenedor principal.
     * Este método debe llamarse explícitamente después de inicializar el controlador.
     *
     * @throws RuntimeException Si ocurre un error al cargar los archivos FXML
     */
    public void cargarPaneles() {
        try {
            // Cargar y configurar el panel de juego
            cargarPanel("/com/enriquealberto/vistas/Juego.fxml", juego);

            // Cargar y configurar el panel de estadísticas
            cargarPanel("/com/enriquealberto/vistas/estadisticas.fxml", estadistica);

        } catch (IOException e) {
            System.err.println("Error crítico al cargar los paneles internos: " + e.getMessage());
            throw new RuntimeException("Error al inicializar las vistas del juego", e);
        }
    }

    /**
     * Método helper para cargar un archivo FXML en un panel contenedor.
     *
     * @param fxmlPath Ruta del archivo FXML a cargar
     * @param contenedor Panel AnchorPane donde se insertará la vista cargada
     * @throws IOException Si hay un error al leer el archivo FXML
     */
    private void cargarPanel(String fxmlPath, AnchorPane contenedor) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Configurar anclajes para que ocupe todo el espacio disponible
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);

        contenedor.getChildren().setAll(root);
    }
}