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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author Aladawy
 */
public class DoubleBoardController implements Initializable {


    @FXML
    private Button BackButtonId;
    @FXML
    private Label playerOneScore;
    @FXML
    private Text cell00;
    @FXML
    private Text cell01;
    @FXML
    private Text cell02;
    @FXML
    private Text cell10;
    @FXML
    private Text cell11;
    @FXML
    private Text cell12;
    @FXML
    private Text cell20;
    @FXML
    private Text cell21;
    @FXML
    private Text cell22;
    @FXML
    private Label PlayerTwoScore;
    @FXML
    private Line winLine;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onBack(ActionEvent event) {
    }

}
