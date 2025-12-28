/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame.ui.level;

import javafx.util.Pair;

/**
 *
 * @author amr04
 */
  public class HardLevel {

    private String aiPlayer = "O";
    private String humanPlayer = "X";

    public int[] getMove(String[][] board) {
        return getBestMove(board);
    }

    private int minimax(String[][] board, int depth, boolean isMaximizing) {
        String result = checkWinner(board);
        if (result != null) {
            if (result.equals(aiPlayer)) return 10 - depth;
            if (result.equals(humanPlayer)) return depth - 10;
            if (result.equals("Tie")) return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    board[i][j] = aiPlayer;
                    int score = minimax(board, depth + 1, false);
                    board[i][j] = "";
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    board[i][j] = humanPlayer;
                    int score = minimax(board, depth + 1, true);
                    board[i][j] = "";
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private int[] getBestMove(String[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
            if (board[i][j].equals("")) {
                board[i][j] = aiPlayer;
                int score = minimax(board, 0, false);
                board[i][j] = "";
                if (score > bestScore) {
                    bestScore = score;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }
        return move;
    }

    private String checkWinner(String[][] b) {
        for (int i = 0; i < 3; i++) {
            if (!b[i][0].equals("") && b[i][0].equals(b[i][1]) && b[i][0].equals(b[i][2])) return b[i][0];
            if (!b[0][i].equals("") && b[0][i].equals(b[1][i]) && b[0][i].equals(b[2][i])) return b[0][i];
        }
        if (!b[0][0].equals("") && b[0][0].equals(b[1][1]) && b[0][0].equals(b[2][2])) return b[0][0];
        if (!b[0][2].equals("") && b[0][2].equals(b[1][1]) && b[0][2].equals(b[2][0])) return b[0][2];

        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) if (b[i][j].equals("")) return null;

        return "Tie";
    }
}