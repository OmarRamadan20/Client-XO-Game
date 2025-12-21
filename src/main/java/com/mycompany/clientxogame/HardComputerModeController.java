/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import javafx.util.Pair;

/**
 *
 * @author amr04
 */
 
 //============omer===============================
interface ComputerMove {
    Pair<Integer, Integer> move(char[][] board);
}
public class HardComputerModeController implements ComputerMove {

    private final char computerPlayer = 'O';
    private final char humanPlayer = 'X';

    @Override
    public Pair<Integer, Integer> move(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        Pair<Integer, Integer> bestMove = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0' || board[i][j] == ' ') {  
                    board[i][j] = computerPlayer;
                    int score = alphaBeta(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board[i][j] = '\0'; 
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Pair<>(i, j);
                    }
                }
            }
        }
        return bestMove;
    }

    private int alphaBeta(char[][] board, int depth, int alpha, int beta, boolean isMax) {
        int stateValue = evaluate(board);

        if (stateValue == 10) return stateValue - depth;
        if (stateValue == -10) return stateValue + depth;
        if (!hasMoves(board)) return 0;

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '\0') {
                        board[i][j] = computerPlayer;
                        int eval = alphaBeta(board, depth + 1, alpha, beta, false);
                        board[i][j] = '\0';
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '\0') {
                        board[i][j] = humanPlayer;
                        int eval = alphaBeta(board, depth + 1, alpha, beta, true);
                        board[i][j] = '\0';
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return minEval;
        }
    }

    private int evaluate(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '\0')
                return (board[i][0] == computerPlayer) ? 10 : -10;
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '\0')
                return (board[0][i] == computerPlayer) ? 10 : -10;
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\0')
            return (board[0][0] == computerPlayer) ? 10 : -10;
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\0')
            return (board[0][2] == computerPlayer) ? 10 : -10;
        return 0;
    }

    private boolean hasMoves(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\0') return true;
            }
        }
        return false;
    }
}