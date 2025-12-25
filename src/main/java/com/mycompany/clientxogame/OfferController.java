/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import static javafx.util.Duration.seconds;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author amr04
 */
public class OfferController implements Initializable {
    private String fromPlayer;
    @FXML
    private Button btmOfCourse;
    @FXML
    private Button btmTimeAnther;
    @FXML
    private Label timeId;
    private Timeline timeline;
    private int secondsRemaining = 10;

    public void setFromPlayer(String fromPlayer) {
        this.fromPlayer = fromPlayer;
    }
@Override
    public void initialize(URL url, ResourceBundle rb) {
       
        startCountdown();

        ServerHandler.getInstance().setListener(json -> {
            String type = json.optString("type", "");
            if (type.equals("invite_status_back")) {
                String status = json.optString("status", "");
                if (status.equals("later")) {
                    
                    if (timeline != null) timeline.stop();
                    Platform.runLater(() -> {
                        NavigateBetweeenScreens.goToAvailablePlayer(null);
                    });
                }
            }
        });
    }
    private void startCountdown() {
        timeline = new Timeline(new KeyFrame(seconds(1), event -> {
            secondsRemaining--;
            timeId.setText(String.valueOf(secondsRemaining));

            if (secondsRemaining <= 0) {
                timeline.stop();
                handleAutoReject(); 
            }
        }));
        timeline.setCycleCount(10);
        timeline.play();
    }
private void handleAutoReject() {
        Platform.runLater(() -> {
            System.out.println("Offer expired. Auto-rejecting...");
            
            sendResponse("later");
            NavigateBetweeenScreens.goToAvailablePlayer(null);
        });
    }
    @FXML
    private void onActionOfCourse(ActionEvent event) {
        if (timeline != null) timeline.stop(); 
        NavigateBetweeenScreens.mySymbol = "O";
        NavigateBetweeenScreens.isMyTurn = false;
        NavigateBetweeenScreens.currentOpponent = fromPlayer;
        
        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", "accept");
        response.put("to", fromPlayer); 
        response.put("from", LoggedUser.name);
        
        ServerHandler.getInstance().send(response);
      
        NavigateBetweeenScreens.goToPlay(event);
    }

//    @FXML
//    private void onActionTimeAnTher(ActionEvent event) {
//        JSONObject response = new JSONObject();
//        response.put("type", "invite_response");
//        response.put("status", "later");
//        response.put("to", fromPlayer);
//        response.put("from", LoggedUser.name);
//        
//        ServerHandler.getInstance().send(response);
//       
//        NavigateBetweeenScreens.goToAvailablePlayer(event);
//    }
    @FXML
    private void onActionTimeAnTher(ActionEvent event) {
        if (timeline != null) timeline.stop(); 

        sendResponse("later");
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

    private void sendResponse(String status) {
        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", status);
        response.put("to", fromPlayer);
        response.put("from", LoggedUser.name);
        ServerHandler.getInstance().send(response);
    }
    @FXML
    private void onActionTimeLine(MouseEvent event) {
    }
}