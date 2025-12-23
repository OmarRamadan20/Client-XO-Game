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
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author amr04
 */
public class OfferController implements Initializable {
    private String fromPlayer;

    public void setFromPlayer(String fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void onActionOfCourse(ActionEvent event) {
        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", "accept");
        response.put("to", fromPlayer); 
        response.put("from", LoggedUser.name);
        
        ServerHandler.getInstance().send(response);
      
        NavigateBetweeenScreens.goToPlay(event);
    }

    @FXML
    private void onActionTimeAnTher(ActionEvent event) {
        JSONObject response = new JSONObject();
        response.put("type", "invite_response");
        response.put("status", "later");
        response.put("to", fromPlayer);
        response.put("from", LoggedUser.name);
        
        ServerHandler.getInstance().send(response);
       
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }
}