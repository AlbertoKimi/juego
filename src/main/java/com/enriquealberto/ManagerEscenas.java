package com.enriquealberto;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ManagerEscenas {
    private static ManagerEscenas instance;
    private Stage stage; // La ventana principal de la aplicación
    private HashMap<EscenaID, Scene> scenes; // Mapa para almacenar las escenas según su identificador
    private HashMap<EscenaID, Object> sceneControllers; // Mapa para almacenar los controladores de las escenas
    private EscenaID currentScene; // Variable para almacenar la escena actualmente activa

    private ManagerEscenas() {
        scenes = new HashMap<>();
        sceneControllers = new HashMap<>();
    }

    public static ManagerEscenas getInstance(){
        if (instance == null) {
            instance = new ManagerEscenas();
        }
        return instance;
    }

    public void init(Stage stage){
        this.stage = stage;
    }

    public void setScene(EscenaID sceneID, String fxml){
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/enriquealberto/vistas/" + fxml + ".fxml"));
            if (fxmlLoader.getLocation() == null) {
                throw new IllegalStateException("No se pudo encontrar el archivo FXML: " + fxml + ".fxml");
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, screenWidth * 0.83, screenHeight * 0.90);
            scene.getStylesheets().add(App.class.getResource("/com/enriquealberto/css/styles.css").toExternalForm());
            scenes.put(sceneID, scene); // Almacena la escena en el mapa

            // Guardar el controlador de la escena
            Object controller = fxmlLoader.getController();
            sceneControllers.put(sceneID, controller); // Almacena el controlador de la escena

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeScene(EscenaID sceneID){
        scenes.remove(sceneID);
        sceneControllers.remove(sceneID); // Eliminar también el controlador si se elimina la escena
    }

    public void loadScene(EscenaID sceneID) {
        if (scenes.containsKey(sceneID)){
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show();
            currentScene = sceneID; // Actualiza la escena actualmente activa
        }
    }

    public Scene getScene(EscenaID sceneID){
        return scenes.get(sceneID); // Obtiene la escena
    }

    // Obtener el controlador de una escena
    public Object getController(EscenaID sceneID){
        return sceneControllers.get(sceneID); // Devuelve el controlador de la escena
    }

    public Stage getStage() {
        return this.stage;
    }

    public EscenaID getCurrentScene() {
        // Devuelve la escena actualmente activa
        return currentScene; // Asegúrate de tener una variable que almacene la escena actual
    }
}