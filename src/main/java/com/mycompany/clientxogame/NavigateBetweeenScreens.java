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

    public static void backToModeSelection(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/ModeSelection.fxml", "Mode Selection");
    }

    public static void goToEasyLevel(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Easy Level");
    }

    public static void goToMeduimLevel(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Meduim Level");
    }

    public static void goToHardLevel(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Hard Level");
    }

    public static void backToLevelSelection(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/LevelSelection.fxml", "Level Selection");
    }

    public static void goToDoubleMode(ActionEvent event) {
        changeScene(event, "/UI/players/offline_players.fxml", "Offline Players");
    }

    public static void goToPlay(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "Play Offline");
    }

    public static void backToOfflinePlayer(ActionEvent event) {
        changeScene(event, "/UI/players/offline_players.fxml", "Offline Players");
    }

    public static void goToSignUp(ActionEvent event) {
        changeScene(event, "/UI/Register/signUp.fxml", "Sign Up");
    }

    public static void goToLogIn(ActionEvent event) {
        changeScene(event, "/UI/Register/login.fxml", "Login");
    }

    public static void goToAvailblePlayerAfterLogin(ActionEvent event) {
        changeScene(event, "/UI/players/available_players.fxml", "Available Players");
    }
//ui not exist

    public static void goToShowProfile(ActionEvent event) {
        changeScene(event, "/UI/players/available_players.fxml", "Available Players");
    }

    public static void logOut(ActionEvent event) {
        changeScene(event, "/UI/Register/signUp.fxml", "Sign Up");
    }

    public static void invite(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/accept.fxml", "Invite To Play");
    }

    public static void acceptToPlay(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Game");
    }

    public static void rejectInvitation(ActionEvent event) {
        changeScene(event, "/UI/players/available_players.fxml", "Available Players");
    }

    public static void cancelInvitation(ActionEvent event) {
        changeScene(event, "/UI/players/available_players.fxml", "Available Players");
    }

    public static void backFromProfile(ActionEvent event) {
        changeScene(event, "/UI/players/available_players.fxml", "Available Players");
    }

    public static void goToShowRecords(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/win.fxml", "Win");
    }

    public static void winGame(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/win.fxml", "Win");
    }

    public static void loseGame(ActionEvent event) {
        changeScene(event, "/com/mycompany/clientxogame/lose.fxml", "Lose");
    }

    public static void goToPlayAgain(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Game");
    }

    public static void goToNewGame(ActionEvent event) {
        changeScene(event, "/game/board.fxml", "XO Game");
    }

}
