package com.mycompany.clientxogame.ui.auth;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.network.ServerHandler;
import com.mycompany.clientxogame.network.ServerListener;
import com.mycompany.clientxogame.sound.SoundManager;
import com.mycompany.clientxogame.model.LoggedUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
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

        NavigationManager.lastEvent = event;

        String gmail = enterEmail.getText();
        String password = enterPassword.getText();

        JSONObject request = new JSONObject();
        request.put("type", "login");
        request.put("gmail", gmail);
        request.put("password", password);
//------commint function  isPlayerAlreadyLoggedIn ==========================
        ServerHandler.getInstance().send(request);
    }

    @Override
    public void onMessage(JSONObject response) {

        if (!response.getString("type").equals("login")) {
            return;
        }

        if (response.getString("status").equals("success")) {

            LoggedUser.name = response.getString("name");
            LoggedUser.gmail = response.getString("gmail");
            LoggedUser.score = response.getInt("score");

            NavigationManager.goToAvailablePlayer(NavigationManager.lastEvent
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

        SoundManager.getInstance().playButton("enter");
        NavigationManager.goToRegister(event);
    }

    @FXML
    private void actionBtn(ActionEvent event) {

        SoundManager.getInstance().playButton("back");
        NavigationManager.backToModeSelection(event);
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
