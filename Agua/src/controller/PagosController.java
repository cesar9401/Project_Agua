/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Administradores;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class PagosController implements Initializable {

    //Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;
    
    @FXML
    private JFXTextField txtCodigoSocio;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombreSocio;
    @FXML
    private JFXTextField txtCui;
    @FXML
    private JFXDatePicker dateUltimo;
    @FXML
    private JFXDatePicker dateActual;
    @FXML
    private JFXDatePicker datePagarHasta;
    @FXML
    private JFXTextField txtSancion;
    @FXML
    private JFXTextField txtSancionDeTrabajo;
    @FXML
    private JFXTextField txtTotalDeConstruccion;
    @FXML
    private JFXTextField txtTotalDeMora;
    @FXML
    private TableView<?> tblDetalle;
    @FXML
    private TableColumn<?, ?> columnCodigo;
    @FXML
    private TableColumn<?, ?> columnDescripcion;
    @FXML
    private TableColumn<?, ?> columnPrecio;
    @FXML
    private JFXButton btnVisualizarPago;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtNoRecibo;
    @FXML
    private JFXTextField txtTotalPago;
    @FXML
    private JFXButton btnEliminarFIla;
    @FXML
    private JFXToggleButton togglePropietario;
    @FXML
    private Label lblPagarHasta;

    private Socios tmp;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnBusccar(ActionEvent event) {
        tmp = null;        
        
        String codigo = (togglePropietario.isSelected())?"A-"+txtCodigoSocio.getText():"B-"+txtCodigoSocio.getText();            
            
        Query buscar = getEntityManager().createNamedQuery("Socios.findByCodigo").setParameter("codigo", codigo);
        if (buscar.getResultList().size() > 0) {
            this.tmp =(Socios) buscar.getResultList().get(0);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Informacion ");
            alert.setHeaderText("Codgio de Socio");
            alert.setContentText("El codigo de socio no se encuentra en la base de datos");
            alert.show();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
        togglePropietario.setOnAction(e->{
          
        });
    }
    
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
    }
    
    public Socios searchSocio(String codigo){
        
        Query buscar = getEntityManager().createNamedQuery("Socios.findByCodigo").setParameter("codigo", codigo);
        return null;
        
    }
    
    private EntityManager getEntityManager(){
         EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        
        return emf.createEntityManager();
    }

    
}
