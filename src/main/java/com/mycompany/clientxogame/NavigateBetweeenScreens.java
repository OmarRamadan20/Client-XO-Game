/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientxogame;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class NavigateBetweeenScreens {
    private  static void changeScene(ActionEvent event ,String fxmlFile,String title)
    {
        try {
            Parent root=FXMLLoader.load(NavigateBetweeenScreens.class.getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            System.getLogger(NavigateBetweeenScreens.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
}
