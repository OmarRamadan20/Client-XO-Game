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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LoginController implements Initializable {

    @FXML
    private TextField enterName;
    @FXML
    private TextField enterPassword;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogin;
    @FXML
    private Button registerID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logIn(ActionEvent event) {
        NavigateBetweeenScreens.goToAvailablePlayer(event);
    }

    @FXML
    private void register(ActionEvent event) {
          NavigateBetweeenScreens.goToRegister(event);
    }

    @FXML
    private void actionBtn(ActionEvent event) {
        NavigateBetweeenScreens.backToModeSelection(event);
    }
 
    
}
