package com.mycompany.clientxogame.ui.level;

import java.util.Random;

public class EasyLevel {

    private String aiPlayer = "O";

    public int[] getMove(String[][] board) {
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
}
