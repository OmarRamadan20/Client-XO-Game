
package com.mycompany.clientxogame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class SplashScreenController implements Initializable {

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        URL videoUrl = getClass().getResource("/splash/splash4.mp4");

        if (videoUrl != null) {
            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                try {
                    switchToModeSelection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));
            delay.setOnFinished(event -> {
                try {
                    switchToModeSelection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            delay.play();
        }
    }

    private void switchToModeSelection() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModeSelection.fxml"));
        Parent root = loader.load();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Stage nextStage = new Stage();
        nextStage.setTitle("XO Game - Select Mode");
        nextStage.setScene(new Scene(root));

        nextStage.show();
        nextStage.centerOnScreen();

        Stage currentStage = (Stage) mediaView.getScene().getWindow();
        currentStage.close();
    }
}
 