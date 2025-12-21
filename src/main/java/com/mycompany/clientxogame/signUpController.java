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
    private TextField enterPassword;
    @FXML
    private TextField confirmPass;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSignUp;

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

         NavigateBetweeenScreens.goToLogIn(event);

        String name = enterName.getText();
        String gmail = enterEmail.getText();
        String password = enterPassword.getText();
        String confirmPassword = confirmPass.getText();
        if (name.isEmpty() || gmail.isEmpty() || password.isEmpty()) {
            System.out.println("Please Fill All Fields");
            return;
        }
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return;
        }
        JSONObject request = new JSONObject();
        request.put("type", "signup");
        request.put("name", name);
        request.put("gmail", gmail);
        request.put("password", password);
        ServerHandler.getInstance().setListener(new ServerListener() {
            @Override
            public void onMessage(JSONObject msg) {
                String type = msg.getString("type");
                if (type.equals("signup")) {
                    String status = msg.getString("status");
                    if (status.equals("success")) {
                        System.out.println("Sign Up Is Sucessfull !");
                        NavigateBetweeenScreens.goToAvailablePlayer(event);
                    } else {
                        System.out.println("Sign Up Is Failes !");
                    }
                }
            }

        });
        ServerHandler.getInstance().send(request);


    }

}
