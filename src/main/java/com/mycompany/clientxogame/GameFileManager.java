/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

   
    public static void save(List<Move> moves) {
        String fileName = "game_" + System.currentTimeMillis() + ".txt";
        save(moves, fileName);
    }

    public static void save(List<Move> moves, String fileName) {
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
    
    public static List<String> getAllGames() {
        File folder = new File(DIR);
        String[] files = folder.list((dir, name) -> name.endsWith(".txt"));
        if (files == null) return new ArrayList<>();
        return Arrays.asList(files);
    }
}
