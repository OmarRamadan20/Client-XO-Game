module com.mycompany.clientxogame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.clientxogame to javafx.fxml;
    exports com.mycompany.clientxogame;
}
