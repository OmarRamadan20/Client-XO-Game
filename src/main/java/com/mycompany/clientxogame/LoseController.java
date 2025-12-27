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
import javafx.util.Duration;

public class LoseController implements Initializable {

    @FXML
    private Label result;

    @FXML
    private Button newGameButton;

    @FXML
    private Button playAgainButton;

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             URL videoUrl = getClass().getResource("/videos/lose.mp4");

            if (videoUrl == null) {
                 return;
            }

            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

             mediaView.setFitWidth(400);
            mediaView.setFitHeight(300);
            mediaView.setPreserveRatio(true);

             mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

             mediaPlayer.setOnReady(() -> {
                 mediaPlayer.play();
            });

             mediaPlayer.setOnError(() -> {
             });

        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    private void newGame(ActionEvent event) {
        stopVideo();
     }

    @FXML
    private void playAgain(ActionEvent event) {
        stopVideo();
     }

    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();  
        }
    }
}