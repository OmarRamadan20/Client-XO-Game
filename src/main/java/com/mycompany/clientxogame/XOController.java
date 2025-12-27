package com.mycompany.clientxogame;

import static com.mycompany.clientxogame.ServerHandler.getInstance;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
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

    @FXML
    private Text cell00, cell01, cell02;
    @FXML
    private Text cell10, cell11, cell12;
    @FXML
    private Text cell20, cell21, cell22;

    @FXML
    private Line winLine;
    @FXML
    private VBox endGameBox;

    private Text[][] cells;
    private String[][] board;
    private boolean xTurn = true;
    private boolean gameOver = false;
    private SingleMode ai = new SingleMode();
    private String difficulty;
    private String mySymbol;
    private boolean myTurn;
    private String opponentName;
    @FXML
    private Button idRecords;

    private List<Move> moves = new ArrayList<>();
    boolean isRecord = false;
    @FXML
    private Label Playerone;
    @FXML
    private Label playerOneScore;
    @FXML
    private Label Playertwo;
    @FXML
    private Label PlayerTwoScore;

    char operant = '+';

    int scoreX = 0;
        int scoreO = 0;
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };

        resetBoard();

        setupCells();

    }

    public void setOnlineMode(String opponent, String symbol, boolean turn) {
        this.opponentName = opponent;
        this.mySymbol = symbol;
        this.myTurn = turn;
        Playerone.setText(this.opponentName);
        Playertwo.setText(LoggedUser.name);

        System.out.println("Playing against: " + opponent + " | Symbol: " + symbol + " | My Turn: " + turn);

        ServerHandler.getInstance().setListener(json -> {
            if (json.optString("type").equals("player_move")) {
                String move = json.getString("move");
                int r = Integer.parseInt(move.split(",")[0]);
                int c = Integer.parseInt(move.split(",")[1]);

                Platform.runLater(() -> {
                    String opSymbol = mySymbol.equals("X") ? "O" : "X";
                    Color opColor = opSymbol.equals("X") ? Color.LIME : Color.HOTPINK;
                    makeMove(r, c, opSymbol, opColor);
                    this.myTurn = true;

                    int winStatus = checkWin();
                    if (winStatus != -1 || isBoardFull()) {
                        handleGameOver(winStatus);
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
                final int row = r;
                final int col = c;

                parent.setOnMouseClicked(e -> {
                    SoundManager.getInstance().playButton("playClick");
                    parent.setTranslateY(4);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> parent.setTranslateY(0)));
                    timeline.play();

                    if (gameOver || !cell.getText().isEmpty() || !myTurn) {
                        return;
                    }

                    String colorStr = mySymbol.equals("X") ? "LIME" : "HOTPINK";
                    makeMove(row, col, mySymbol, Color.valueOf(colorStr));

                    myTurn = false;

                    JSONObject moveRequest = new JSONObject();
                    moveRequest.put("type", "move");
                    moveRequest.put("to", NavigateBetweeenScreens.currentOpponent);
                    moveRequest.put("from", LoggedUser.name);
                    moveRequest.put("move", row + "," + col);

                    ServerHandler.getInstance().send(moveRequest);

                    int winStatus = checkWin();
                    if (winStatus != -1 || isBoardFull()) {
                        handleGameOver(winStatus);
                    }
                });
            }
        }
    }

    String winnerSymbol = " ";

    private void handleGameOver(int winCode) {
        gameOver = true;

        if (winCode != -1) {
            drawWinLine(winCode);

            winnerSymbol = "";
            if (winCode >= 0 && winCode <= 2) {
                winnerSymbol = board[winCode][0];
            } else if (winCode >= 3 && winCode <= 5) {
                int col = winCode - 3;
                winnerSymbol = board[0][col];
            } else if (winCode == 6) {
                winnerSymbol = board[0][0];
            } else if (winCode == 7) {
                winnerSymbol = board[0][2];
            }

            if (!myTurn) {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }

        } else {
            System.out.println("Draw");
            winnerSymbol = "";
        }

        endGameBox.setVisible(true);

        handleInsertGameResult();

        if (isRecord) {
            GameFileManager.save(moves, LoggedUser.name, opponentName);
        }
        
        updateScore();
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
    
    
    

    private void handleInsertGameResult() {
        JSONObject request = new JSONObject();
        request.put("type", "game_result");
        request.put("gmail1", LoggedUser.gmail);
        request.put("gmail2", Opponent.gmail);
        request.put("gmailWin", determineWinnerGmail());

        ServerHandler.getInstance().send(request);
    }

    private String determineWinnerGmail() {
        if (winnerSymbol.equals(mySymbol)) {
            return LoggedUser.gmail;
        } else if (!winnerSymbol.equals("")) {
            return Opponent.gmail;
        }
        return "";
    }

    private void handleGetScore(JSONObject response) {

        String type = response.getString("type");

        if (type.equals("getScore_response")) {

            String status = response.getString("status");

            if (status.equals("success")) {
                LoggedUser.score = response.getInt("score");
                System.out.println("Score = " + LoggedUser.score);
            } else {
                System.out.println("Error: " + response.getString("message"));
            }
        }
    }

    private void makeMove(int row, int col, String player, Color color) {
        cells[row][col].setText(player);
        cells[row][col].setFill(color);
        board[row][col] = player;
        int playerId = player.equals("X") ? 0 : 1;
        moves.add(new Move(playerId, row, col));
    }

    private int checkWin() {
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].equals("")
                    && board[r][0].equals(board[r][1])
                    && board[r][1].equals(board[r][2])) {
                return r;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].equals("")
                    && board[0][c].equals(board[1][c])
                    && board[1][c].equals(board[2][c])) {
                return c + 3;
            }
        }

        if (!board[0][0].equals("") && board[0][0].equals(board[1][1])
                && board[1][1].equals(board[2][2])) {
            return 6;
        }
        if (!board[0][2].equals("") && board[0][2].equals(board[1][1])
                && board[1][1].equals(board[2][0])) {
            return 7;
        }

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.equals("")) {
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
                setLineForRow(0);
                break;
            case 1:
                setLineForRow(1);
                break;
            case 2:
                setLineForRow(2);
                break;
            case 3:
                setLineForCol(0);
                break;
            case 4:
                setLineForCol(1);
                break;
            case 5:
                setLineForCol(2);
                break;
            case 6:
                setLineForDiag(true);
                break;
            case 7:
                setLineForDiag(false);
                break;
        }
    }

    private void setLineForRow(int row) {
        setLineBounds((StackPane) cells[row][0].getParent(), (StackPane) cells[row][2].getParent());
    }

    private void setLineForCol(int col) {
        setLineBounds((StackPane) cells[0][col].getParent(), (StackPane) cells[2][col].getParent());
    }

    private void setLineForDiag(boolean leftToRight) {
        if (leftToRight) {
            setLineBounds((StackPane) cells[0][0].getParent(), (StackPane) cells[2][2].getParent());
        } else {
            setLineBounds((StackPane) cells[0][2].getParent(), (StackPane) cells[2][0].getParent());
        }
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
        resetBoard();
        xTurn = true;
        gameOver = false;
        winLine.setVisible(false);
        endGameBox.setVisible(false);

        for (Text[] row : cells) {
            for (Text cell : row) {
                cell.setText("");
            }
        }
    }

    @FXML
    private void onBack() {
        SoundManager.getInstance().playButton("back");
        System.exit(0);
    }

    @FXML
    private void onActionRecode(ActionEvent event) {
                SoundManager.getInstance().playButton("enter");

        isRecord = true;
    }
    
    @FXML
    private void handleCellHover(MouseEvent event) {
        StackPane pane = (StackPane) event.getSource();
        pane.setScaleX(1.05);
        pane.setScaleY(1.05);
        ((Rectangle) pane.getChildren().get(0)).setFill(javafx.scene.paint.Color.web("#3d0158"));
    }

    @FXML
    private void handleCellExit(MouseEvent event) {
        StackPane pane = (StackPane) event.getSource();
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
        ((Rectangle) pane.getChildren().get(0)).setFill(javafx.scene.paint.Color.web("#2c003e"));
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
