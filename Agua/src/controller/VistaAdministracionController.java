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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class VistaAdministracionController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
}
