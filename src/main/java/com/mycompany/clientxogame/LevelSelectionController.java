package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class LevelSelectionController implements Initializable {

    @FXML
    private Button btmEasy; 
    @FXML
    private Button btmMeduim;
    @FXML
    private Button btmHard;
    @FXML
    private Button btmBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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