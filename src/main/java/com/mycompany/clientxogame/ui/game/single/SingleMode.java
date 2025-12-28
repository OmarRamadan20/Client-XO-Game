package com.mycompany.clientxogame.ui.game.single;

import com.mycompany.clientxogame.ui.level.HardLevel;
import com.mycompany.clientxogame.ui.level.MediumLevel;
import com.mycompany.clientxogame.ui.level.EasyLevel;

public class SingleMode {

    private EasyLevel easyAI = new EasyLevel();
    private MediumLevel mediumAI = new MediumLevel();
    private HardLevel hardAI = new HardLevel();

    public int[] getMove(String[][] board, String difficulty) {
        if (difficulty.equalsIgnoreCase("Easy")) {
            return easyAI.getMove(board);
        } else if (difficulty.equalsIgnoreCase("Medium")) {
            return mediumAI.getMove(board);
        } else if (difficulty.equalsIgnoreCase("Hard")) {
            return hardAI.getMove(board);
        } else {
            return easyAI.getMove(board);
        }
    }
}
