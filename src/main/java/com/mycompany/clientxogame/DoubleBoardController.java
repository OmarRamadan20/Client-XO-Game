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
        setupCells();    
       // resetGame(); 
    }
    private void setupCells() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                StackPane parent = (StackPane) cells[r][c].getParent();
                final int row = r;
                final int col = c;
                parent.setOnMouseClicked(e -> {
                    if (gameOver || board[row][col] != null) {
                        return;
                    }

                    if (xTurn) {
                        cells[row][col].setText("X");
                        cells[row][col].setFill(Color.LIME);
                        board[row][col] = "X";
                    } else {
                        cells[row][col].setText("O");
                        cells[row][col].setFill(Color.HOTPINK);
                        board[row][col] = "O";
                    }

                    int winCode = checkWin();
                    if (winCode != -1) {
                        gameOver = true;
                        updateScore(); 
                       // drawWinLine(winCode); 
                    } else if (isBoardFull()) {
                        gameOver = true;
                        System.out.println("Draw!");
                    }

                    xTurn = !xTurn; 
                });
            }
        }
    }

    
    private void updateScore() {
        
        if (xTurn) { 
            scoreX++;
            playerOneScore.setText(String.valueOf(scoreX));
        } else { 
            scoreO++;
            PlayerTwoScore.setText(String.valueOf(scoreO));
        }
    }

    private int checkWin() {
        for (int r = 0; r < 3; r++) {
            if (board[r][0] != null && board[r][0].equals(board[r][1]) && board[r][1].equals(board[r][2])) return r;
        }
        for (int c = 0; c < 3; c++) {
            if (board[0][c] != null && board[0][c].equals(board[1][c]) && board[1][c].equals(board[2][c])) return c + 3;
        }
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) return 6;
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) return 7;
        return -1;
    }

    private boolean isBoardFull() {
        for (String[] row : board)
            for (String cell : row)
                if (cell == null) return false;
        return true;
    }

   
   
}
