package com.mycompany.clientxogame.ui.game.multiplayer;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.network.ServerHandler;
import com.mycompany.clientxogame.sound.SoundManager;
import com.mycompany.clientxogame.model.LoggedUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.json.JSONObject;

public class OfferController implements Initializable {

    private String fromPlayer;

    @FXML
    private Button btmOfCourse;
    @FXML
    private Button btmTimeAnther;
    @FXML
    private Label timeId;
    @FXML
    private ProgressIndicator timerIndicator;

    private Timeline timeline;
    private int secondsRemaining = 10;

    public void setFromPlayer(String fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTimer();

        ServerHandler.getInstance().setListener(json -> {
            String type = json.optString("type", "");
            if (type.equals("invite_status_back")) {
                String status = json.optString("status", "");
                if (status.equals("later")) {
                    stopTimeline();
                    Platform.runLater(() -> {
                        NavigationManager.goToAvailablePlayer(null);
                    });
                }
            }
        });
    }

    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            timeId.setText(String.valueOf(secondsRemaining));

            double progress = secondsRemaining / 10.0;
            timerIndicator.setProgress(progress);

            if (secondsRemaining <= 0) {
                stopTimeline();
                handleAutoReject();
            }
        }));

        timeline.setCycleCount(10);
        timeline.play();
    }

    private void handleAutoReject() {
        Platform.runLater(() -> {
            sendResponse("later");
            NavigationManager.goToAvailablePlayer(null);
        });
    }

    @FXML
    private void onActionOfCourse(ActionEvent event) {
        stopTimeline();
            SoundManager.getInstance().playButton("enter");

        NavigationManager.mySymbol = "O";
        NavigationManager.isMyTurn = false;
        NavigationManager.currentOpponent = fromPlayer;

        sendResponse("accept");
        NavigationManager.goToPlay(event);
    }

    @FXML
    private void onActionTimeAnTher(ActionEvent event) {
        stopTimeline();
        sendResponse("later");
            SoundManager.getInstance().playButton("back");

        NavigationManager.goToAvailablePlayer(event);
    }

    private void sendResponse(String status) {
        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", status);
        response.put("to", fromPlayer);
        
        response.put("from", LoggedUser.name);
 
        response.put("fromEmail", LoggedUser.gmail);
         ServerHandler.getInstance().send(response);
        
    }

    private void stopTimeline() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        Button btn = (Button) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
        st.setToX(1.05);
        st.setToY(1.05);
        st.play();
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(3);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(0);
    }
}
