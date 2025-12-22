package com.mycompany.clientxogame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/clientxogame/ModeSelection.fxml"));

        //F:\traning\iti\Java\Final Project\ClientXOGame\src\main\resources/

        Scene scene = new Scene(root, 650,650);
        primaryStage.setTitle("XO Game");
         primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
     }
}
