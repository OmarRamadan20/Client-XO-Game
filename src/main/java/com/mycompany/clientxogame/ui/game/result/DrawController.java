package com.mycompany.clientxogame.ui.game.result;


import com.mycompany.clientxogame.model.LoggedUser;
import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.network.ServerHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.json.JSONObject;

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
            }

          
            ServerHandler.getInstance().setListener(json -> {
                String type = json.optString("type");
                
               
                if ("invite_recieved".equals(type)) { 
                    Platform.runLater(() -> {
                        stopVideo();
                        NavigationManager.invite(null, json.getString("from"));
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void newGame(ActionEvent event) {
        stopVideo();
        NavigationManager.goToAvailablePlayer(event);
    }

    @FXML
    private void playAgain(ActionEvent event) {
        stopVideo();
        
        String opponentName = NavigationManager.currentOpponent;
        if (opponentName != null) {
            JSONObject inviteReq = new JSONObject();
          
            inviteReq.put("type", "invite"); 
            inviteReq.put("from", LoggedUser.name);
            inviteReq.put("to", opponentName);

            ServerHandler.getInstance().send(inviteReq);

            NavigationManager.invitedFrom = opponentName; 
            NavigationManager.goToWaitAccept(event);
        } else {
            NavigationManager.goToAvailablePlayer(event);
        }
    }

    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}