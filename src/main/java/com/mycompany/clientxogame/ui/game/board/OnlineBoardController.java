package com.mycompany.clientxogame.ui.game.board;

import com.mycompany.clientxogame.ui.history.GameFileManager;
import com.mycompany.clientxogame.ui.history.Move;
import com.mycompany.clientxogame.navigation.NavigationManager;
import com.mycompany.clientxogame.model.Opponent;
import com.mycompany.clientxogame.network.ServerHandler;
import com.mycompany.clientxogame.sound.SoundManager;
import com.mycompany.clientxogame.model.LoggedUser;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.JSONObject;

public class OnlineBoardController implements Initializable {

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

    @FXML
    private Label Playerone;
    @FXML
    private Label Playertwo;
    @FXML
    private Label playerOneScore;
    @FXML
    private Label PlayerTwoScore;

    @FXML
    private Button idRecords;

    private Text[][] cells;
    private String[][] board;
    private boolean gameOver = false;

    private String mySymbol;
    private boolean myTurn;
    private String opponentName;
    private String winnerSymbol = "";

    private static int scoreX = 0;
    private static int scoreO = 0;

    private final List<Move> moves = new ArrayList<>();
    private boolean isRecord = false;
    @FXML
    private HBox recordingIndicator;
    @FXML
    private Circle redDot;

    private FadeTransition dotAnimation;
    @FXML
    private Button BackButtonId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };

        dotAnimation = new FadeTransition(Duration.seconds(0.5), redDot);
        dotAnimation.setFromValue(1.0);
        dotAnimation.setToValue(0.2);
        dotAnimation.setCycleCount(Timeline.INDEFINITE);
        dotAnimation.setAutoReverse(true);

        resetBoard();
        setupCells();
        endGameBox.setVisible(false);
    }
    
    
    public void setPlayerOneName(String name) {
    Platform.runLater(() -> Playerone.setText(name));
}

public void setPlayerTwoName(String name) {
    Platform.runLater(() -> Playertwo.setText(name));
}


