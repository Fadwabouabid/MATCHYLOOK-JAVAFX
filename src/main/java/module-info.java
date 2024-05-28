module com.example.look {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires javafx.media;

    opens com.example.look to javafx.fxml;
    exports com.example.look;
}