/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONObject;
/**
 * FXML Controller class
 *
 * @author amr04
 */
public class AcceptController implements Initializable {


    @FXML
    private Button cancelBtn;
    /**
     * Initializes the controller class.
     */



@Override
public void initialize(URL url, ResourceBundle rb) {
    ServerHandler.getInstance().setListener(json -> {
        String type = json.optString("type", "");
        if (type.equals("invite_status_back")) {
            String status = json.getString("status");
            Platform.runLater(() -> {
              
                Stage currentStage = (Stage) cancelBtn.getScene().getWindow();
                
                if ("accept".equals(status)) {

                    NavigateBetweeenScreens.goToPlay(null);
                } else {
                    NavigateBetweeenScreens.goToAvailablePlayer(null);
                }
            });
        }
    });
}
    private String fromPlayer;

public void setFromPlayer(String fromPlayer) {
    this.fromPlayer = fromPlayer;
}



    @FXML
    private void onActionCancel(ActionEvent event) {

    JSONObject response = new JSONObject();
    response.put("type", "invite_response");
    response.put("status", "later"); 
    response.put("to", NavigateBetweeenScreens.invitedFrom); 
    response.put("from", LoggedUser.name);
    ServerHandler.getInstance().send(response);
    
    NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

}
