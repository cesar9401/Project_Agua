/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.PagosSocios;
import object.Socios;

/**
 *
 * @author cesar31
 */
public class MainTestJulio extends Application{
    
    public static void main(String[] args){

        Query pagos = getEntityManager().createNamedQuery("PagosSocios.findLatestPago");
        List<PagosSocios> list = pagos.getResultList();
        
        for (PagosSocios pagosSocios : list) {
            System.out.println("pagosSocios = " + pagosSocios.getIdPagosSocios());
        }
    //    launch(args);
    }
private static EntityManager getEntityManager() {
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();

        return emf.createEntityManager();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Pagos.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    
}
