/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.persistence.EntityManagerFactory;
import model.EventosJpaController;
import model.exceptions.NonexistentEntityException;
import object.Eventos;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class EditEventosController implements Initializable {

    private Eventos evt;
    private double precio = 0;
    
    @FXML
    private ImageView image_moneda;
    @FXML
    private JFXTextField txt_ValorCuota;
    @FXML
    private JFXTextField txt_nombreEvento;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private DatePicker fecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image coin = new Image("/img/calendario.png");
        image_moneda.setImage(coin);
    }

    public void initializeAttributes(Eventos evt){
        this.evt = evt;
        showAttributes();
        precio = evt.getCuota().doubleValue();
    }
    
    public void showAttributes(){
        java.sql.Date date = new java.sql.Date(evt.getFecha().getTime());
        txt_nombreEvento.setText(evt.getNombre());
        txt_ValorCuota.setText(evt.getCuota() + "");
        fecha.setValue(date.toLocalDate());
    }

    @FXML
    private void validar(KeyEvent event) {
        try{
            precio = Double.parseDouble(txt_ValorCuota.getText());
        }catch(NumberFormatException e){
            txt_ValorCuota.setText("");
        } 
    }

    @FXML
    private void editarCuotaAction(ActionEvent event) {
        //Acciones para editar evento
        editEvento();
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        this.image_moneda.getScene().getWindow().hide();
    }
    
    public void editEvento(){
        if(!txt_nombreEvento.getText().isEmpty() && !txt_ValorCuota.getText().isEmpty() && fecha.getValue() != null){
            
            BigDecimal valor = BigDecimal.valueOf(precio);
            
            evt.setNombre(txt_nombreEvento.getText());
            evt.setCuota(valor);
            evt.setFecha(java.sql.Date.valueOf(fecha.getValue()));
            
            setEditEvento();
            
            //Cerrar ventana
            this.image_moneda.getScene().getWindow().hide();
            
        }else{
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setContentText("Debe llenar los campos obligatorios");
            alertaError.show();
        }
    }
    
    public void setEditEvento(){
        try {
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            EventosJpaController editEvt = new EventosJpaController(emf);
            editEvt.edit(evt);
            
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setContentText("Evento Editado Correctamente");
            info.show(); 
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditEventosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditEventosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
