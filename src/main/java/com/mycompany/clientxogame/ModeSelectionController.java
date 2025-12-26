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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ModeSelectionController implements Initializable {

    @FXML
    private Button btmSingle;
    @FXML
    private Button btmPlayer;
    @FXML
    private Button btmOline;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  SoundManager.startBackgroundMusic();
        // TODO
    }

    @FXML
    private void onActionBtmSingle(ActionEvent event) {
 
        NavigateBetweeenScreens.goToSingleMode(event);
    }

    @FXML
    private void onActionBtmPlayer(ActionEvent event) {
         NavigateBetweeenScreens.goToDoubleMode(event);

    }

    @FXML
    private void onActionOnline(ActionEvent event) {
         NavigateBetweeenScreens.goToLogIn(event);

    }

    @FXML
    private void handleMouseEnter(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.08);
        btn.setScaleY(1.08);
        btn.setOpacity(0.9);
    }

    @FXML
    private void handleMouseExit(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();

        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
        btn.setOpacity(1.0);
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setTranslateY(4);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setTranslateY(0);
    }

}
