package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import org.json.JSONObject;

public class LoginController implements Initializable, ServerListener {

   

    @FXML

    private TextField enterPassword;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Button btnBack;
    @FXML
    private Button registerID;
    @FXML
    private TextField enterEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerHandler.getInstance().setListener(this);
    }

    @FXML
    private void logIn(ActionEvent event) {

        NavigateBetweeenScreens.lastEvent = event;

        String gmail = enterEmail.getText();
        String password = enterPassword.getText();

        JSONObject request = new JSONObject();
        request.put("type", "login");
        request.put("gmail", gmail);
        request.put("password", password);

        ServerHandler.getInstance().send(request);
    }

    @Override
    public void onMessage(JSONObject response) {

        if (!response.getString("type").equals("login"))
            return;

        if (response.getString("status").equals("success")) {

            LoggedUser.name = response.getString("name");
            LoggedUser.gmail = response.getString("gmail");
            LoggedUser.score = response.getInt("score");

            NavigateBetweeenScreens.goToAvailablePlayer(
                    NavigateBetweeenScreens.lastEvent
            );

        } else {
            showError(response.getString("message"));
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    
    @FXML
    private void register(ActionEvent event) {
        NavigateBetweeenScreens.goToRegister(event);
    }

    @FXML
    private void actionBtn(ActionEvent event) {
        NavigateBetweeenScreens.backToModeSelection(event);
    }
}


