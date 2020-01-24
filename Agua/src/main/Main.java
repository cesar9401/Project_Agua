/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author cesar31
 */
public class Main extends Application{
    
    public static void main(String[] args){
        //Write your code here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initWithLogo(stage);
        
//        Parent root = FXMLLoader.load(getClass().getResource("../view/CrearCuotas.fxml"));
//        stage.setTitle("Login");
//        stage.setScene(new Scene(root));
//        stage.show();
    }
    public void initWithLogo(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getWindow();
//    
        try {
            stage.getIcons().add(new Image("/img/icono200x200.png"));
            
        } catch (Exception e) {
            System.out.println("SIn logo");
        }
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    
}
