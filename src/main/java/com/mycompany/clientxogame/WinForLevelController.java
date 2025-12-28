/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.ui.game.board.SinglePlayerBoardController;
import com.mycompany.clientxogame.ui.game.board.TwoPlayersBoardController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author amr04
 */
public class WinForLevelController implements Initializable {

    private String difficulty;

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    @FXML
    private Button newGameButton;
    @FXML
    private Button playAgainButton;
    @FXML
    private MediaView mediaView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String path = getClass()
                .getResource("/videos/win.mp4")
                .toExternalForm();

        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

    @FXML
    private void newGame(ActionEvent event) {
        SinglePlayerBoardController.resetScores();
        NavigationManager.backToLevelSelection(event);

    }

    @FXML
    private void playAgain(ActionEvent event) {

        switch (difficulty) {
            case "Easy":
                NavigationManager.goToEasyLevel(event);
                break;
            case "Medium":
                NavigationManager.goToMeduimLevel(event);
                break;
            case "Hard":
                NavigationManager.goToHardLevel(event);
                break;
        }
    }

}
