module com.enriquealberto {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;
    requires javafx.media;
    exports com.enriquealberto;
}
