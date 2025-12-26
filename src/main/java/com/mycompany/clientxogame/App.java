package com.mycompany.clientxogame;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = loadFXML("splash/splashScreen"); 
        
        scene = new Scene(root, 450, 450);
        
        
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlLocation = App.class.getResource("/" + fxml + ".fxml");

        if (fxmlLocation == null) {
            fxmlLocation = App.class.getResource(fxml + ".fxml");
        }

        if (fxmlLocation == null) {
            throw new IOException("تعذر العثور على ملف: " + fxml);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}