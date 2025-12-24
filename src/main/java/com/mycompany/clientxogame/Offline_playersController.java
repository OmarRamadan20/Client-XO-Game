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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Offline_playersController implements Initializable {

    @FXML
    private ImageView playerAvatar;
    @FXML
    private ImageView playerAvatar1;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField player1Name;
    @FXML
    private TextField player2Name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        playerAvatar.setFitWidth(80);
        playerAvatar.setFitHeight(80);
        playerAvatar.setPreserveRatio(true);

        Circle clip = new Circle(40, 40, 40);
        playerAvatar.setClip(clip);
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        NavigateBetweeenScreens.backToModeSelection(event);
    }

    @FXML
    private void onActionPlay(ActionEvent event) {
        String name1 = player1Name.getText();
        String name2 = player2Name.getText();

        NavigateBetweeenScreens.goToTwoPlayersMode(event, name1, name2);

    }

}
