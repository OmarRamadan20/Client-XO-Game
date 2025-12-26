package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
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
    private AudioClip enterClickSound;
    private AudioClip backClickSound;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            enterClickSound = new AudioClip(getClass().getResource("/sounds/enter_click.mp3").toExternalForm());
            backClickSound = new AudioClip(getClass().getResource("/sounds/back_click.mp3").toExternalForm());

        } catch (Exception e) {
            System.err.println(" Audio Not Found ");
        }
    }

    private void playClick() {
        if (enterClickSound != null) {
            enterClickSound.play();
        }
    }

    private void playBackClick() {
        if (backClickSound != null) {
            backClickSound.play();
        }
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
        ((Button) event.getSource()).setTranslateY(3);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(0);
    }

    @FXML
    private void onActionBtmEasy(ActionEvent event) {
        playClick();
        NavigateBetweeenScreens.goToEasyLevel(event);
    }

    @FXML
    private void onActionBtmMedium(ActionEvent event) {
        playClick();

        NavigateBetweeenScreens.goToMeduimLevel(event);
    }

    @FXML
    private void onActionHard(ActionEvent event) {
        playClick();

        NavigateBetweeenScreens.goToHardLevel(event);
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        playBackClick();
        NavigateBetweeenScreens.backToModeSelection(event);
    }
}