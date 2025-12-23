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
public class AcceptController implements Initializable {


    @FXML
    private Button cancelBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onActionCancel(ActionEvent event) {
        JSONObject request = new JSONObject();
        request.put("type", "cancel_invite");
         request.put("to", "cancel_invite");
          request.put("from",LoggedUser.name );
        ServerHandler.getInstance().send(request);
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

}
