module com.enriquealberto {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;
    requires javafx.media;
    exports com.enriquealberto; // Exporta el paquete principal
    exports com.enriquealberto.Controladores; // Exporta los controladores si los usas en FXML
    exports com.enriquealberto.model;

    
}
