/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class NavigateBetweeenScreens {

    private static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(NavigateBetweeenScreens.class.getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            System.getLogger(NavigateBetweeenScreens.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static void goToSingleMode(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/LevelSelection.fxml", "Level Selection");
    }
              public static void backToModeSelection(ActionEvent event)
    {
        changeScene(event, "/com/mycompany/clientxogame/    ModeSelection.fxm", "Mode Selection");
    }
    public static void goToEasyLevel(ActionEvent event) {
        changeScene(event, "/game/board.game", "XO Easy Level");
    }

    public static void goToMeduimLevel(ActionEvent event) {
        changeScene(event, "/game/board.game", "XO Meduim Level");
    }

    public static void goToHardLevel(ActionEvent event) {
        changeScene(event, "/game/board.game", "XO Hard Level");
    }
            public static void backToLevelSelection(ActionEvent event)
    {
        changeScene(event, "/com/mycompany/clientxogame/LevelSelection.fxm", "Level Selection");
    }

    public static void goToDoubleMode(ActionEvent event) {
        changeScene(event, "/game/board.game", "XO");
    }

    public static void goToSignUp(ActionEvent event) {
        changeScene(event, "/Register/signUp.fxml", "Sign Up");
    }

    public static void goToLogIn(ActionEvent event) {
        changeScene(event, "/Register/login.fxml", "Login");
    }
}
