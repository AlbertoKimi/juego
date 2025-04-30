package com.enriquealberto;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * La clase <code>SceneManager</code> es responsable de gestionar las escenas de la aplicación JavaFX.
 * Permite cargar, almacenar, cambiar y eliminar escenas. Además, gestiona la asignación de hojas de estilo (CSS)
 * para cada escena y se asegura de que la escena actual se cargue en la ventana principal (Stage).
 */
public class ManagerEscenas {
    // Instancia única (singleton) de SceneManager
    private static ManagerEscenas instance;

    private Stage stage; // La ventana principal de la aplicación
    /*private URL styles;*/ // Ruta a la hoja de estilo CSS que se aplica a las escenas
    private HashMap<EscenaID, Scene> scenes; // Mapa para almacenar las escenas según su identificador

    /**
     * Constructor privado de <code>SceneManager</code>. 
     * Inicializa el mapa de escenas vacío.
     */
    private ManagerEscenas() {
        scenes = new HashMap<>();
    }

    /**
     * Método estático para obtener la instancia única de <code>SceneManager</code> (patrón Singleton).
     * 
     * @return La instancia única de <code>SceneManager</code>.
     */
    public static ManagerEscenas getInstance(){
        if (instance == null) {
            instance = new ManagerEscenas();
        }
        return instance;
    }

    /**
     * Inicializa el <code>SceneManager</code> con el <code>Stage</code> principal y la ruta de la hoja de estilo.
     * 
     * @param stage la ventana principal de la aplicación donde se mostrarán las escenas.
     * @param styles el nombre de la hoja de estilo CSS a aplicar a las escenas.
     */
    
    public void init(Stage stage){
        this.stage = stage;
        /*this.styles = App.class.getResource("styles/" + styles + ".css");*/ // Ruta al archivo CSS
    }

    /**
     * Establece una escena, cargando un archivo FXML y configurando su tamaño.
     * La escena también se asocia con la hoja de estilo definida previamente.
     * 
     * @param sceneID el identificador único de la escena.
     * @param fxml el nombre del archivo FXML que define la vista de la escena.
     * @param width el ancho de la escena.
     * @param height el alto de la escena.
     */
    public void setScene(EscenaID sceneID, String fxml/*,int width, int height*/){
                Screen screen = Screen.getPrimary();

        // Obtener el tamaño de la pantalla
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
       
        try {
            // Carga el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/enriquealberto/vistas/" + fxml + ".fxml"));
            if (fxmlLoader.getLocation() == null) { // Verificar si la ubicación es nula
                throw new IllegalStateException("No se pudo encontrar el archivo FXML: " + fxml + ".fxml");
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, screenWidth*0.62, screenHeight*0.75); // Crea la escena con el tamaño especificado
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con el identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }

    /**
     * Elimina una escena previamente almacenada usando su identificador.
     * 
     * @param sceneID el identificador único de la escena que se desea eliminar.
     */
    public void removeScene(EscenaID sceneID){
        scenes.remove(sceneID); // Elimina la escena del mapa
    }

    /**
     * Carga y muestra una escena previamente almacenada en el <code>SceneManager</code>.
     * 
     * @param sceneID el identificador único de la escena que se desea cargar.
     */
    public void loadScene(EscenaID sceneID) {
        if (scenes.containsKey(sceneID)){
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show(); // Muestra la ventana con la nueva escena
        }
    }

    @SuppressWarnings("exports")
    public Scene getScene(EscenaID sceneID){
        if (scenes.containsKey(sceneID)){
            return scenes.get(sceneID);
        } else {
            System.err.println("La escena seleccionada no existe");
            return null;
        }
    }
}