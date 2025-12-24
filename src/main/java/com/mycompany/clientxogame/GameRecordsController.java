/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
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

    @FXML private Button backBtn;
    @FXML private Label nameTxt;
    @FXML private Button btnplayOn;
    @FXML
    private ListView<String> ListFile;
     private ObservableList<String> filesListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filesListData.addAll(GameFileManager.getAllGames());
        ListFile.setItems(filesListData);

        ListFile.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateUI(newVal);
            }
        });
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        NavigateBetweeenScreens.backToShowProfile(event);
    }

    @FXML
    private void playOn(ActionEvent event) {
        String selectedFile = ListFile.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
             NavigateBetweeenScreens.goToPlayRecords(event,selectedFile);
        }
    }

    private void updateUI(String nameFile) {
        nameTxt.setText(nameFile);
    }
}
