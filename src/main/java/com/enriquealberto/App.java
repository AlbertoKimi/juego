package com.enriquealberto;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

  /**
     * Método que se ejecuta cuando la aplicación JavaFX inicia.
     * 
     * Este método configura el título de la ventana, establece el icono de la ventana,
     * inicializa el <code>SceneManager</code>, configura las escenas disponibles y carga la escena principal.
     * 
     * @param stage el <code>Stage</code> principal de la aplicación, que representa la ventana.
     * @throws IOException si ocurre un error al cargar los recursos o las vistas.
     */
    
    @Override
    public void start(Stage stage) throws IOException {
        // Establece el título de la ventana
        stage.setTitle("Estructura base de una aplicación en JAVAFX");
        
        // Establece el icono de la ventana
        //stage.getIcons().add(new Image(App.class.getResource("/com/alberto/imagenes/icono.jpg").toExternalForm()));
        
        // Obtiene la instancia del SceneManager
        ManagerEscenas sm = ManagerEscenas.getInstance();
        
        // Inicializa el SceneManager con el stage y una ruta de estilos
        sm.init(stage);
        
        // Configura las escenas con identificadores y tamaños
        sm.setScene(EscenaID.PORTADA, "Portada");
        sm.setScene(EscenaID.SELECTION, "selection");
        
        
        // Carga la escena principal
        sm.loadScene(EscenaID.PORTADA);
    }

    /**
     * El método principal que lanza la aplicación JavaFX.
     * 
     * @param args los argumentos de la línea de comandos, no utilizados en este caso.
     */
    public static void main(String[] args) {
        launch();
    }

}