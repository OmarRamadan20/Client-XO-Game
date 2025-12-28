package com.mycompany.clientxogame.ui.game.result;

import com.mycompany.clientxogame.ui.game.board.OnlineBoardController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class DrawController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button newGameButton;

    @FXML
    private Button playAgainButton;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             URL videoUrl = getClass().getResource("/videos/draw.mp4");

            if (videoUrl != null) {
                Media media = new Media(videoUrl.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                
                 mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                
                 mediaPlayer.setOnReady(() -> mediaPlayer.play());
            } else {
             }
        } catch (Exception e) {
         }
    }

    @FXML
    private void newGame(ActionEvent event) {
        
        stopVideo();
        OnlineBoardController.resetScores();
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