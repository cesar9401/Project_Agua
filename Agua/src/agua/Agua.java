/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agua;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author julio
 */
public class Agua extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
        
        
    }
    
}
