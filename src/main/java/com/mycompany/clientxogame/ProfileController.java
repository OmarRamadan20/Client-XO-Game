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
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author Aladawy
 */
public class ProfileController implements Initializable {


    @FXML
    private ImageView ImageId;
    @FXML
    private Label LableId;
    @FXML
    private Label ScoreId;
    @FXML
    private Button BackButtonId;
    @FXML
    private Button RecordsButtonId;
    @FXML
    private Button history;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    LableId.setText(LoggedUser.name);
    ScoreId.setText(String.valueOf(LoggedUser.score));

    }    
    
    @FXML
    private void onBack(ActionEvent event) {
        NavigateBetweeenScreens.backFromProfile(event);
    }

    @FXML
    private void onRecord(ActionEvent event) {
        NavigateBetweeenScreens.goToShowRecords(event);
    }

    @FXML
    private void onHistory(ActionEvent event) {
   NavigateBetweeenScreens.goToShowHistory(event);
    
    }

}
