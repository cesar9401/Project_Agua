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
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Administradores;
import object.Socios;
import object.auxiliary.DetallePago;
import object.auxiliary.PopSocios;
import object.auxiliary.ViewSocio;
import org.controlsfx.control.PopOver;

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
    private TableView<DetallePago> tblDetalle;
    @FXML
    private TableColumn<DetallePago, String> columnCodigo;
    @FXML
    private TableColumn<DetallePago, String> columnDescripcion;
    @FXML
    private TableColumn<DetallePago, Double> columnPrecio;
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
    private JFXToggleButton togglePropietario;
    @FXML
    private Label lblPagarHasta;

    @FXML
    private JFXButton btnAdd;
    //   private Socios socio;
    private int idSocio;

    private Socios tmp;
    private PopSocios prueba;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTable();

        txtNombreSocio.setEditable(false);
        txtCui.setEditable(false);
        prueba = new PopSocios();
        prueba.setSocio(tmp);
       
        tmp = prueba.popOverMancomunado( txtCodigoSocio);

        txtCodigoSocio.setOnAction(e -> {
            System.out.println("Nombre evento " + e.getEventType().getName());
            if (e.getEventType().getName().contains("ACTION")) {
                colocarDatosSocio();
            }
        });

    }

    public void initializeAttributes(Socios socio, Administradores admin) {
        this.socio = socio;
        this.admin = admin;
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();

        return emf.createEntityManager();
    }

    public void colocarDatosSocio() {
        System.out.println("colocar datos");
        txtNombreSocio.setText(tmp.getNombres());
        txtCui.setText(tmp.getDpi());

        //Query buscarUltimoPago = getEntityManager().createNamedQuery("PagosSocios.findByMesCancelado")
    }

    public void inicializarTable() {
        columnCodigo.setCellValueFactory(new PropertyValueFactory<DetallePago, String>("codigo"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<DetallePago, String>("descripcion"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<DetallePago, Double>("precio"));
    }
    

     @FXML
    private void btnBusccar(ActionEvent event) {

        this.socio = prueba.getSocio();
         
         txtNombreSocio.setText(prueba.getSocio().getNombres()+" "+prueba.getSocio().getApellidos());
         txtCui.setText(prueba.getSocio().getDpi());
         searchLatestPay();
         
    }
    private void searchLatestPay(){
        Query searchPay = getEntityManager().createNamedQuery("PagosSocios.findLatestPago");//.setParameter("sociosIdSocio", this.socio.getIdSocio());
        if (searchPay.getResultList().size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("EL socio que desea pagar no cuenta con un registro de pagos,\npor favor verificar ");
            alert.setTitle("Informacion");
            alert.setHeaderText("Verificar Socio");
        }else{
            
        }
    }
}
