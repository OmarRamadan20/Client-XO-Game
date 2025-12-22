/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import org.json.JSONObject;

/**
 *
 * @author amr04
 */
 

public class ServerHandler extends Thread {

    private static ServerHandler instance;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private ServerListener listener;

     private ServerHandler() {}

     public static ServerHandler getInstance() {
        if (instance == null) {
            instance = new ServerHandler();
            instance.start();
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 5555);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

             while (true) {
                String msg = reader.readLine();
                if (msg == null) break;
                handleMessage(msg);
            }

        } catch (Exception e) {
            System.out.println("Disconnected from server");
            e.printStackTrace();
        }
    }

     public void send(JSONObject json) {
        if (writer != null) {
            writer.println(json.toString());
        }
    }

     private void handleMessage(String msg) {
        JSONObject json = new JSONObject(msg);
        if (listener != null) {
             Platform.runLater(() -> listener.onMessage(json));
        }
    }

     public void setListener(ServerListener listener) {
        this.listener = listener;
    }
}
 
