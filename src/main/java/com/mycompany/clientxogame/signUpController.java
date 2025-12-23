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
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author amr04
 */
public class signUpController implements Initializable {

    @FXML
    private TextField enterName;
    @FXML
    private TextField enterEmail;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSignUp;
    @FXML
    private PasswordField passID;
    @FXML
    private PasswordField confirmPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ActionBack(ActionEvent event) {
        NavigateBetweeenScreens.goToLogIn(event);
    }

    @FXML
    private void signUp(ActionEvent event) {

        String name = enterName.getText().trim();
        String gmail = enterEmail.getText().trim();

        String password = passID.getText();
        String confirmPassword = confirmPass.getText();
        if (name.isEmpty() || gmail.isEmpty() || password.isEmpty()) {
            showSimpleMessage("Please Fill All Fields");
            return;
        }

        if (!gmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showSimpleMessage("Invalid Email Format (ex: user@gmail.com)");
            return;
        }

        if (password.length() < 8) {
            showSimpleMessage("Password must be at least 8 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showSimpleMessage("Passwords do not match");
            return;
        }
        JSONObject request = new JSONObject();
        request.put("type", "signup");
        request.put("name", name);
        request.put("gmail", gmail);
        request.put("password", password);

        ServerHandler.getInstance().setListener((JSONObject msg) -> {
            String type = msg.getString("type");
            if (type.equals("signup")) {
                String status = msg.getString("status");
                if (status.equals("success")) {
                    javafx.application.Platform.runLater(() -> {
                        showSimpleMessage("Registration successful! Welcome " + name);
                        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
                        delay.setOnFinished(e -> {
                            NavigateBetweeenScreens.goToAvailablePlayer(event);
                        });
                        delay.play();
                    });
                } else {
                    showSimpleMessage("This Email is already registered! ");
                }
            }
        });
        ServerHandler.getInstance().send(request);
    }

    private void showSimpleMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);

        alert.setHeaderText(null);
        alert.setTitle(null);

        alert.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
        javafx.scene.Node okButton = alert.getDialogPane().lookupButton(javafx.scene.control.ButtonType.OK);
        okButton.setVisible(false);
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(2),
                ae -> alert.close()
        ));
        timeline.play();

        alert.show();
    }

    @FXML
    private void OnActionPassword(ActionEvent event) {
    }

}
