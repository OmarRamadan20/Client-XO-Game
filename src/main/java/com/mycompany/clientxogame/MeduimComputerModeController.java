/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

/**
 *
 * @author user
 */


import java.util.Random;

public class MeduimComputerModeController {

    private String aiPlayer = "O";
    private String humanPlayer = "X";

    public int[] getMove(String[][] board) {
        int[] move = getImmediateMove(board);
        return (move != null) ? move : getRandomMove(board);
    }

    private int[] getImmediateMove(String[][] board) {
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    board[i][j] = aiPlayer;
                    if (aiPlayer.equals(checkWinner(board))) {
                        board[i][j] = "";
                        return new int[]{i, j};
                    }
                    board[i][j] = "";
                }
            }
        }

      
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    board[i][j] = humanPlayer;
                    if (humanPlayer.equals(checkWinner(board))) {
                        board[i][j] = "";
                        return new int[]{i, j};
                    }
                    board[i][j] = "";
                }
            }
        }

        return null;
    }

    private int[] getRandomMove(String[][] board) {
        Random r = new Random();
        int row, col;
        boolean hasEmpty = false;
        for (String[] rows : board) for (String s : rows) if (s.equals("")) hasEmpty = true;
        if (!hasEmpty) return null;

        do {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!board[row][col].equals(""));
        return new int[]{row, col};
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