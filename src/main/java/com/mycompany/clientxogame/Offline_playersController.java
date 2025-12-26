/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import static javafx.util.Duration.seconds;

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

        String p1 = player1Name.getText().trim();
        String p2 = player2Name.getText().trim();

        if (p1.isEmpty() || p2.isEmpty()) {
            showSimpleMessage("Please enter names for both players");
            return;
        }

        NavigateBetweeenScreens.goToTwoPlayersMode(event, p1, p2);
    }

    private void showSimpleMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(null);

        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Node okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(
                seconds(2),
                ae -> alert.close()
        ));
        timeline.play();

        alert.show();
    }

    @FXML
    private void onActionEnterNameplayer1(ActionEvent event) {
    }

    @FXML
    private void onActionEnterPlayer2(ActionEvent event) {
    }

    @FXML
    private void handleMouseEnter(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.1);
        btn.setScaleY(1.1);
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
