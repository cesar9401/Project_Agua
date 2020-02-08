/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.persistence.EntityManagerFactory;
import model.CuotasJpaController;
import model.exceptions.NonexistentEntityException;
import object.Cuotas;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class EditCuotasController implements Initializable {
    
    private Cuotas cta;
    private double precio = 0;
    
    @FXML
    private ImageView image_moneda;
    @FXML
    private JFXTextField txt_ValorCuota;
    @FXML
    private JFXTextField txt_nombreCuota;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_editar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image coin = new Image("/img/moneda.png");
        image_moneda.setImage(coin);
    }

    public void initializeAttributes(Cuotas cta){
        this.cta = cta;
        showAttributes();
        precio = cta.getValorCuota().doubleValue();
    }
    
    public void showAttributes(){
        txt_nombreCuota.setText(cta.getNombreCuota());
        txt_ValorCuota.setText(cta.getValorCuota() + "");
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
    private void cancelarAction(ActionEvent event) {
        this.image_moneda.getScene().getWindow().hide();
    }

    @FXML
    private void editarCuotaAction(ActionEvent event) {
        //Acciones para editar cuota
        editCuota();
    }
    
    public void editCuota(){
        if(!txt_nombreCuota.getText().isEmpty() && !txt_ValorCuota.getText().isEmpty()){
            
            BigDecimal valor = BigDecimal.valueOf(precio);
            //Acciones para editar cuota
            
            cta.setNombreCuota(txt_nombreCuota.getText());
            cta.setValorCuota(valor);
            
            setEditCuota();
            
            //Cerrar ventana
            this.image_moneda.getScene().getWindow().hide();
            
        }else{
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setContentText("Debe llenar los campos obligatorios");
            alertaError.show();
        }
    }
    
    public void setEditCuota(){
        try {
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            CuotasJpaController editCta = new CuotasJpaController(emf);
            editCta.edit(cta);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setContentText("Couta Editada Correctamente");
            info.show(); 
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditCuotasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditCuotasController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
