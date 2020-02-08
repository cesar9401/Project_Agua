/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.CuotasJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.Administradores;
import object.Cuotas;
import object.Socios;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class CrearCuotasController implements Initializable {
    
    //Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;
    
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
    @FXML
    private ImageView image_moneda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pane_nuevaCuota.setVisible(false);
        Image coin = new Image("/img/moneda.png");
        image_moneda.setImage(coin);
        createTable();
        setTableCuotas();
        btn_editar.setDisable(true);
        btn_eliminar.setDisable(true);
    }

    //Metodo para recibir los objetos de tipo Socios y Aministradores con informacion del admin logueado
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
    }    

    //Boton Nueva Cuota, para mostrar AnchorPane con formulario para nuevas cuotas
    @FXML
    private void nuevaCuotaAction(ActionEvent event) {
        if(!pane_nuevaCuota.isVisible()){
            pane_nuevaCuota.setVisible(true);
        }else{
            pane_nuevaCuota.setVisible(false);
        }
    }

    //Boton Agregar
    @FXML
    private void agregarCuotaAction(ActionEvent event) {
        setCuota();
    }
    
    //Boton cancelar
    @FXML
    private void cancelarAction(ActionEvent event) {
        if(pane_nuevaCuota.isVisible()){
            pane_nuevaCuota.setVisible(false);
        }
    }

    //Validar que ingrese datos para cobros
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
        Cuotas tmp = table_cuotas.getSelectionModel().getSelectedItem();
        if(tmp != null){
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            EntityManager em = emf.createEntityManager();
            Query getCuota = em.createNamedQuery("Cuotas.findByIdCuotas").setParameter("idCuotas", tmp.getIdCuotas());
            Cuotas cta = null;
            try{
                cta = (Cuotas) getCuota.getResultList().get(0);
            }catch(Exception ex){
            
            }
            
            if(cta != null){
                try {
                    editCuota(cta);
                } catch (IOException ex) {
                    Logger.getLogger(CrearCuotasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setContentText("Cuota no disponible, intente más tarde");
                alertaError.show();
            }
        }else{
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setContentText("Debe seleccionar una cuota para poder editarla");
            alertaError.show();
        }
    }
    
    public void editCuota(Cuotas cta) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EditCuotas.fxml"));
        Parent root = loader.load();
        EditCuotasController controller = loader.getController();
        controller.initializeAttributes(cta);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Editar Cuota");
        stage.showAndWait();
        
        //Actualizar tabla
        setTableCuotas();
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        Cuotas tmp = table_cuotas.getSelectionModel().getSelectedItem();
        if(tmp != null){
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            CuotasJpaController deleteCuota = new CuotasJpaController(emf);
            try {
                deleteCuota.destroy(tmp.getIdCuotas());
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setContentText( "Se ha eliminado la cuota satisfactoriamente");
                info.show();
                
                //Actualizar tabla
                setTableCuotas();
                
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(CrearCuotasController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrearCuotasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setContentText("Debe seleccionar una cuota para poder eliminarla");
                info.show();                
        }

    }
    
    @FXML
    private void seleccionarAction(MouseEvent event) {  
        Cuotas tmp = table_cuotas.getSelectionModel().getSelectedItem();
        if(tmp != null){
            btn_editar.setDisable(false);
            btn_eliminar.setDisable(false);
        }
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
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setContentText("Couta Almacenada Correctamente");
            info.show(); 
            
            txt_nombreCuota.setText("");
            txt_ValorCuota.setText("");
            
            //Agregar a la tabla
            setTableCuotas();
        }else{
            Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setContentText("Debe llenar los campos obligatorios");
                info.show(); 
            
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
    
    public void createTable(){
        cuotas = FXCollections.observableArrayList();
        this.colNo.setCellValueFactory(new PropertyValueFactory("idCuotas"));
        this.colDescripcion.setCellValueFactory(new PropertyValueFactory("nombreCuota"));
        this.colValor.setCellValueFactory(new PropertyValueFactory("valorCuota"));
    }
    
    public void setTableCuotas(){
        cuotas.clear();
        List<Cuotas> cuotas_tmp = getCuotas();
        if(!this.cuotas.containsAll(cuotas_tmp)){
            this.cuotas.addAll(cuotas_tmp);
            this.table_cuotas.setItems(cuotas);
        }
    }
}
