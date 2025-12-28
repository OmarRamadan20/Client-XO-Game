package com.mycompany.clientxogame.ui.history;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.network.ServerHandler;
import com.mycompany.clientxogame.model.LoggedUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameHistoryController implements Initializable {

    @FXML
    private ListView<JSONObject> ListFile;  
    @FXML
    private Button backBtn1;
    @FXML
    private Label nameTxt11;
    @FXML
    private Label idDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerHandler server = ServerHandler.getInstance();

         server.setListener(json -> {
            String type = json.getString("type");
            if (type.equals("playerHistory")) {
                Platform.runLater(() -> handlePlayerHistory(json));
            }
        });

         JSONObject request = new JSONObject();
        request.put("type", "playerHistory");
        request.put("gmail", LoggedUser.gmail);  
        server.send(request);

         ListFile.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(JSONObject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String gmail1 = item.optString("gmail1", "Unknown");
                    String gmail2 = item.optString("gmail2", "Unknown");

                    String name1 = gmail1.contains("@") ? gmail1.split("@")[0] : gmail1;
                    String name2 = gmail2.contains("@") ? gmail2.split("@")[0] : gmail2;

                    setText(name1 + " vs " + name2);
                }
            }
        });
    }

     private void handlePlayerHistory(JSONObject json) {
        JSONArray jsonGames = json.optJSONArray("games");
        ListFile.getItems().clear();

        if (jsonGames != null && jsonGames.length() > 0) {
            for (int i = 0; i < jsonGames.length(); i++) {
                JSONObject obj = jsonGames.getJSONObject(i);
                ListFile.getItems().add(obj);
            }

             if (!ListFile.getItems().isEmpty()) {
                ListFile.getSelectionModel().select(0);
                showGameDetails(ListFile.getItems().get(0));
            }

             ListFile.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    showGameDetails(newSel);
                }
            });
        } else {
            nameTxt11.setText("No game history found");
            idDate.setText("");
        }
    }

     private void showGameDetails(JSONObject game) {
        String winner = game.optString("gmailwinner", "Unknown");
        String winnerName = winner.contains("@") ? winner.split("@")[0] : winner;

        nameTxt11.setText(winnerName);
        idDate.setText(game.optString("date", ""));
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        NavigationManager.backToShowHistory(event);
    }
}
