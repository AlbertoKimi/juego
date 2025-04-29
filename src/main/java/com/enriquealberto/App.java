package com.enriquealberto;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.enriquealberto.model.GestorMapas;
import com.enriquealberto.model.Mapa;
import com.enriquealberto.model.Proveedor;

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

        // Definir el tamaño de las celdas
        double anchoCelda = 100; 
        double altoCelda = 70; 

        // Calcular el tamaño del Stage
        double anchoVentana = columnas * anchoCelda;
        double altoVentana = filas * altoCelda;

        // Establece el título de la ventana
        stage.setTitle("Mazmorras frutales");
        stage.setResizable(false); // Evita que la ventana sea redimensionable
        stage.setWidth(anchoVentana); // Establece el ancho de la ventana
        stage.setHeight(altoVentana); // Establece la altura de la ventana

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

        // Carga la escena principal
        sm.loadScene(EscenaID.CONTENEDOR);
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

}