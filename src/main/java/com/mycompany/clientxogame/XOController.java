package com.mycompany.clientxogame; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.JSONObject;

public class XOController implements Initializable {

     @FXML private Text cell00, cell01, cell02;
    @FXML private Text cell10, cell11, cell12;
    @FXML private Text cell20, cell21, cell22;

    @FXML private Line winLine;
    @FXML private VBox endGameBox;

    @FXML private Label Playerone;
    @FXML private Label Playertwo;
    @FXML private Label playerOneScore;
    @FXML private Label PlayerTwoScore;

    @FXML private Button idRecords;

     private Text[][] cells;
    private String[][] board;
    private boolean gameOver = false;

    private String mySymbol;
    private boolean myTurn;
    private String opponentName;
    private String winnerSymbol = "";

    private int scoreX = 0;
    private int scoreO = 0;

    private final List<Move> moves = new ArrayList<>();
    private boolean isRecord = false;

     @Override
    public void initialize(URL url, ResourceBundle rb) {

        cells = new Text[][]{
                {cell00, cell01, cell02},
                {cell10, cell11, cell12},
                {cell20, cell21, cell22}
        };

        resetBoard();
        setupCells();
        endGameBox.setVisible(false);
    }

     public void setOnlineMode(String opponent, String symbol, boolean turn) {
        this.opponentName = opponent;
        this.mySymbol = symbol;
        this.myTurn = turn;

        Playerone.setText(opponentName);
        Playertwo.setText(LoggedUser.name);

        ServerHandler.getInstance().setListener(json -> {
            if ("player_move".equals(json.optString("type"))) {

                String[] rc = json.getString("move").split(",");
                int r = Integer.parseInt(rc[0]);
                int c = Integer.parseInt(rc[1]);

                Platform.runLater(() -> {
                    String opSymbol = mySymbol.equals("X") ? "O" : "X";
                    Color opColor = opSymbol.equals("X") ? Color.LIME : Color.HOTPINK;

                    makeMove(r, c, opSymbol, opColor);
                    myTurn = true;

                    int winCode = checkWin();
                    if (winCode != -1 || isBoardFull()) {
                        handleGameOver(winCode);
                    }
                });
            }
        });
    }

     private void resetBoard() {
        board = new String[][]{
                {"", "", ""},
                {"", "", ""},
                {"", "", ""}
        };
    }

    private void setupCells() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                Text cell = cells[r][c];
                StackPane parent = (StackPane) cell.getParent();
                int row = r, col = c;

                parent.setOnMouseClicked(e -> {

                    if (gameOver || !cell.getText().isEmpty() || !myTurn) return;

                    SoundManager.getInstance().playButton("playClick");

                    makeMove(row, col, mySymbol,
                            mySymbol.equals("X") ? Color.LIME : Color.HOTPINK);

                    myTurn = false;

                    JSONObject req = new JSONObject();
                    req.put("type", "move");
                    req.put("from", LoggedUser.name);
                    req.put("to", NavigateBetweeenScreens.currentOpponent);
                    req.put("move", row + "," + col);
                    ServerHandler.getInstance().send(req);

                    int winCode = checkWin();
                    if (winCode != -1 || isBoardFull()) {
                        handleGameOver(winCode);
                    }
                });
            }
        }
    }

     private void handleGameOver(int winCode) {

        gameOver = true;

        if (winCode != -1) {
            drawWinLine(winCode);

            if (winCode <= 2) winnerSymbol = board[winCode][0];
            else if (winCode <= 5) winnerSymbol = board[0][winCode - 3];
            else if (winCode == 6) winnerSymbol = board[0][0];
            else winnerSymbol = board[0][2];

        } else {
            winnerSymbol = "";  
        }

        endGameBox.setVisible(true);

        handleInsertGameResult();

        if (isRecord) {
            GameFileManager.save(moves, LoggedUser.name, opponentName);
        }

        Platform.runLater(() -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> {

                if (winnerSymbol.isEmpty()) {
                  NavigateBetweeenScreens.drawGame();  
                } else if (winnerSymbol.equals(mySymbol)) {
                    NavigateBetweeenScreens.winGame();
                } else {
                    NavigateBetweeenScreens.loseGame();
                }

            });
            pause.play();
        });

        updateScore();
    }

    private void updateScore() {
       if(!winnerSymbol.isEmpty())
       {         
        if ("X".equals(winnerSymbol)) {
            scoreX++;
            playerOneScore.setText(String.valueOf(scoreX));
        } else if ("O".equals(winnerSymbol)) {
            scoreO++;
            PlayerTwoScore.setText(String.valueOf(scoreO));
        }
     
    }
    }

     private void handleInsertGameResult() {
        JSONObject req = new JSONObject();
        req.put("type", "game_result");
        req.put("gmail1", LoggedUser.gmail);
        req.put("gmail2", Opponent.gmail);
        req.put("gmailWin",winnerSymbol.isEmpty() ? "draw": winnerSymbol.equals(mySymbol) ? LoggedUser.gmail : Opponent.gmail);
        ServerHandler.getInstance().send(req);
    }

     private void makeMove(int row, int col, String symbol, Color color) {
        cells[row][col].setText(symbol);
        cells[row][col].setFill(color);
        board[row][col] = symbol;
        moves.add(new Move(symbol.equals("X") ? 0 : 1, row, col));
    }

     private int checkWin() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2])) return i;

            if (!board[0][i].isEmpty() &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i])) return i + 3;
        }

        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2])) return 6;

        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0])) return 7;

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] r : board)
            for (String c : r)
                if (c.isEmpty()) return false;
        return true;
    }

    private void drawWinLine(int code) {
    winLine.setVisible(true);

    StackPane start = null;
    StackPane end = null;

    switch (code) {
        case 0:
            start = (StackPane) cells[0][0].getParent();
            end   = (StackPane) cells[0][2].getParent();
            break;

        case 1:
            start = (StackPane) cells[1][0].getParent();
            end   = (StackPane) cells[1][2].getParent();
            break;

        case 2:
            start = (StackPane) cells[2][0].getParent();
            end   = (StackPane) cells[2][2].getParent();
            break;

        case 3:
            start = (StackPane) cells[0][0].getParent();
            end   = (StackPane) cells[2][0].getParent();
            break;

        case 4:
            start = (StackPane) cells[0][1].getParent();
            end   = (StackPane) cells[2][1].getParent();
            break;

        case 5:
            start = (StackPane) cells[0][2].getParent();
            end   = (StackPane) cells[2][2].getParent();
            break;

        case 6:
            start = (StackPane) cells[0][0].getParent();
            end   = (StackPane) cells[2][2].getParent();
            break;

        case 7:
            start = (StackPane) cells[0][2].getParent();
            end   = (StackPane) cells[2][0].getParent();
            break;
    }

    if (start != null && end != null) {
        Bounds sb = start.localToParent(start.getLayoutBounds());
        Bounds eb = end.localToParent(end.getLayoutBounds());

        winLine.setStartX(sb.getMinX() + sb.getWidth() / 2);
        winLine.setStartY(sb.getMinY() + sb.getHeight() / 2);
        winLine.setEndX(eb.getMinX() + eb.getWidth() / 2);
        winLine.setEndY(eb.getMinY() + eb.getHeight() / 2);
    }
}

     @FXML
    private void onActionRecode(ActionEvent event) {
        SoundManager.getInstance().playButton("enter");
        isRecord = true;
    }
}
