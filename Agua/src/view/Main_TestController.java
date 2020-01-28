/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CrearCuotasController;
import controller.CrearEventosController;
import controller.CrearSocioController;
import controller.ModifySocioController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import object.Administradores;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class Main_TestController implements Initializable {

    @FXML
    private AnchorPane navBar;
    @FXML
    private MenuButton admin_button;
    @FXML
    private MenuItem item_cerrarSesion;
    @FXML
    private MenuButton menu_btnSocios;
    @FXML
    private MenuItem item_nuevoSocio;
    @FXML
    private MenuItem item_VerSocios;
    @FXML
    private MenuButton menu_btnPagos;
    @FXML
    private MenuItem item_eventos;
    @FXML
    private MenuButton menu_btnCuotas;
    @FXML
    private MenuItem item_VerCuotas;
    @FXML
    private Label label_inicio;
    
    private Socios socio;
    private Administradores admin;
    @FXML
    private BorderPane border;
    @FXML
    private StackPane stackPane;

    static BorderPane borderStatic;
    @FXML
    private MenuItem item_pagos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        borderStatic=border;
    }    

  @FXML
    void cerrarSesion(ActionEvent event) {
        //Cerrar Sesion
        getLogIn();
    }

    //Metodo para recibir los objetos de tipo Socios y Aministradores con informacion del admin logueado
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
        setAttributesView();
    }

    public void setAttributesView(){
       // codigoAdmin.setText(socio.getCodigo());
        //nombreAdmin.setText(socio.getNombres());
        //apellidosAdmin.setText(socio.getApellidos());
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
            Logger.getLogger(Main_TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para cargar las vistas fxml
    @FXML
    private void handleItemAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) navBar.getScene().getWindow();
        Parent root = null;
        Object obj = event.getSource();
  
        if(obj == item_nuevoSocio){

             loadView("crearSocio");
           
        }else if(obj == item_VerSocios){
            loadView("ModifySocio");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifySocio.fxml"));
//            root = loader.load();
//            ModifySocioController controller = loader.getController();
//            controller.initializeAttributes(socio, admin);
        }else if(obj == item_VerCuotas){
            loadView("CrearCuotas");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearCuotas.fxml"));
//            root = loader.load();
//            CrearCuotasController controller = loader.getController();
//            controller.initializeAttributes(socio, admin);
        }else if(obj == item_eventos){
            loadView("CrearEventos");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearEventos.fxml"));
//            root = loader.load();
//            CrearEventosController controller = loader.getController();
//            controller.initializeAttributes(socio, admin);
        }else if (obj == item_pagos) {
            loadView("pagos");
        }
        
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    //Metodo para obtener MainView.fxml
    @FXML
    private void handleItemActionMouse(MouseEvent event) throws IOException {
        Stage stage = (Stage) navBar.getScene().getWindow();
        Parent root = null;
        Object obj = event.getSource();
        if(obj == label_inicio){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
            root = loader.load();
            Main_TestController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //-----------Aqui termina metodos del navBar y adminBar
    
    public void loadView(String vista){
        
          //border.getChildren().clear();
            try {
                
                System.out.println("Vista Load "+ vista);
            border.setCenter(FXMLLoader.load(getClass().getResource("/view/"+vista+".fxml")));
           // stackPane.getChildren().add(border);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
  

