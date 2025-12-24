package com.mycompany.clientxogame;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class RecordController implements Initializable {

    @FXML private Text cell00, cell01, cell02;
    @FXML private Text cell10, cell11, cell12;
    @FXML private Text cell20, cell21, cell22;

    private Text[][] cells;
    private List<Move> moves;
    private int currentMoveIndex = 0;
    private Timeline timeline;

    private String selectedFile;

    public void setFile(String fileName) {
        this.selectedFile = fileName;
        moves = GameFileManager.load(fileName);
        currentMoveIndex = 0;
        clearBoard();

        if (timeline != null) {
            timeline.stop();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new Text[][]{
            {cell00, cell01, cell02},
            {cell10, cell11, cell12},
            {cell20, cell21, cell22}
        };
    }

    private void clearBoard() {
        for (Text[] row : cells) {
            for (Text cell : row) {
                cell.setText("");
            }
        }
    }

 private void applyMove(Move move) {
    Text cell = getCell(move.row, move.col);
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


    private Text getCell(int row, int col) {
        return cells[row][col];
    }

    @FXML
    private void onStart(ActionEvent event) {
        if (moves == null || moves.isEmpty()) return;
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) return;

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                if (currentMoveIndex < moves.size()) {
                    applyMove(moves.get(currentMoveIndex));
                    currentMoveIndex++;
                } else {
                    timeline.stop();
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void onRestart(ActionEvent event) {
        if (timeline != null) timeline.stop();
        currentMoveIndex = 0;
        clearBoard();
    }

    @FXML
    private void onBack(ActionEvent event) {
        if (timeline != null) timeline.stop();
        NavigateBetweeenScreens.backToGameRecords(event);
    }
}
