module com.mycompany.clientxogame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;
    requires org.json;


    opens com.mycompany.clientxogame to javafx.fxml;
    exports com.mycompany.clientxogame;
}
