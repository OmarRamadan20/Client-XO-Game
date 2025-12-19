/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Offline_playersController implements Initializable {

    @FXML
    private ImageView playerAvatar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

      
        playerAvatar.setFitWidth(80);
        playerAvatar.setFitHeight(80);
        playerAvatar.setPreserveRatio(true);

     
        Circle clip = new Circle(40, 40, 40);
        playerAvatar.setClip(clip);
    }
}