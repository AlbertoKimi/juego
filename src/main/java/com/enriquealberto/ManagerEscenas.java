package com.enriquealberto;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Clase singleton que gestiona las escenas/pantallas de la aplicación.
 * Permite cargar, almacenar y cambiar entre diferentes escenas del juego,
 * así como mantener referencia a sus controladores.
 *
 * @author Enrique
 * @author Alberto
 */
public class ManagerEscenas {
    private static ManagerEscenas instance;
    private Stage stage; // La ventana principal de la aplicación
    private HashMap<EscenaID, Scene> scenes; // Mapa para almacenar las escenas según su identificador
    private HashMap<EscenaID, Object> sceneControllers; // Mapa para almacenar los controladores de las escenas
    private EscenaID currentScene; // Variable para almacenar la escena actualmente activa

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ManagerEscenas() {
        scenes = new HashMap<>();
        sceneControllers = new HashMap<>();
    }

    /**
     * Obtiene la instancia única del ManagerEscenas (Singleton).
     *
     * @return La instancia única del ManagerEscenas
     */
    public static ManagerEscenas getInstance() {
        if (instance == null) {
            instance = new ManagerEscenas();
        }
        return instance;
    }

    /**
     * Inicializa el ManagerEscenas con el Stage principal.
     *
     * @param stage El Stage principal de la aplicación
     */
    public void init(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga y registra una nueva escena en el manager.
     *
     * @param sceneID Identificador único de la escena
     * @param fxml Nombre del archivo FXML (sin extensión) que define la escena
     * @throws IllegalStateException Si no se encuentra el archivo FXML especificado
     */
    public void setScene(EscenaID sceneID, String fxml) {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/enriquealberto/vistas/" + fxml + ".fxml"));
            if (fxmlLoader.getLocation() == null) {
                throw new IllegalStateException("No se pudo encontrar el archivo FXML: " + fxml + ".fxml");
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, screenWidth * 0.85, screenHeight * 0.88);
            scene.getStylesheets().add(App.class.getResource("/com/enriquealberto/css/styles.css").toExternalForm());
            scenes.put(sceneID, scene); // Almacena la escena en el mapa

            // Guardar el controlador de la escena
            Object controller = fxmlLoader.getController();
            sceneControllers.put(sceneID, controller); // Almacena el controlador de la escena

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una escena y su controlador del manager.
     *
     * @param sceneID Identificador de la escena a eliminar
     */
    public void removeScene(EscenaID sceneID) {
        scenes.remove(sceneID);
        sceneControllers.remove(sceneID); // Eliminar también el controlador si se elimina la escena
    }

    /**
     * Carga y muestra una escena previamente registrada.
     *
     * @param sceneID Identificador de la escena a cargar
     */
    public void loadScene(EscenaID sceneID) {
        if (scenes.containsKey(sceneID)) {
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show();
            currentScene = sceneID; // Actualiza la escena actualmente activa
        }
    }

    /**
     * Obtiene una escena registrada.
     *
     * @param sceneID Identificador de la escena a obtener
     * @return La escena correspondiente al ID, o null si no existe
     */
    public Scene getScene(EscenaID sceneID) {
        return scenes.get(sceneID); // Obtiene la escena
    }

    /**
     * Obtiene el controlador asociado a una escena.
     *
     * @param sceneID Identificador de la escena
     * @return El controlador de la escena, o null si no existe
     */
    public Object getController(EscenaID sceneID) {
        return sceneControllers.get(sceneID); // Devuelve el controlador de la escena
    }

    /**
     * Obtiene el Stage principal de la aplicación.
     *
     * @return El Stage principal
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Obtiene el identificador de la escena actualmente activa.
     *
     * @return El identificador de la escena actual
     */
    public EscenaID getCurrentScene() {
        return currentScene;
    }
}