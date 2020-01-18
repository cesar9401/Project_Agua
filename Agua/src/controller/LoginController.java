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
import object.Socios;


/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class LoginController implements Initializable {
    
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
        String user = text_socio.getText();
        String pass = pass_socio.getText();
            
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        Query consultaLogin = em.createNamedQuery("Socios.findAll");
        List<Socios> socios = (List<Socios>) consultaLogin.getResultList();
        for(Socios s: socios){
            System.out.println(s.getCodigo());
        }
        
//        try {
//            getMainView();
//        } catch (IOException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
}
