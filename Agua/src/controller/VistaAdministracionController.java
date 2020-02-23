/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Administradores;
import object.Socios;
import object.auxiliary.ViewSocio;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class VistaAdministracionController implements Initializable {

    private ObservableList<ViewSocio> administradores;
    
    @FXML
    private Label label_cuenta;
    @FXML
    private ImageView image_admin;
    @FXML
    private Label label_codigo;
    @FXML
    private Label label_nombre;
    @FXML
    private Label label_apellidos;
    @FXML
    private Label codigo;
    @FXML
    private Label nombres;
    @FXML
    private Label apellidos;
    @FXML
    private Label label_telefono;
    @FXML
    private Label label_dpi;
    @FXML
    private Label label_direccion;
    @FXML
    private Label telefono;
    @FXML
    private Label dpi;
    @FXML
    private Label direccion;
    @FXML
    private Label label_codigo1;
    @FXML
    private Label codigo1;
    @FXML
    private Button button_password;
    @FXML
    private Label label_administradores;
    @FXML
    private TableView<ViewSocio> table_admins;
    @FXML
    private TableColumn colCodigoA;
    @FXML
    private TableColumn colNombresA;
    @FXML
    private TableColumn colApellidosA;
    @FXML
    private Button eliminarAdmin;
    @FXML
    private Button nuevoAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        eliminarAdmin.setDisable(true);
        createTable();
        setTable();
        setImage();
        setDatos();
    }    
    
    @FXML
    private void cambiarAction(ActionEvent event) throws IOException {
        //Acciones para cambiar contraseña
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EditPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Cambiar Contraseña - " + administrador.Administrador.getSocio().getCodigo());
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    private void seleccionarAdministradores(MouseEvent event) {
        ViewSocio tmp = table_admins.getSelectionModel().getSelectedItem();
        if(tmp != null){
            eliminarAdmin.setDisable(false);
        }else{
            eliminarAdmin.setDisable(true);
        }
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        deleteAdmin();
    }

    @FXML
    private void nuevoAction(ActionEvent event) throws IOException {
        nuevoAdmin();
    }    
    
    public void setImage(){
        Socios tmp = administrador.Administrador.getSocio();
        try{
            if(tmp.getFotografia().toString() == null){
                Image img = new Image("/img/usr+.png");
                image_admin.setImage(img);
            }else{
                InputStream input = new ByteArrayInputStream(tmp.getFotografia());
                Image image = new Image(input);
                image_admin.setImage(image);
            }        
        }catch(Exception e){
            Image img = new Image("/img/usr+.png");
            image_admin.setImage(img);
        }
    }
    
    public void setDatos(){
        Socios socio = administrador.Administrador.getSocio();
        codigo.setText(socio.getCodigo());
        nombres.setText(socio.getNombres());
        apellidos.setText(socio.getApellidos());
        telefono.setText(socio.getTelefono()+"");
        dpi.setText(socio.getDpi());
        direccion.setText(socio.getDireccion());
        
        codigo1.setText(socio.getCodigo());
    }
    
    public void createTable(){
        administradores = FXCollections.observableArrayList();
        colCodigoA.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresA.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosA.setCellValueFactory(new PropertyValueFactory("apellidos"));
    }
    
    public void setTable(){
        administradores.clear();
        List<ViewSocio> admins = getAdministradoresVista();
        
        if(!administradores.containsAll(admins)){
            administradores.addAll(admins);
            table_admins.setItems(administradores);
        }
    }
    
    public List<ViewSocio> getAdministradoresVista(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        
        Query getAdmins = em.createNamedQuery("Administradores.findAll");
        
        List<ViewSocio> viewSocios = new ArrayList<>();
        List<Administradores> admins = new ArrayList<>();
        
        admins = getAdmins.getResultList();
        
        for(Administradores a: admins){
            viewSocios.add(new ViewSocio(a.getIdAdministrador(), a.getSociosIdSocio().getCodigo(), a.getSociosIdSocio().getNombres(), a.getSociosIdSocio().getApellidos()));
        }
        
        return viewSocios;
    }
    
    public void nuevoAdmin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Crear Administrador");
        stage.showAndWait();
        
        setTable();
    }
    
    public void deleteAdmin(){
        ViewSocio tmp = table_admins.getSelectionModel().getSelectedItem();
        
        if(tmp != null){
            if(tmp.getIdSocio() != administrador.Administrador.getAdmin().getIdAdministrador()){
                //Acciones para eliminar
                
                
                setTable();
            }else{
                //Un administrador no puede eliminarse a el mismo.
                Alert errorInfo = new Alert(Alert.AlertType.ERROR);
                errorInfo.setTitle("Error");
                errorInfo.setHeaderText(" Accion no Valida");
                errorInfo.setContentText("No te puedes eliminar a ti mismo.");
                errorInfo.show(); 
            }
            
        
        }else{
            Alert errorInfo = new Alert(Alert.AlertType.ERROR);
            errorInfo.setTitle("Error");
            errorInfo.setHeaderText(" Accion no Valida");
            errorInfo.setContentText("Debe seleccionar un administrador para poder eliminarlo");
            errorInfo.show(); 
        }
    }
}
