package com.mycompany.clientxogame;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

class Player {
    private String name;
    private int score;

    public Player() {}
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return name + "  -  Score: " + score;
    }
}

public class Available_playersController implements Initializable {

    @FXML private Label nameTxt;
    @FXML private Label scoreTxt;
    @FXML private ListView<Player> playersList;
    @FXML private Button btnInvite;

    private ObservableList<Player> players = FXCollections.observableArrayList();
    private ServerHandler server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = ServerHandler.getInstance();
        playersList.setItems(players);

        
        server.setListener(json -> {
            String type = json.optString("type", "");
            if ("getAvailablePlayers".equals(type)) {
                Platform.runLater(() -> handleAvailablePlayers(json));
            } else if ("logout_response".equals(type)) {
                Platform.runLater(() -> handleLogoutResponse(json));
            }
        });

        playersList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateUI(newVal);
        });

        requestPlayersFromServer();
    }

    private void handleAvailablePlayers(JSONObject json) {
        JSONArray jsonPlayers = json.optJSONArray("players");
        players.clear();

        if (jsonPlayers != null && jsonPlayers.length() > 0) {
            for (int i = 0; i < jsonPlayers.length(); i++) {
                JSONObject obj = jsonPlayers.getJSONObject(i);
                String name = obj.optString("name", "Unknown");
                int score = obj.optInt("score", 0);
                players.add(new Player(name, score));
            }

            playersList.getSelectionModel().select(0);
            updateUI(players.get(0));
        } else {
            nameTxt.setText("No players online");
            scoreTxt.setText("");
        }
    }

    private void updateUI(Player player) {
        nameTxt.setText(player.getName());
        scoreTxt.setText(String.valueOf(player.getScore()));
    }

    private void requestPlayersFromServer() {
        JSONObject request = new JSONObject();
        request.put("type", "getAvailablePlayers");
        server.send(request);
    }

    @FXML
    private void invitePlayer(ActionEvent event) {
        // كود الدعوة للعب
    }

    @FXML
    private void refreshPlayers(ActionEvent event) {
        requestPlayersFromServer();
    }

    @FXML
    private void showProfile(ActionEvent event) {
        NavigateBetweeenScreens.goToShowProfile(event);
    }

    @FXML
    private void logOut(ActionEvent event) {
        NavigateBetweeenScreens.lastEvent = event;
        server.logout(LoggedUser.gmail);
    }

    private void handleLogoutResponse(JSONObject response) {
        if ("success".equals(response.optString("status"))) {
            LoggedUser.name = null;
            LoggedUser.gmail = null;
            LoggedUser.score = 0;

            NavigateBetweeenScreens.goToLogIn(NavigateBetweeenScreens.lastEvent);
        }
    }
}