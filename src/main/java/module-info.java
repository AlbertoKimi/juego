module com.enriquealberto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.enriquealberto to javafx.fxml;
    opens com.enriquealberto.Controladores to javafx.fxml;
    exports com.enriquealberto;
}
