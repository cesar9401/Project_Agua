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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Administradores;
import object.Socios;
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


   // private Socios socio;
    private int idSocio;

    private Socios tmp;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnBusccar(ActionEvent event) {

        socio = null;        
       // togglePropietario.setVisible(false);
       // String codigo = (togglePropietario.isSelected())?"A-"+txtCodigoSocio.getText():"B-"+txtCodigoSocio.getText();            

        tmp = null;        
        
        String codigo = (togglePropietario.isSelected())?"A-"+txtCodigoSocio.getText():"B-"+txtCodigoSocio.getText();            

            
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        txtCodigoSocio.setOnAction(e->{
            
            ObservableList<ViewSocio> showDataSocio = FXCollections.observableArrayList();
        
        
 
        });

        
        
    }   

    
    
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
    }
    

    public Socios searchSocio(String codigo){
        
        Query buscar = getEntityManager().createNamedQuery("Socios.findByCodigo").setParameter("codigo", codigo);
        if (buscar.getResultList().size() > 0) {
         colocarDatosSocio();
        }
        return null;
        
    }
    
    private EntityManager getEntityManager(){
         EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        
        return emf.createEntityManager();
    }

    public void colocarDatosSocio(){
         txtNombreSocio.setText(socio.getNombres());
         txtCui.setText(socio.getDpi());
        
         //Query buscarUltimoPago = getEntityManager().createNamedQuery("PagosSocios.findByMesCancelado")
    }
     public void popOverMancomunado(ObservableList<ViewSocio> items){
       
        
         
        JFXTextField searchSocio = new JFXTextField();
        searchSocio.setPrefSize(200, 150);
        searchSocio.setPromptText("Ingrese el codigo del Socio a Buscar");
        searchSocio.setEditable(true);
        //searchSocio.setLabelFloat(true);
//        
//        searchSocio.setLayoutX(2);
//        searchSocio.setLayoutY(5);
        searchSocio.setVisible(true);
        
        
        TableColumn<ViewSocio,String> forCodigo = new TableColumn<ViewSocio, String>("Codigo");
        TableColumn<ViewSocio,String> forSocio = new TableColumn<ViewSocio, String>("Nombre");
        
          forCodigo.setCellValueFactory(new PropertyValueFactory<ViewSocio,String>("codigo"));
        forSocio.setCellValueFactory(new PropertyValueFactory<ViewSocio,String>("nombre"));
        
        
        forCodigo.setPrefWidth(250);
        forSocio.setPrefWidth(250);
        
        TableView<ViewSocio> tableView = new TableView<>(items);
        
        tableView.setLayoutX(0);
        tableView.setLayoutY(40);
        tableView.getColumns().addAll(forCodigo,forSocio);
        
        tableView.setOnMouseClicked(e ->{
            if (e.getClickCount() == 2) {
                this.idSocio=tableView.getSelectionModel().getSelectedItem().getIdSocio();
                txtCodigoSocio.setText("");
                txtCodigoSocio.setText(tableView.getSelectionModel().getSelectedItem().getCodigo());
                
                System.out.println("Id"+idSocio);
            }
        });
        
        AnchorPane anchorPane = new AnchorPane(searchSocio,tableView);
        
//        anchorPane.setClip(searchSocio);
//        anchorPane.setClip(tableView);
        
        PopOver popOver = new PopOver(anchorPane);
        txtCodigoSocio.setEffect(new Blend());
        
        
        txtCodigoSocio.setOnKeyPressed(e->{
            System.out.println("Pop oVert visible");
            popOver.show(txtCodigoSocio);
        });
        
        
    }
}
