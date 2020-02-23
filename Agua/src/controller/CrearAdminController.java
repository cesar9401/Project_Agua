/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Socios;
import object.auxiliary.ViewSocio;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class CrearAdminController implements Initializable {

    private ObservableList<ViewSocio> noAdmins;
    
    //img
    Image confirm = new Image("/img/confirmar.png");
    Image error = new Image("/img/error.png");    
    
    @FXML
    private TableView<ViewSocio> table_admins;
    @FXML
    private TableColumn colCodigoA;
    @FXML
    private TableColumn colNombresA;
    @FXML
    private TableColumn colApellidosA;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button button_confirmar;
    @FXML
    private Button button_cancelar;
    @FXML
    private ImageView confirm_image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usuario.setEditable(false);
        createTable();
        setTable();
    }    

    @FXML
    private void seleccionarSocios(MouseEvent event) {
    }

    @FXML
    private void pedirPassword(KeyEvent event) {
        if(!confirmPassword.getText().isEmpty()){
            String pass = password.getText();
            String confirm_pass = confirmPassword.getText();
            
            if(pass.equals(confirm_pass)){
                confirm_image.setImage(confirm);
                button_confirmar.setDisable(false);
            }else{
                confirm_image.setImage(error);
                button_confirmar.setDisable(true);            
            }
        }        
    }

    @FXML
    private void confirmarPassword(KeyEvent event) {
        
        if(!password.getText().isEmpty()){
            String pass = password.getText();
            String confirm_pass = confirmPassword.getText();
            
            if(pass.equals(confirm_pass)){
                confirm_image.setImage(confirm);
                button_confirmar.setDisable(false);
            }else{
                confirm_image.setImage(error);
                button_confirmar.setDisable(true);            
            }
        }        
    }

    @FXML
    private void confirmarAction(ActionEvent event) {
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        this.button_cancelar.getScene().getWindow().hide();
    }
    
    public void createTable(){
        noAdmins = FXCollections.observableArrayList();
        colCodigoA.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresA.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosA.setCellValueFactory(new PropertyValueFactory("apellidos"));        
    }
    
    public void setTable(){
        noAdmins.clear();
        List<ViewSocio> noAdmin = getNoAdministradores();
        
        if(!noAdmins.containsAll(noAdmin)){
            noAdmins.addAll(noAdmin);
            table_admins.setItems(noAdmins);
        }
    }
    
    public List<ViewSocio> getNoAdministradores(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        
        Query getNoAdmins = em.createNativeQuery("SELECT * FROM socios LEFT JOIN administradores ON id_socio = administradores.socios_id_socio WHERE administradores.socios_id_socio IS NULL", Socios.class);
        
        List<Socios> noAdmin = new ArrayList<>();
        List<ViewSocio> viewSocios = new ArrayList<>();
    
        noAdmin = getNoAdmins.getResultList();
        
        for(Socios s: noAdmin){
            viewSocios.add(new ViewSocio(s.getIdSocio(), s.getCodigo(), s.getNombres(), s.getApellidos()));
        }
        
        return viewSocios;
    }
    
}
