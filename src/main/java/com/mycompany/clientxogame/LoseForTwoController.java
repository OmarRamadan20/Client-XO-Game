/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.ui.game.board.TwoPlayersBoardController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author amr04
 */
public class LoseForTwoController implements Initializable {

   
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
                .getResource("/videos/lose.mp4")
                .toExternalForm();

        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

    @FXML
    private void newGame(ActionEvent event) {
                TwoPlayersBoardController.resetScores();
                NavigationManager.backToModeSelection(event);

    }

    @FXML
    private void playAgain(ActionEvent event) {

        NavigationManager.goToTwoPlayersMode(event, TwoPlayersBoardController.player1,TwoPlayersBoardController.player2);
             
                
        }
    }

 