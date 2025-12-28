/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame.ui.history;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.sound.SoundManager;
import com.mycompany.clientxogame.model.LoggedUser;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GameRecordsController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private Label nameTxt;
    @FXML
    private Button btnplayOn;
    @FXML
    private ListView<String> ListFile;
    private ObservableList<String> filesListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filesListData.addAll(GameFileManager.getPlayerGames(LoggedUser.name));
        ListFile.setItems(filesListData);

        ListFile.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateUI(newVal);
            }
        });
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        SoundManager.getInstance().playButton("back");
        NavigationManager.backToShowProfile(event);
    }

    @FXML
    private void playOn(ActionEvent event) {
        String selectedFile = ListFile.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            List<Move> moves = GameFileManager.load(selectedFile);

            ObservableList<String> movesData = FXCollections.observableArrayList();
            for (Move m : moves) {
                String player = m.playerId == 0 ? "X" : "O";
                movesData.add(player + " -> Row: " + m.row + ", Col: " + m.col);
            }
            SoundManager.getInstance().playButton("enter");

            ListFile.setItems(movesData);

            nameTxt.setText(selectedFile);
            SoundManager.getInstance().playButton("enter");

            NavigationManager.goToPlayRecords(event, selectedFile);
        }
    }

    private void updateUI(String nameFile) {
        nameTxt.setText(nameFile);
    }
}
