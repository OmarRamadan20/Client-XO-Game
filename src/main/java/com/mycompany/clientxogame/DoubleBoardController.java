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
import javafx.geometry.Bounds;
import javafx.geometry.Point2D; 
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Aladawy
 */
public class DoubleBoardController implements Initializable {

    @FXML
    private Button BackButtonId;
    @FXML private Label playerOneScore, PlayerTwoScore;
    @FXML private Text cell00, cell01, cell02, cell10, cell11, cell12, cell20, cell21, cell22;
    @FXML private Line winLine;

    private Text[][] cells;
    private String[][] board = new String[3][3];
    private boolean xTurn = true;
    private boolean gameOver = false;
     private int scoreX = 0, scoreO = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };
       // setupCells();    
       // resetGame(); 
    }

    
}
