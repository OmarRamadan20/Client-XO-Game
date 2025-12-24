package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class DoubleBoardController implements Initializable {

    @FXML
    private Button BackButtonId;

    @FXML
    private Label playerOneScore, PlayerTwoScore;

    @FXML
    private Text cell00, cell01, cell02,
            cell10, cell11, cell12,
            cell20, cell21, cell22;

    @FXML
    private Line winLine;

    private Text[][] cells;

    
    private String[][] board = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };

    private boolean xTurn = true;
    private boolean gameOver = false;
    private int scoreX = 0, scoreO = 0;
    private SingleMode ai = new SingleMode();
    private String difficulty = "Easy"; 
    

    public void setDifficulty(String difficulty) {
        if (difficulty != null && !difficulty.isEmpty()) {
            this.difficulty = difficulty;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new Text[][]{
                {cell00, cell01, cell02},
                {cell10, cell11, cell12},
                {cell20, cell21, cell22}
        };
        setupCells();
        resetGame();
    }

    private void setupCells() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                StackPane parent = (StackPane) cells[r][c].getParent();
                final int row = r;
                final int col = c;

                parent.setOnMouseClicked(e -> {
                    if (gameOver || !xTurn || !board[row][col].isEmpty()) {
                        return;
                    }

                    makePlayerMove(row, col);

                    
                    if (!gameOver) {
                        Platform.runLater(this::makeAIMove);
                    }
                });
            }
        }
    }

    private void makePlayerMove(int row, int col) {
        cells[row][col].setText("X");
        cells[row][col].setFill(Color.LIME);
        board[row][col] = "X";
       

        int winCode = checkWin();
        if (winCode != -1) {
            gameOver = true;
            scoreX++;
            playerOneScore.setText(String.valueOf(scoreX));
            drawWinLine(winCode);
          
        } else if (isBoardFull()) {
            gameOver = true;
           
        } else {
            xTurn = false; 
        }
    }

    private void makeAIMove() {
        
        if (difficulty == null || difficulty.isEmpty()) {
            difficulty = "Easy";
        }

        int[] move = ai.getMove(board, difficulty);
        int row = move[0];
        int col = move[1];

        if (board[row][col].isEmpty()) {
            cells[row][col].setText("O");
            cells[row][col].setFill(Color.HOTPINK);
            board[row][col] = "O";
           

            int winCode = checkWin();
            if (winCode != -1) {
                gameOver = true;
                scoreO++;
                PlayerTwoScore.setText(String.valueOf(scoreO));
                drawWinLine(winCode);
              
            } else if (isBoardFull()) {
                gameOver = true;
               
            } else {
                xTurn = true; 
            }
        }
    }

    private int checkWin() {
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].isEmpty()
                    && board[r][0].equals(board[r][1])
                    && board[r][1].equals(board[r][2])) {
                return r;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].isEmpty()
                    && board[0][c].equals(board[1][c])
                    && board[1][c].equals(board[2][c])) {
                return c + 3;
            }
        }

        if (!board[0][0].isEmpty()
                && board[0][0].equals(board[1][1])
                && board[1][1].equals(board[2][2])) {
            return 6;
        }

        if (!board[0][2].isEmpty()
                && board[0][2].equals(board[1][1])
                && board[1][1].equals(board[2][0])) {
            return 7;
        }

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void drawWinLine(int code) {
        winLine.setVisible(true);
       switch (code) {
    case 0:
        setLineBounds(cells[0][0], cells[0][2]);
        break;
    case 1:
        setLineBounds(cells[1][0], cells[1][2]);
        break;
    case 2:
        setLineBounds(cells[2][0], cells[2][2]);
        break;
    case 3:
        setLineBounds(cells[0][0], cells[2][0]);
        break;
    case 4:
        setLineBounds(cells[0][1], cells[2][1]);
        break;
    case 5:
        setLineBounds(cells[0][2], cells[2][2]);
        break;
    case 6:
        setLineBounds(cells[0][0], cells[2][2]);
        break;
    case 7:
        setLineBounds(cells[0][2], cells[2][0]);
        break;
}

    }

    private void setLineBounds(Text startCell, Text endCell) {
        Bounds startBounds = startCell.localToScene(startCell.getBoundsInLocal());
        Bounds endBounds = endCell.localToScene(endCell.getBoundsInLocal());

        Pane parent = (Pane) winLine.getParent();

        Point2D startPoint = parent.sceneToLocal(
                startBounds.getMinX() + startBounds.getWidth() / 2,
                startBounds.getMinY() + startBounds.getHeight() / 2
        );

        Point2D endPoint = parent.sceneToLocal(
                endBounds.getMinX() + endBounds.getWidth() / 2,
                endBounds.getMinY() + endBounds.getHeight() / 2
        );

        winLine.setStartX(startPoint.getX());
        winLine.setStartY(startPoint.getY());
        winLine.setEndX(endPoint.getX());
        winLine.setEndY(endPoint.getY());
    }

    @FXML
    public void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = "";
                cells[r][c].setText("");
            }
        }
        xTurn = true;
        gameOver = false;
        winLine.setVisible(false);
       
    }

    @FXML
    private void onBack(ActionEvent event) {
        NavigateBetweeenScreens.backToLevelSelection(event);
    }
}