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
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class GameRecordsController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private VBox recordsContainer;
    @FXML
    private Button playIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBack(ActionEvent event) {
        NavigateBetweeenScreens.backToShowProfile(event);
    }

    @FXML
    private void playActionRecord(ActionEvent event) {
        
        NavigateBetweeenScreens.goToPlayRecords(event);
    }
    
}
