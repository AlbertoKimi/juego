module com.enriquealberto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.enriquealberto to javafx.fxml;
    exports com.enriquealberto;
}
