package com.mycompany.clientxogame.ui.history;

import com.mycompany.clientxogame.navigation.NavigationManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class RecordController implements Initializable {

    @FXML private Text cell00, cell01, cell02;
    @FXML private Text cell10, cell11, cell12;
    @FXML private Text cell20, cell21, cell22;

    @FXML private Button playBtn;
    @FXML private Button backBtn;
    @FXML private HBox replayStatusBox;
    @FXML private Circle recordingDot;

    private Text[][] cells;
    private List<Move> moves;
    private int currentMoveIndex = 0;
    private Timeline timeline;
    private String selectedFile;
    private FadeTransition dotAnimation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };

        dotAnimation = new FadeTransition(Duration.seconds(0.5), recordingDot);
        dotAnimation.setFromValue(1.0);
        dotAnimation.setToValue(0.2);
        dotAnimation.setCycleCount(Timeline.INDEFINITE);
        dotAnimation.setAutoReverse(true);
    }

    public void setFile(String fileName) {
        this.selectedFile = fileName;
        moves = GameFileManager.load(fileName);
        resetReplay();
    }

    @FXML
    private void onStart(ActionEvent event) {
        resetReplay();

        if (moves == null || moves.isEmpty()) {
            System.out.println("No moves found in file.");
            return;
        }

        replayStatusBox.setOpacity(1.0);
        dotAnimation.play();
        System.out.println("Replay Started...");

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                if (currentMoveIndex < moves.size()) {
                    applyMove(moves.get(currentMoveIndex));
                    currentMoveIndex++;
                } else {
                    stopReplayUI();
                }
            })
        );
        timeline.setCycleCount(moves.size());
        timeline.play();
    }

    private void applyMove(Move move) {
        Text cell = cells[move.row][move.col];
        if (cell != null) {
            if (move.playerId == 0) {
                cell.setText("X");
                cell.setFill(Color.LIME);
            } else {
                cell.setText("O");
                cell.setFill(Color.HOTPINK);
            }
        }
    }

    private void resetReplay() {
        if (timeline != null) timeline.stop();
        currentMoveIndex = 0;
        clearBoard();
        replayStatusBox.setOpacity(0.0);
        dotAnimation.stop();
    }

    private void stopReplayUI() {
        if (timeline != null) timeline.stop();
        replayStatusBox.setOpacity(0.0);
        dotAnimation.stop();
        System.out.println("Replay Finished.");
    }

    private void clearBoard() {
        for (Text[] row : cells) {
            for (Text cell : row) {
                cell.setText("");
            }
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        resetReplay();
        NavigationManager.backToGameRecords(event);
    }


    @FXML
    private void handleMouseEnter(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.08);
        btn.setScaleY(1.08);
        btn.setOpacity(0.9);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
        btn.setOpacity(1.0);
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(4);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        ((Button) event.getSource()).setTranslateY(0);
    }
}