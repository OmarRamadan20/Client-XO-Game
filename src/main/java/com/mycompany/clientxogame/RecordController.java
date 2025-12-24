/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

 
 

public class RecordController implements Initializable {

    @FXML private Text cell00;
    @FXML private Text cell01;
    @FXML private Text cell02;
    @FXML private Text cell10;
    @FXML private Text cell11;
    @FXML private Text cell12;
    @FXML private Text cell20;
    @FXML private Text cell21;
    @FXML private Text cell22;

    private List<Move> moves;        
    private int currentMoveIndex = 0;  

    private String selectedFile; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }    

     public void setFile(String fileName) {
        this.selectedFile = fileName;
        moves = GameFileManager.load(fileName);
        currentMoveIndex = 0;
        clearBoard();
    }

     private void clearBoard() {
        cell00.setText("");
        cell01.setText("");
        cell02.setText("");
        cell10.setText("");
        cell11.setText("");
        cell12.setText("");
        cell20.setText("");
        cell21.setText("");
        cell22.setText("");
    }

     private void applyMove(Move move) {
        Text cell = getCell(move.row, move.col);
        if(cell != null) {
            cell.setText(move.playerId == 1 ? "X" : "O");
        }
    }

     private Text getCell(int row, int col) {
        switch(row) {
            case 0: switch(col) { case 0: return cell00; case 1: return cell01; case 2: return cell02; }
            case 1: switch(col) { case 0: return cell10; case 1: return cell11; case 2: return cell12; }
            case 2: switch(col) { case 0: return cell20; case 1: return cell21; case 2: return cell22; }
        }
        return null;
    }

     @FXML
    private void onStart(ActionEvent event) {
        if(moves != null && currentMoveIndex < moves.size()) {
            applyMove(moves.get(currentMoveIndex));
            currentMoveIndex++;
        }
    }

     @FXML
    private void onBack(ActionEvent event) {
        NavigateBetweeenScreens.backToGameRecords(event);
    }

}
