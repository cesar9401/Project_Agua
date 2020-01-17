/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.persistence.EntityManagerFactory;
import model.SociosJpaController;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class CrearSocioController implements Initializable {

    @FXML
    private JFXButton Crear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearSocio(ActionEvent event) {
        try {
            methodTest();
        } catch (ParseException ex) {
            Logger.getLogger(CrearSocioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void methodTest() throws ParseException{
         EntityManagerFactory  emf = conexion.ConexionJPA.getInstancia().getEMF();
        SociosJpaController guardar = new SociosJpaController(emf);
        
        //String fecha = "2015-01-01";
        //Date fecha = new Date();
        //Date hoy = new Date(2018, 01, 01);
        Socios nuevo = new Socios();
        
        nuevo.setCodigo("111");
        nuevo.setNombres("nombre");
        nuevo.setApellidos("apellido");
        nuevo.setDpi("24356");
        nuevo.setDireccion("afd");
         
          
       // nuevo.setFechaInicioPago(hoy);
        //nuevo.setFechaInicioPago(fecha);
        nuevo.setExonerado(false);
        
        
        guardar.create(nuevo);
    }
    
}
