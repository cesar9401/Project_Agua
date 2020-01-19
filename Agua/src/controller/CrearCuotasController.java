/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.CuotasJpaController;
import object.Cuotas;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class CrearCuotasController implements Initializable {
    
    private double precio = 0;
    private ObservableList<Cuotas> cuotas;
    
    @FXML
    private AnchorPane pane_cuotas;
    @FXML
    private Button button_nueva;
    @FXML
    private AnchorPane pane_nuevaCuota;
    @FXML
    private JFXTextField txt_nombreCuota;
    @FXML
    private JFXTextField txt_ValorCuota;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Label label_titulo;
    @FXML
    private TableView<Cuotas> table_cuotas;
    @FXML
    private Button btn_editar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private TableColumn colNo;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colValor;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pane_nuevaCuota.setVisible(false);
        setTableCuotas();
    }    

    @FXML
    private void nuevaCuotaAction(ActionEvent event) {
        if(!pane_nuevaCuota.isVisible()){
            pane_nuevaCuota.setVisible(true);
        }else{
            pane_nuevaCuota.setVisible(false);
        }
    }

    @FXML
    private void agregarCuotaAction(ActionEvent event) {
        setCuota();
    }
    
    public void setCuota(){
        Cuotas nueva = new Cuotas();
        
        if(!txt_nombreCuota.getText().isEmpty() && !txt_ValorCuota.getText().isEmpty()){
            nueva.setNombreCuota(txt_nombreCuota.getText());
            BigDecimal valor = BigDecimal.valueOf(precio);
            nueva.setValorCuota(valor);
            
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            CuotasJpaController saveCuota = new CuotasJpaController(emf);
            saveCuota.create(nueva);
            
            Alerta.Alerta.AlertInformation("Informacion", "Nueva Cuota", "Almacenado Correctamente");
            txt_nombreCuota.setText("");
            txt_ValorCuota.setText("");
            
            setTableCuotas();
        }else{
            Alerta.Alerta.AlertInformation("Faltan Datos", "Informacion", "Debe llenar los Campos obligatorios");
        }
    }
    
    public List<Cuotas> getCuotas(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        Query getCuotas = em.createNamedQuery("Cuotas.findAll");
        List<Cuotas> cts = null;
        try{
            cts = (List<Cuotas>) getCuotas.getResultList();
        }catch(Exception e){
        
        }    
        
        return cts;
    }
    
    public void setTableCuotas(){
        cuotas = FXCollections.observableArrayList();
        cuotas.clear();
        this.colNo.setCellValueFactory(new PropertyValueFactory("idCuotas"));
        this.colDescripcion.setCellValueFactory(new PropertyValueFactory("nombreCuota"));
        this.colValor.setCellValueFactory(new PropertyValueFactory("valorCuota"));
        List<Cuotas> cuotas_tmp = getCuotas();
        
        if(!this.cuotas.containsAll(cuotas_tmp)){
            this.cuotas.addAll(cuotas_tmp);
            this.table_cuotas.setItems(cuotas);
        }
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        if(pane_nuevaCuota.isVisible()){
            pane_nuevaCuota.setVisible(false);
        }
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
    private void editAction(ActionEvent event) {
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
    }
}
