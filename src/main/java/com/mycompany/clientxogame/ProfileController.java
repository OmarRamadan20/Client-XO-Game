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
import javafx.scene.input.MouseEvent;

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
    private Button HistoryButtonId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LableId.setText(LoggedUser.name);
        ScoreId.setText(String.valueOf(LoggedUser.score));

    }

    @FXML
    private void onBack(ActionEvent event) {
 
        SoundManager.getInstance().playButton("back");
        NavigateBetweeenScreens.backFromProfile(event);
     }

    @FXML
    private void onRecord(ActionEvent event) {
 
        SoundManager.getInstance().playButton("enter");
        NavigateBetweeenScreens.goToShowRecords(event);
     }

    @FXML
    private void onHistory(ActionEvent event) {
        NavigateBetweeenScreens.goToShowHistory(event);
        System.out.println("History Button Clicked!");
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
        ((Button) event.getSource()).setTranslateY(4);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(0);
    }
}

