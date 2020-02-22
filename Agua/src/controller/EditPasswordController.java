/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class EditPasswordController implements Initializable {

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
    private TextField codigo1;
    @FXML
    private Button button_cancelar;
    @FXML
    private Button button_confirmar;
    @FXML
    private PasswordField password;

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
    private void cancelarAction(ActionEvent event) {
        button_cancelar.getScene().getWindow().hide();
    }

    @FXML
    private void confirmarAction(ActionEvent event) {
        //Acciones evento cambiar contraseña
        updatePassword();
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
        codigo1.setEditable(false);
    }    
    
    public void updatePassword(){
        if(!password.getText().isEmpty()){
            String pass = password.getText();
            
        }else{
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setContentText("Debe llenar el campo: \"Nueva Contraseña\"");
            alertaError.show();
        }
    }
}
