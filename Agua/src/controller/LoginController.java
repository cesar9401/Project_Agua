/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Administradores;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class LoginController implements Initializable {
    
    private Socios socio;
    private Administradores admin;
    
    @FXML
    private Label label_socio;

    @FXML
    private TextField text_socio;

    @FXML
    private Label label_pass;

    @FXML
    private PasswordField pass_socio;

    @FXML
    private Button button_login;

    @FXML
    private ImageView image_view;

    @FXML
    void iniciarSesion(ActionEvent event) {

        //Evento Iniciar Sesion
        obtener();
        
    }
    
    public void obtener(){
        
        //Evento Iniciar Sesion
        socio = getSocioBD();
        admin = getAdmin(socio);
        
        if(admin != null){
            logIn();
        }else{
            System.out.println("Usuario y/o contraseña incorrecta");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        URL nuevo = getClass().getResource("/img/icono200x200.png");
        Image image = new Image(nuevo.toString(), 200, 200, false, true);
        image_view.setImage(image);
    }
    
    public void getMainView() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Inicio");
        stage.setScene(new Scene(root));
        stage.show();
        label_socio.getScene().getWindow().hide();
    }
    
    public void logIn(){
        try {
            getMainView();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Socios getSocioBD(){
        Socios socio = null;
        
        String user = text_socio.getText();
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        Query consultaSocio = em.createNamedQuery("Socios.findByCodigo").setParameter("codigo", user);
        try{
            socio = (Socios) consultaSocio.getSingleResult();
        }catch(Exception e){
            
        }
    
        return socio;
    }
    
    public Administradores getAdmin(Socios socio){
        Administradores admin = null;
        
        String pass = pass_socio.getText();
        EntityManagerFactory emf2 = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em2 = emf2.createEntityManager();
        Query consultaAdmin = em2.createNamedQuery("Administradores.findByPassword").setParameter("password", pass);
        
        List<Administradores> admins = null;
        try {
            admins = (List<Administradores>) consultaAdmin.getResultList();
        } catch (Exception e) {
             
        }
        
        if(admins != null && socio != null){
            for(Administradores a: admins){
                if(a.getSociosIdSocio().equals(socio)){
                    admin = a;
                    break;
                }
            }        
        }
        
        return admin;
    }

    //Getter and Setter Socios y Administradores
    public Socios getSocio() {
        return socio;
    }

    public void setSocio(Socios socio) {
        this.socio = socio;
    }

    public Administradores getAdmin() {
        return admin;
    }

    public void setAdmin(Administradores admin) {
        this.admin = admin;
    }
}
