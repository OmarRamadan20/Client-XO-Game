/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

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
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import static javafx.util.Duration.seconds;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author amr04
 */
public class AcceptController implements Initializable {

    @FXML
    private Label timeId;
    /**
     * Initializes the controller class.
     */
    private Timeline timeline;
    private int secondsRemaining = 10;

    private String fromPlayer;
    @FXML
    private ProgressIndicator timerIndicator;
    @FXML
    private Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        startCountdown();

        ServerHandler.getInstance().setListener(json -> {
            String type = json.optString("type", "");
            if (type.equals("invite_status_back")) {

                if (timeline != null) {
                    timeline.stop();
                }

                String status = json.getString("status");
                Platform.runLater(() -> {
                    if ("accept".equals(status)) {
                        NavigateBetweeenScreens.mySymbol = "X";
                        NavigateBetweeenScreens.isMyTurn = true;
                        NavigateBetweeenScreens.currentOpponent = NavigateBetweeenScreens.invitedFrom;
                        SoundManager.getInstance().playButton("enter");

                        NavigateBetweeenScreens.goToPlay(null);
                    } else {
                        NavigateBetweeenScreens.goToAvailablePlayer(null);
                    }
                });
            }
        });
    }

    private void startCountdown() {
        timeline = new Timeline(new KeyFrame(seconds(1), event -> {
            secondsRemaining--;
            timeId.setText(String.valueOf(secondsRemaining));

            // تحديث الدائرة (البروجرس)
            double progress = secondsRemaining / 10.0;
            timerIndicator.setProgress(progress);

            if (secondsRemaining <= 0) {
                stopTimeline();
                handleTimeout();
            }
        }));
        timeline.setCycleCount(10);
        timeline.play();
    }

    private void handleTimeout() {
        Platform.runLater(() -> {
            System.out.println("Invitation expired.");

            NavigateBetweeenScreens.goToAvailablePlayer(null);
        });
    }

    public void setFromPlayer(String fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    @FXML
    private void onActionCancel(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }

        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", "later");
        response.put("to", NavigateBetweeenScreens.invitedFrom);
        response.put("from", LoggedUser.name);

        ServerHandler.getInstance().send(response);
        SoundManager.getInstance().playButton("back");

        NavigateBetweeenScreens.goToAvailablePlayer(event);
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
