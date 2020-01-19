/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class MainViewController implements Initializable {
        
    @FXML
    private AnchorPane navBar;
    @FXML
    private MenuButton admin_button;
    @FXML
    private MenuItem item_cerrarSesion;
    @FXML
    private Label label_socios;
    @FXML
    private Label label_pagos;
    @FXML
    private AnchorPane adminBar;
    @FXML
    private AnchorPane base_pane;
    @FXML
    private Label label_datos;
    @FXML
    private Label label_codigo;
    @FXML
    private Label codigoAdmin;
    @FXML
    private Label label_nombres;
    @FXML
    private Label nombreAdmin;
    @FXML
    private Label label_apellidos;
    @FXML
    private Label apellidosAdmin;
    @FXML
    private Label label_cuotas;

    @FXML
    void cerrarSesion(ActionEvent event) {
        //Cerrar Sesion
        getLogIn();
    }
    
    @FXML
    private void handleButtonAction(MouseEvent event) throws IOException {
        
        Object obj = event.getSource();
        
        if(obj.equals(label_socios)){
            base_pane.getChildren().clear();
            base_pane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CrearSocio.fxml")));
        }else if(obj.equals(label_pagos)){
            base_pane.getChildren().clear();
            
        }else if(obj.equals(label_cuotas)){
            base_pane.getChildren().clear();
            base_pane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CrearCuotas.fxml")));
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void signOff() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
        
        //Cerra ventana
        admin_button.getScene().getWindow().hide();
    }
    
    public void getLogIn(){
        try {
            signOff();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
