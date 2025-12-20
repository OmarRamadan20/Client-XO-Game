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
/**
 * FXML Controller class
 *
 * @author amr04
 */
public class LevelSelectionController implements Initializable {


    @FXML
    private Button btmُُEasy;
    @FXML
    private Button btmMeduim;
    @FXML
    private Button btmHard;
    @FXML
    private Button btmBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onActionBtmEasy(ActionEvent event) {
   
    NavigateBetweeenScreens.goToEasyLevel(event);
    }
    

    @FXML
    private void onActionBtmMedium(ActionEvent event) {
    NavigateBetweeenScreens.goToMeduimLevel(event);
    }

    @FXML
    private void onActionHard(ActionEvent event) {
    NavigateBetweeenScreens.goToHardLevel(event);
    
    }

    @FXML
    private void onActionBack(ActionEvent event) {
    
    NavigateBetweeenScreens.backToModeSelection(event);
    }

}
