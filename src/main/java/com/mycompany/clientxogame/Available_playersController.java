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

    public Player() {
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "  -  Score: " + score;
    }
}

public class Available_playersController implements Initializable {

    @FXML
    private Label nameTxt;
    @FXML
    private Label scoreTxt;
    @FXML
    private ListView<Player> playersList;
    @FXML
    private Button btnInvite;

    private ObservableList<Player> players = FXCollections.observableArrayList();
    private ServerHandler server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = ServerHandler.getInstance();

        playersList.setItems(players);

        server.setListener(json -> {
            System.out.println("Received JSON from server: " + json);
            String type = json.optString("type", "");

            Platform.runLater(() -> {
                switch (type) {
                    case "getAvailablePlayers":
                        handleAvailablePlayers(json);
                        break;
                    case "logout_response":
                        handleLogoutResponse(json);
                        break;
                    case "invite_recieved":
                        String from = json.getString("from");
                        
                        System.out.print(from);
                        NavigateBetweeenScreens.invite(NavigateBetweeenScreens.lastEvent, from);
                        break;
                    case "player_move":
                        String move = json.getString("move");
                        System.out.println("player_move moved: " + move);

                        break;
                }
            });
        });

        playersList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateUI(newVal);
            }
        });

        requestPlayersFromServer();
    }

    private void requestPlayersFromServer() {
        JSONObject request = new JSONObject();
        request.put("type", "getAvailablePlayers");
        System.out.println("Sending request to server: " + request);
        server.send(request);
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

    @FXML
    private void invitePlayer(ActionEvent event) {
        Player selected = playersList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Invite sent to: " + selected.getName());
        }
        JSONObject request = new JSONObject();
        request.put("type", "invite");
        request.put("to", selected.getName());
        request.put("from", LoggedUser.name);
        server.send(request);

    }

    @FXML
    private void refreshPlayers(ActionEvent event) {
        requestPlayersFromServer();
    }

    @FXML
    private void logOut(ActionEvent event) {
        JSONObject request = new JSONObject();
        request.put("type", "logout");
        request.put("gmail", LoggedUser.gmail);

        NavigateBetweeenScreens.lastEvent = event;

        server.send(request);
    }

    private void handleLogoutResponse(JSONObject response) {
        if (response.optString("status").equals("success")) {

            LoggedUser.name = null;
            LoggedUser.gmail = null;
            LoggedUser.score = 0;

            NavigateBetweeenScreens.goToLogIn(NavigateBetweeenScreens.lastEvent);
        } else {
            System.out.println("Logout failed");
        }
    }

    @FXML
    private void showProfile(ActionEvent event) {

        NavigateBetweeenScreens.goToShowProfile(event);

    }
}
