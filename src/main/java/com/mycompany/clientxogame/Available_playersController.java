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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Available_playersController implements Initializable {

    @FXML
    private Label nameTxt;
    @FXML
    private Button btnInvite;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnProfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void displayName(MouseEvent event) {
    }

    @FXML
    private void invitePlayer(ActionEvent event) {
       NavigateBetweeenScreens.invite(event);
    }

    @FXML
    private void logOut(ActionEvent event) {
         NavigateBetweeenScreens.goToRegister(event);
    }

    @FXML
    private void showProfile(ActionEvent event) {
    }
    
}
