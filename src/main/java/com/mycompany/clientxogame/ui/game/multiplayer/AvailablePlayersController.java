package com.mycompany.clientxogame.ui.game.multiplayer;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.model.Opponent;
import com.mycompany.clientxogame.network.ServerHandler;
import com.mycompany.clientxogame.sound.SoundManager;
import com.mycompany.clientxogame.model.LoggedUser;
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
import javafx.scene.input.MouseEvent;

class Player {

    private String name;
    private int score;
    private String gmail;

    public Player() {
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Player(String name, int score, String gmail) {
        this.name = name;
        this.score = score;
        this.gmail = gmail;
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

public class AvailablePlayersController implements Initializable {

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
                        System.out.println("Invitation received from: " + from);

                        Platform.runLater(() -> {
                            NavigationManager.invite(null, from);
                        });
                        break;
                    case "player_move":
                        String move = json.getString("move");
                        System.out.println("player_move moved: " + move);

                        break;
                    case "invite_status_back":
                        String status = json.getString("status");
                        if ("accept".equals(status)) {
                           
                            NavigationManager.goToTwoPlayersMode(null, LoggedUser.name, Opponent.name);
                        } else {
                            
                            Platform.runLater(() -> {
                               
                            });
                        }
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

    private void handleAvailablePlayers(JSONObject json) {
        JSONArray jsonPlayers = json.optJSONArray("players");
        players.clear();

        if (jsonPlayers != null && jsonPlayers.length() > 0) {
            for (int i = 0; i < jsonPlayers.length(); i++) {
                JSONObject obj = jsonPlayers.getJSONObject(i);
                String name = obj.optString("name", "Unknown");
                String gmail = obj.optString("gmail");
                int score = obj.optInt("score", 0);

                if (name.equals(LoggedUser.name)) {
                    continue;
                }

                players.add(new Player(name, score, gmail));
            }

            if (!players.isEmpty()) {
                playersList.getSelectionModel().select(0);
                updateUI(players.get(0));
            } else {
                showNoPlayers();
            }

        } else {
            showNoPlayers();
        }
    }

    private void showNoPlayers() {
        nameTxt.setText("No players online");
        scoreTxt.setText("");
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
        Player selected = playersList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            JSONObject request = new JSONObject();
            request.put("type", "invite");
            request.put("to", selected.getName());
            request.put("from", LoggedUser.name);
            SoundManager.getInstance().playButton("enter");

            NavigationManager.invitedFrom = selected.getName();
            Opponent.gmail = selected.getGmail();
            Opponent.name = selected.getName();
            Opponent.score = selected.getScore();
            server.send(request);
            NavigationManager.lastEvent = event;
            NavigationManager.goToWaitAccept(event);
        }
     }

    private void handleLogoutResponse(JSONObject response) {
        if ("success".equals(response.optString("status"))) {
            LoggedUser.name = null;
            LoggedUser.gmail = null;
            LoggedUser.score = 0;
            SoundManager.getInstance().playButton("back");


            NavigationManager.goToLogIn(NavigationManager.lastEvent);
        }
    }

    @FXML
    private void refreshPlayers(ActionEvent event) {
        SoundManager.getInstance().playButton("enter");

        requestPlayersFromServer();
    }

    @FXML
    private void showProfile(ActionEvent event) {

        SoundManager.getInstance().playButton("enter");

        NavigationManager.goToShowProfile(event);

    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(4);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(0);
    }

    @FXML
    private void handleMouseEnter(javafx.scene.input.MouseEvent event) {
        javafx.scene.control.Button btn = (javafx.scene.control.Button) event.getSource();
        btn.setScaleX(1.1);
        btn.setScaleY(1.1);
        btn.setOpacity(0.9);
    }

    @FXML
    private void handleMouseExit(javafx.scene.input.MouseEvent event) {
        javafx.scene.control.Button btn = (javafx.scene.control.Button) event.getSource();
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
        btn.setOpacity(1.0);
    }

    @FXML
    private void logOut(ActionEvent event) {
        NavigationManager.lastEvent = event;
        server.logout(LoggedUser.gmail);
    }
}
