/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame.network;

import org.json.JSONObject;

/**
 *
 * @author amr04
 */
public interface ServerListener {
    void onMessage(JSONObject msg);
}
