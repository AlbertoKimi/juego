module com.enriquealberto {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
    requires transitive javafx.graphics;

    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;
=======
    requires javafx.graphics;
    requires javafx.media;

    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;

>>>>>>> enrique
    exports com.enriquealberto;
}
