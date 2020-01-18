/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.apache.commons.io.IOUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
  
    @FXML
    private ImageView img;
    @FXML
    private JFXToggleButton isExonerated;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtCui;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXToggleButton mancomunado;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXTextField txtDireccion;
    
    private boolean changeImg;
    private String pathImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image imgUsr = new Image("/img/usr+.png");
        img.setImage(imgUsr);
        changeImg = false;
        
        txtCui.setOnKeyTyped(event -> validationOfNumber(event));
        txtCode.setOnKeyTyped(event -> validationOfNumber(event));
    }    

    @FXML
    private void crearSocio(ActionEvent event) {
        captureData();
    }
    
    @FXML
    private void loadImg(MouseEvent event) {
    
        FileChooser filechooser = new FileChooser();
        File selectFile = filechooser.showOpenDialog(null);
        
        if (selectFile != null) {
            if (selectFile.isFile() && 
                    (selectFile.getName().contains(".png") || selectFile.getName().contains(".jpg") || selectFile.getName().contains(".bmp") || selectFile.getName().contains(".gif"))) {
                    
                System.out.println("Path: "+ selectFile.getAbsolutePath());
                Image imgChange = new Image("file:"+selectFile.getAbsolutePath());
                img.setImage(imgChange);
                changeImg = true;
                pathImg = selectFile.getAbsolutePath();
                
            }else
                Alerta.Alerta.AlertInformation("Imagen", "Archivo Invalido", "Debe seleccionar una Imagen");
            
           
            
            
        }
    }
    
    
    /**
     * verifica que rellene los campos obligatorios sin tener duplicados en la base de datos
     * si cumple con lo anterior guarda al nuevo socio
     */
    public void captureData(){
        
        Socios nuevo = new Socios();
        
      
        if (!txtCode.getText().isEmpty() && !txtLastName.getText().isEmpty() && !txtName.getText().isEmpty() &&  datePicker.getValue() != null) {
            
            nuevo.setNombres(txtName.getText());
            nuevo.setApellidos(txtLastName.getText());
            nuevo.setDpi(txtCui.getText());
            nuevo.setDireccion(txtDireccion.getText());
            nuevo.setFechaInicioPago(java.sql.Date.valueOf(datePicker.getValue()));
            
            
            if (checkCode() !=  null) {
                nuevo.setCodigo(checkCode());
            }
            
            if (changeImg) {
            
                try {
                    
                    FileInputStream myStream = new FileInputStream(pathImg);
                    byte[] imageInBytes = IOUtils.toByteArray(myStream); 
                    nuevo.setFotografia(imageInBytes);
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CrearSocioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Alerta.Alerta.AlertError("Error", "Imagen", "Ocurrio un error al intentar guardar la imagen");
                    Logger.getLogger(CrearSocioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Alerta.Alerta.AlertInformation("Informacion", "Nuevo Socio", "Almacenado Correctamente");
        }else{
            Alerta.Alerta.AlertInformation("Faltan Datos", "Informacion", "Debe llenar los Campos obligatorios");
        }
        
        
        
        
        
        
        
        
    }
    public String checkCode(){
        
        
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();

        Query consultaCodigo;
        String useThisCode;

            if (mancomunado.isSelected()) {
                useThisCode = "B-"+txtCode.getText();
                 consultaCodigo = em.createNamedQuery("Socios.findByCodigo").setParameter("codigo", "B-"+txtCode.getText());

            }else{
                useThisCode = "A-"+txtCode.getText();
                 consultaCodigo = em.createNamedQuery("Socios.findByCodigo").setParameter("codigo", "A-"+txtCode.getText());

            }
        if (consultaCodigo.getResultList().size()>0) {

            Alerta.Alerta.AlertInformation("Informacion", "Codigo", "El Codigo que Ingreso ya Existe");
            
            txtCode.setText("");
            return null;

        }
        return useThisCode;
        

    }
    /**
     * metodo que sirve para que el usuario ingrese solo numeros
     * 
     * @param keyEvent 
     */
    public void validationOfNumber(KeyEvent keyEvent){
        
        try{
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex){ }
    }
    
}
