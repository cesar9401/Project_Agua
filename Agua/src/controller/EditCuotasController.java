/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import object.Cuotas;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class EditCuotasController implements Initializable {
    
    private Cuotas cta;
    @FXML
    private ImageView image_moneda;
    @FXML
    private JFXTextField txt_ValorCuota;
    @FXML
    private JFXTextField txt_nombreCuota;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_cancelar;

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
    }

    @FXML
    private void validar(KeyEvent event) {
    }

    @FXML
    private void agregarCuotaAction(ActionEvent event) {
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
    }
    
}
