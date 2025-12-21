/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import org.json.JSONObject;

/**
 *
 * @author amr04
 */
class ServerHandler {

    public ServerHandler() {
        Socket mySocket;
        try {
            mySocket = new Socket("127.0.0.1", 5555);
            PrintStream ps = new PrintStream(mySocket.getOutputStream());
            DataInputStream dis;
            dis = new DataInputStream(mySocket.getInputStream());
            JSONObject request = new JSONObject();
            request.put("type", "login");
            request.put("gmail","amr042277@gmail.com");
            request.put("password", "1234");

            ps.println(request.toString());
            String replyMsg = null;
            replyMsg = dis.readLine();

            System.out.println(replyMsg);
        } catch (IOException ex) {
            System.getLogger(ServerHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

}
