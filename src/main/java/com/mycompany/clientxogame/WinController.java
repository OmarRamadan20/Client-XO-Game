/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

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
 * FXML Controller class
 *
 * @author Aladawy
 */
public class WinController implements Initializable {

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
    NavigateBetweeenScreens.goToNewGame(event);
    
    }

    @FXML
    private void playAgain(ActionEvent event) {
    }
    
}
