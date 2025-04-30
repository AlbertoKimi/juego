package com.enriquealberto;

import java.io.IOException;

import com.enriquealberto.model.GestorMapas;
import javafx.application.Application;
import com.enriquealberto.model.Mapa;
import com.enriquealberto.model.Proveedor;
import javafx.stage.Stage;
import javafx.scene.image.Image;





/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * Método que se ejecuta cuando la aplicación JavaFX inicia.
     * 
     * Este método configura el título de la ventana, establece el icono de la
     * ventana,
     * inicializa el <code>SceneManager</code>, configura las escenas disponibles y
     * carga la escena principal.
     * 
     * @param stage el <code>Stage</code> principal de la aplicación, que representa
     *              la ventana.
     * @throws IOException si ocurre un error al cargar los recursos o las vistas.
     */

    @Override
    public void start(Stage stage) throws IOException {
        GestorMapas gestorMapas = Proveedor.getInstance().getGestorMapas();
        Mapa primerMapa = gestorMapas.getMapaActual();
        int filas = primerMapa.getMapa().size();
        int columnas = primerMapa.getMapa().get(0).size();

        // Establece el título de la ventana
        stage.setTitle("Mazmorras frutales");
        stage.setResizable(false); // Evita que la ventana sea redimensionable
       

        // Establece el icono de la ventana
        
        stage.getIcons().add(new Image(App.class.getResource("/com/enriquealberto/imagenes/icono.png").toExternalForm()));
         

        // Obtiene la instancia del SceneManager
        ManagerEscenas sm = ManagerEscenas.getInstance();

        // Inicializa el SceneManager con el stage y una ruta de estilos
        sm.init(stage);

        // Configura las escenas con identificadores y tamaños
        sm.setScene(EscenaID.JUEGO, "Juego"/* ,900,600 */);
        sm.setScene(EscenaID.ESTADISTICAS, "estadisticas"/* ,900,600 */);
        sm.setScene(EscenaID.CONTENEDOR, "contenedor"/* ,900,600 */);
        /*
         * sm.setScene(EscenaID.PORTADA, "Portada", 800, 500);
         * sm.setScene(EscenaID.SELECTION, "Selection", 800, 500);
         */

        sm.setScene(EscenaID.PORTADA, "Portada");
        sm.setScene(EscenaID.SELECTION, "selection");
        
        
        // Carga la escena principal
        sm.loadScene(EscenaID.JUEGO);
    }

    /**
     * El método principal que lanza la aplicación JavaFX.
     * 
     * @param args los argumentos de la línea de comandos, no utilizados en este
     *             caso.
     */
    public static void main(String[] args) {
        launch();
    }

    // Removed duplicate start(Stage) method to resolve the compile error.

}