public static void resetScores() {
    scoreX = 0;
    scoreO = 0;
    
}


    public void setScores(int scoreX, int scoreO) {
        this.scoreX = scoreX;
        this.scoreO = scoreO;
        playerOneScore.setText(String.valueOf(scoreX));
        PlayerTwoScore.setText(String.valueOf(scoreO));
    }

    public void setOnlineMode(String opponent, String symbol, boolean turn) {
       this.opponentName = opponent;
        this.mySymbol = symbol;
        this.myTurn = turn;

      Playerone.setText(LoggedUser.name);
Playertwo.setText(opponentName);


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

                    if (gameOver || !cell.getText().isEmpty() || !myTurn) {
                        return;
                    }

                    SoundManager.getInstance().playButton("playClick");
                    parent.setTranslateY(4);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> parent.setTranslateY(0)));
                    timeline.play();

                    makeMove(row, col, mySymbol,
                            mySymbol.equals("X") ? Color.LIME : Color.HOTPINK);

                    myTurn = false;

                    JSONObject req = new JSONObject();
                    req.put("type", "move");
                    req.put("from", LoggedUser.name);
                    req.put("to", NavigationManager.currentOpponent);
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
        dotAnimation.stop();
        recordingIndicator.setVisible(false);

        if (winCode != -1) {
            setLineBounds(winCode);

            if (winCode <= 2) {
                winnerSymbol = board[winCode][0];
            } else if (winCode <= 5) {
                winnerSymbol = board[0][winCode - 3];
            } else if (winCode == 6) {
                winnerSymbol = board[0][0];
            } else {
                winnerSymbol = board[0][2];
            }

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
                    NavigationManager.drawGame();
                } else if (winnerSymbol.equals(mySymbol)) {
                    NavigationManager.winGame();
                } else {
                    NavigationManager.loseGame();
                }

            });
            pause.play();
        });

        updateScore();
    }

    private void updateScore() {
    if (!winnerSymbol.isEmpty()) {
        boolean iAmX = "X".equals(mySymbol);
        boolean winnerIsX = "X".equals(winnerSymbol);

        if ((iAmX && winnerIsX) || (!iAmX && !winnerIsX)) {
            scoreX++;
            playerOneScore.setText(String.valueOf(scoreX));
        } else {
            scoreO++;
            PlayerTwoScore.setText(String.valueOf(scoreO));
        }

        NavigationManager.lastScoreX = scoreX;
        NavigationManager.lastScoreO = scoreO;
    }
}


    private void handleInsertGameResult() {
        JSONObject req = new JSONObject();
        req.put("type", "game_result");
        req.put("gmail1", LoggedUser.gmail);
        req.put("gmail2", Opponent.gmail);
        req.put("gmailWin", winnerSymbol.isEmpty() ? "draw" : winnerSymbol.equals(mySymbol) ? LoggedUser.gmail : Opponent.gmail);
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
            if (!board[i][0].isEmpty()
                    && board[i][0].equals(board[i][1])
                    && board[i][1].equals(board[i][2])) {
                return i;
            }

            if (!board[0][i].isEmpty()
                    && board[0][i].equals(board[1][i])
                    && board[1][i].equals(board[2][i])) {
                return i + 3;
            }
        }

        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1])
                && board[1][1].equals(board[2][2])) {
            return 6;
        }

        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1])
                && board[1][1].equals(board[2][0])) {
            return 7;
        }

        return -1;
    }

    private boolean isBoardFull() {
        for (String[] r : board) {
            for (String c : r) {
                if (c.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setLineBounds(int code) {
        StackPane startPane = null;
        StackPane endPane = null;

        switch (code) {
            case 0:
                startPane = (StackPane) cells[0][0].getParent();
                endPane = (StackPane) cells[0][2].getParent();
                break;
            case 1:
                startPane = (StackPane) cells[1][0].getParent();
                endPane = (StackPane) cells[1][2].getParent();
                break;
            case 2:
                startPane = (StackPane) cells[2][0].getParent();
                endPane = (StackPane) cells[2][2].getParent();
                break;
            case 3:
                startPane = (StackPane) cells[0][0].getParent();
                endPane = (StackPane) cells[2][0].getParent();
                break;
            case 4:
                startPane = (StackPane) cells[0][1].getParent();
                endPane = (StackPane) cells[2][1].getParent();
                break;
            case 5:
                startPane = (StackPane) cells[0][2].getParent();
                endPane = (StackPane) cells[2][2].getParent();
                break;
            case 6:
                startPane = (StackPane) cells[0][0].getParent();
                endPane = (StackPane) cells[2][2].getParent();
                break;
            case 7:
                startPane = (StackPane) cells[0][2].getParent();
                endPane = (StackPane) cells[2][0].getParent();
                break;
        }

        if (startPane != null && endPane != null) {
            final StackPane finalStart = startPane;
            final StackPane finalEnd = endPane;
            Pane lineContainer = (Pane) winLine.getParent();

            Platform.runLater(() -> {
                Bounds startBounds = finalStart.localToScene(finalStart.getBoundsInLocal());
                Point2D startPt = lineContainer.sceneToLocal(startBounds.getMinX() + startBounds.getWidth() / 2,
                        startBounds.getMinY() + startBounds.getHeight() / 2);

                Bounds endBounds = finalEnd.localToScene(finalEnd.getBoundsInLocal());
                Point2D endPt = lineContainer.sceneToLocal(endBounds.getMinX() + endBounds.getWidth() / 2,
                        endBounds.getMinY() + endBounds.getHeight() / 2);

                winLine.setStartX(startPt.getX());
                winLine.setStartY(startPt.getY());
                winLine.setEndX(startPt.getX());
                winLine.setEndY(startPt.getY());
                winLine.setVisible(true);

                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(600),
                        new KeyValue(winLine.endXProperty(), endPt.getX()),
                        new KeyValue(winLine.endYProperty(), endPt.getY())
                ));
                timeline.play();
            });
        }
    }

    @FXML
    private void onActionRecode(ActionEvent event) {
        SoundManager.getInstance().playButton("enter");

        isRecord = true;
        recordingIndicator.setVisible(true);
        dotAnimation.play();
        idRecords.setDisable(true);
        idRecords.setOpacity(0.5);
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
