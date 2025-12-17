package com.mycompany.clientxogame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class XOController implements Initializable {

    @FXML private Text cell00, cell01, cell02;
    @FXML private Text cell10, cell11, cell12;
    @FXML private Text cell20, cell21, cell22;

    @FXML private Line winLine;
    @FXML private Button playAgainBtn;
    @FXML private Button backBtn;
    @FXML private VBox endGameBox;

    private Text[][] cells;
    private String[][] board = new String[3][3];
    private boolean xTurn = true;
    private boolean gameOver = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };
        setupCells();
    }

    private void setupCells() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Text cell = cells[r][c];
                StackPane parent = (StackPane) cell.getParent();

                int row = r;
                int col = c;

                parent.setOnMouseClicked(e -> {
                    if (gameOver || !cell.getText().isEmpty()) return;

                    if (xTurn) {
                        cell.setText("X");
                        cell.setFill(Color.LIME);
                        board[row][col] = "X";
                    } else {
                        cell.setText("O");
                        cell.setFill(Color.HOTPINK);
                        board[row][col] = "O";
                    }

                    xTurn = !xTurn;

                    int win = checkWin();
                    if (win != -1) {
                        drawWinLine(win);
                        gameOver = true;
                        endGameBox.setVisible(true);
                    } else if (isBoardFull()) {
                        gameOver = true;
                        endGameBox.setVisible(true);
                    }
                });
            }
        }
    }

    private int checkWin() {
        
        for (int r = 0; r < 3; r++) {
            if (board[r][0] != null &&
                board[r][0].equals(board[r][1]) &&
                board[r][1].equals(board[r][2])) return r; 
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c] != null &&
                board[0][c].equals(board[1][c]) &&
                board[1][c].equals(board[2][c])) return c + 3; 
        }

        if (board[0][0] != null &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2])) return 6;
        if (board[0][2] != null &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0])) return 7;

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] row : board)
            for (String cell : row)
                if (cell == null) return false;
        return true;
    }

    private void drawWinLine(int code) {
        winLine.setVisible(true);

        switch (code) {

            case 0: setLineForRow(0); break;
            case 1: setLineForRow(1); break;
            case 2: setLineForRow(2); break;

            case 3: setLineForCol(0); break;
            case 4: setLineForCol(1); break;
            case 5: setLineForCol(2); break;

            case 6: setLineForDiag(true); break;
            case 7: setLineForDiag(false); break;
        }
    }

    private void setLineForRow(int row) {
        StackPane start = (StackPane) cells[row][0].getParent();
        StackPane end = (StackPane) cells[row][2].getParent();
        setLineBounds(start, end);
    }

    private void setLineForCol(int col) {
        StackPane start = (StackPane) cells[0][col].getParent();
        StackPane end = (StackPane) cells[2][col].getParent();
        setLineBounds(start, end);
    }

    private void setLineForDiag(boolean leftToRight) {
        if (leftToRight)
            setLineBounds((StackPane) cells[0][0].getParent(), (StackPane) cells[2][2].getParent());
        else
            setLineBounds((StackPane) cells[0][2].getParent(), (StackPane) cells[2][0].getParent());
    }

    private void setLineBounds(StackPane start, StackPane end) {

        Bounds s = start.localToParent(start.getLayoutBounds());
        Bounds e = end.localToParent(end.getLayoutBounds());

        winLine.setStartX(s.getMinX() + s.getWidth() / 2);
        winLine.setStartY(s.getMinY() + s.getHeight() / 2);
        winLine.setEndX(e.getMinX() + e.getWidth() / 2);
        winLine.setEndY(e.getMinY() + e.getHeight() / 2);
    }

    @FXML
    private void onPlayAgain() {
        board = new String[3][3];
        xTurn = true;
        gameOver = false;
        winLine.setVisible(false);
        endGameBox.setVisible(false);

        for (Text[] row : cells)
            for (Text cell : row)
                cell.setText("");
    }

    @FXML
    private void onBack() {
        System.exit(0);
    }
}
