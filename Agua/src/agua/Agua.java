/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agua;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

        initWithLogo(stage);
//        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
//        stage.setTitle("Login");
//        stage.setScene(new Scene(root));
//        stage.show();
    }
     public void initWithLogo(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearCuotas.fxml"));
        
        Parent root = loader.load();
        
       // Scene scene = new Scene(root);
     //   scene.getWindow();
        try {
            stage.getIcons().add(new Image("/img/icono200x200.png"));
            
        } catch (Exception e) {
            System.out.println("SIn logo");
        }
        
        stage.setTitle("Login");
        stage.show();
    }
    
}
