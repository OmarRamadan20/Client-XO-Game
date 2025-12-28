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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Aladawy
 */
public class WinController implements Initializable {

    @FXML private Button newGameButton;
    @FXML private Button playAgainButton;
    @FXML private MediaView mediaView;
    
    private MediaPlayer mediaPlayer; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String path = getClass().getResource("/videos/win.mp4").toExternalForm();
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

           
            ServerHandler.getInstance().setListener(json -> {
                if (json.optString("type").equals("invite_recieved")) {
                    javafx.application.Platform.runLater(() -> {
                        stopVideo();
                        NavigateBetweeenScreens.invite(null, json.getString("from"));
                    });
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void newGame(ActionEvent event) {
        stopVideo();
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

    @FXML
    private void playAgain(ActionEvent event) {
        stopVideo();
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

    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null; 
        }
    }
}