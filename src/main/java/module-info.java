/**
 * Módulo principal de la aplicación Mazmorras Frutales.
 *
 * <p>Este módulo define las dependencias y configuraciones necesarias para la aplicación JavaFX,
 * incluyendo los paquetes que deben ser accesibles para JavaFX FXML y las dependencias requeridas.</p>
 *
 * <p><b>Configuraciones principales:</b>
 * <ul>
 *   <li>Requiere módulos de JavaFX (controls, fxml, graphics, media)</li>
 *   <li>Exporta los paquetes principales de la aplicación</li>
 *   <li>Abre los paquetes necesarios para la inyección de FXML</li>
 * </ul>
 * </p>
 *
 * @author Enrique
 * @author Alberto
 */
module com.enriquealberto {
    // Dependencias requeridas
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.media;

    // Paquetes abiertos para FXML
    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;

    // Paquetes exportados
    exports com.enriquealberto; // Paquete principal de la aplicación
    exports com.enriquealberto.Controladores; // Controladores de las vistas FXML
    exports com.enriquealberto.model; // Modelos y clases de dominio
    exports com.enriquealberto.interfaces; // Interfaces utilizadas en la aplicación
}