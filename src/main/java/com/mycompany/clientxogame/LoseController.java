package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.json.JSONObject;

public class LoseController implements Initializable {

    @FXML private Label result;
    @FXML private Button newGameButton;
    @FXML private Button playAgainButton;
    @FXML private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            URL videoUrl = getClass().getResource("/videos/lose.mp4");
            if (videoUrl != null) {
                Media media = new Media(videoUrl.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);

                mediaView.setFitWidth(400);
                mediaView.setFitHeight(300);
                mediaView.setPreserveRatio(true);

                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            }

          
            ServerHandler.getInstance().setListener(json -> {
                if (json.optString("type").equals("invite_recieved")) {
                    Platform.runLater(() -> {
              
                        if (mediaView.getMediaPlayer() != null) {
                            mediaView.getMediaPlayer().stop();
                        }
                        NavigateBetweeenScreens.invite(null, json.getString("from"));
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void newGame(ActionEvent event) {
 
        if (mediaView.getMediaPlayer() != null) {
            mediaView.getMediaPlayer().stop();
        }
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

    @FXML
    private void playAgain(ActionEvent event) {
 
        if (mediaView.getMediaPlayer() != null) {
            mediaView.getMediaPlayer().stop();
        }

        String opponentName = NavigateBetweeenScreens.currentOpponent;
        if (opponentName != null) {
            JSONObject inviteReq = new JSONObject();
            inviteReq.put("type", "invite");
            inviteReq.put("from", LoggedUser.name);
            inviteReq.put("to", opponentName);

            ServerHandler.getInstance().send(inviteReq);

        
            NavigateBetweeenScreens.invitedFrom = opponentName; 
            NavigateBetweeenScreens.goToWaitAccept(event);
        } else {
            NavigateBetweeenScreens.goToAvailablePlayer(event);
        }
    }
}