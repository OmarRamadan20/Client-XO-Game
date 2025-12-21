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
 * @author amr04
 */
public class signUpController implements Initializable {


    @FXML
    private TextField enterName;
    @FXML
    private TextField enterEmail;
    @FXML
    private TextField enterPassword;
    @FXML
    private TextField confirmPass;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSignUp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void ActionBack(ActionEvent event) {
         NavigateBetweeenScreens.goToLogIn(event);
    }

    @FXML
    private void signUp(ActionEvent event) {
         NavigateBetweeenScreens.goToLogIn(event);
    }

}
