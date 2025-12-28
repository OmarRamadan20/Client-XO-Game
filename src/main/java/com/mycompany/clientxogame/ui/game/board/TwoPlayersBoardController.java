/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientxogame.ui.game.board;

import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.sound.SoundManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import static javafx.application.Platform.runLater;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TwoPlayersBoardController implements Initializable {

    @FXML
    private Button BackButtonId;
    @FXML
    private Label playerOneScore;
    @FXML
    private Text cell00;
    @FXML
    private Text cell01;
    @FXML
    private Text cell02;
    @FXML
    private Text cell10;
    @FXML
    private Text cell11;
    @FXML
    private Text cell12;
    @FXML
    private Text cell20;
    @FXML
    private Text cell21;
    @FXML
    private Text cell22;
    @FXML
    private Label PlayerTwoScore;
    @FXML
    private Line winLine;
    @FXML
    private Label playerOneName;
    @FXML
    private Label playerTwoName;

    private Text[][] cells;
    private String[][] board = new String[3][3];
    private boolean xTurn = true;
    private boolean gameOver = false;
    private int scoreX = 0, scoreO = 0;
    private String player1;
    private String player2;

    public void setPlayersNames(String name1, String name2) {
        this.player1 = name1;
        this.player2 = name2;

        playerOneName.setText(name1);
        playerTwoName.setText(name2);
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
                    SoundManager.getInstance().playButton("playClick");
                    parent.setTranslateY(4);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> parent.setTranslateY(0)));
                    timeline.play();

                    if (gameOver || board[row][col] != null) return;

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
                        drawWinLine(winCode);

                         String winnerSymbol = xTurn ? "X" : "O";
                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                            pause.setOnFinished(ev -> {
                                if (winnerSymbol.equals("X")) {
                                    NavigationManager.winGame();
                                } else {
                                    NavigationManager.loseGame();
                                }
                            });
                            pause.play();
                        });

                    } else if (isBoardFull()) {
                        gameOver = true;
                        System.out.println("Draw!");
                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                            pause.setOnFinished(ev -> NavigationManager.drawGame());
                            pause.play();
                        });
                    }

                    xTurn = !xTurn;
                });

                parent.setOnMouseEntered(this::handleCellHover);
                parent.setOnMouseExited(this::handleCellExit);
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
            if (board[r][0] != null && board[r][0].equals(board[r][1]) && board[r][1].equals(board[r][2])) {
                return r;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c] != null && board[0][c].equals(board[1][c]) && board[1][c].equals(board[2][c])) {
                return c + 3;
            }
        }

        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) return 6;
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) return 7;

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null) return false;
            }
        }
        return true;
    }

    private void drawWinLine(int code) {
        winLine.setVisible(true);
        switch (code) {
            case 0: setLineBounds(cells[0][0], cells[0][2]); break;
            case 1: setLineBounds(cells[1][0], cells[1][2]); break;
            case 2: setLineBounds(cells[2][0], cells[2][2]); break;
            case 3: setLineBounds(cells[0][0], cells[2][0]); break;
            case 4: setLineBounds(cells[0][1], cells[2][1]); break;
            case 5: setLineBounds(cells[0][2], cells[2][2]); break;
            case 6: setLineBounds(cells[0][0], cells[2][2]); break;
            case 7: setLineBounds(cells[0][2], cells[2][0]); break;
        }
    }

    private void setLineBounds(Text startCell, Text endCell) {
    StackPane startPane = (StackPane) startCell.getParent();
    StackPane endPane = (StackPane) endCell.getParent();
    Pane lineContainer = (Pane) winLine.getParent();

    Platform.runLater(() -> {
        Bounds startBounds = startPane.localToScene(startPane.getBoundsInLocal());
        Point2D startPoint = lineContainer.sceneToLocal(
                startBounds.getMinX() + startBounds.getWidth() / 2,
                startBounds.getMinY() + startBounds.getHeight() / 2
        );

        Bounds endBounds = endPane.localToScene(endPane.getBoundsInLocal());
        Point2D endPoint = lineContainer.sceneToLocal(
                endBounds.getMinX() + endBounds.getWidth() / 2,
                endBounds.getMinY() + endBounds.getHeight() / 2
        );

        winLine.setStartX(startPoint.getX());
        winLine.setStartY(startPoint.getY());
        winLine.setEndX(endPoint.getX());
        winLine.setEndY(endPoint.getY());
        winLine.setVisible(true);
    });
}

    public void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = null;
                cells[r][c].setText("");
            }
        }
        xTurn = true;
        gameOver = false;
        winLine.setVisible(false);
    }

    @FXML
    private void onBack(ActionEvent event) {
        SoundManager.getInstance().playButton("back");
        NavigationManager.gotoModeSelection(event);
        System.out.println("Back button clicked!");
    }

    @FXML
    private void handleCellHover(MouseEvent event) {
        StackPane pane = (StackPane) event.getSource();
        pane.setScaleX(1.05);
        pane.setScaleY(1.05);
        ((Rectangle) pane.getChildren().get(0)).setFill(Color.web("#3d0158"));
    }

    @FXML
    private void handleCellExit(MouseEvent event) {
        StackPane pane = (StackPane) event.getSource();
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
        ((Rectangle) pane.getChildren().get(0)).setFill(Color.web("#2c003e"));
    }

    @FXML
    private void handleCellClick(MouseEvent event) {
        StackPane pane = (StackPane) event.getSource();
        pane.setTranslateY(4);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> pane.setTranslateY(0)));
        timeline.play();
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        Button btn = (Button) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
        st.setToX(1.07);
        st.setToY(1.07);
        st.play();
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setTranslateY(4);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setTranslateY(0);
    }
}
