/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame.ui.history;

import com.mycompany.clientxogame.ui.history.Move;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
/**
 *
 * @author Aladawy
 */
public class GameFileManager {

private static final String DIR = "game_records";

static {
    new File(DIR).mkdirs();
}

public static void save(List<Move> moves, String player1, String player2) {
     String fileName = "XO_" + player1 + "_vs_" + player2 + ".txt";
    saveToFile(moves, fileName);
}

private static void saveToFile(List<Move> moves, String fileName) {
    try (FileWriter writer = new FileWriter(DIR + "/" + fileName)) { 
        for (Move m : moves) {
            writer.write(m.playerId + "," + m.row + "," + m.col + "\n");
        }
        System.out.println("Game saved: " + fileName);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
public static List<Move> load(String fileName) {
        List<Move> moves = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(DIR + "/" + fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                moves.add(new Move(
                        Integer.parseInt(p[0]),
                        Integer.parseInt(p[1]),
                        Integer.parseInt(p[2])
                ));
            }

            System.out.println("Game loaded: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return moves;
    }
    
   public static List<String> getPlayerGames(String playerName) {
    File folder = new File(DIR);
    String[] files = folder.list((dir, name) -> name.endsWith(".txt") && name.contains(playerName));
    if (files == null) return new ArrayList<>();
    Arrays.sort(files); 
    return Arrays.asList(files);
}

}
