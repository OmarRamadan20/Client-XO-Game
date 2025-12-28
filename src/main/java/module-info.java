module com.mycompany.clientxogame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;
    requires org.json;
  
    opens com.mycompany.clientxogame.ui.splash to javafx.fxml;
    opens com.mycompany.clientxogame.ui.mode to javafx.fxml;
    opens com.mycompany.clientxogame.ui.level to javafx.fxml; 
    opens com.mycompany.clientxogame.ui.auth to javafx.fxml;
    opens com.mycompany.clientxogame.ui.game.board to javafx.fxml;
    opens com.mycompany.clientxogame.ui.game.single to javafx.fxml;
    opens com.mycompany.clientxogame.ui.game.multiplayer to javafx.fxml;
    opens com.mycompany.clientxogame.ui.game.result to javafx.fxml;
    opens com.mycompany.clientxogame.ui.history to javafx.fxml;
    opens com.mycompany.clientxogame.ui.profile to javafx.fxml;

    
    opens com.mycompany.clientxogame to javafx.fxml;

    exports com.mycompany.clientxogame;
}
