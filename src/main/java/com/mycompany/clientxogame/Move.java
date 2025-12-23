/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

/**
 *
 * @author Aladawy
 */
public class Move {
    int playerId;
    int row;
    int col;

    public Move(int playerId, int row, int col) {
        this.playerId = playerId;
        this.row = row;
        this.col = col;
    }
}
