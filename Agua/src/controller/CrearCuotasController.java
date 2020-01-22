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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    
    //----------------------> navBar y adminBar
    //Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;
    
    @FXML
    private AnchorPane navBar;
    @FXML
    private MenuButton admin_button;
    @FXML
    private MenuItem item_cerrarSesion;
    @FXML
    private AnchorPane adminBar;
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
    private MenuButton menu_btnSocios;
    @FXML
    private MenuButton menu_btnPagos;
    @FXML
    private MenuButton menu_btnCuotas;
    @FXML
    private MenuItem item_nuevoSocio;
    @FXML
    private MenuItem item_VerSocios;
    @FXML
    private MenuItem item_VerCuotas;
    @FXML
    private Label label_inicio;
    @FXML
    private MenuItem item_eventos;
    //---------------------- Aqui termina NavBar y adminBar    

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
            
        }else{
            Alerta.Alerta.AlertError("Error", "Accion no valida", "Debe seleccionar una cuota para poder editarla");
        }
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        Cuotas tmp = table_cuotas.getSelectionModel().getSelectedItem();
        if(tmp != null){
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            CuotasJpaController deleteCuota = new CuotasJpaController(emf);
            try {
                deleteCuota.destroy(tmp.getIdCuotas());
                Alerta.Alerta.AlertInformation("Informacion", "Cuota Eliminada", "Se ha eliminado la cuota satisfactoriamente");
                setTableCuotas();
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(CrearCuotasController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CrearCuotasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alerta.Alerta.AlertError("Error", "Accion no valida", "Debe seleccionar una cuota para poder eliminarla");
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
            
            Alerta.Alerta.AlertInformation("Informacion", "Nueva Cuota", "Almacenado Correctamente");
            txt_nombreCuota.setText("");
            txt_ValorCuota.setText("");
            
            //Agregar a la tabla
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
    
    
    //-----------Aqui empiezan metodos del navBar y adminBar
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
        codigoAdmin.setText(socio.getCodigo());
        nombreAdmin.setText(socio.getNombres());
        apellidosAdmin.setText(socio.getApellidos());
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

    //Metodo para cargar las vistas fxml
    @FXML
    private void handleItemAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) navBar.getScene().getWindow();
        Parent root = null;
        Object obj = event.getSource();
  
        if(obj == item_nuevoSocio){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearSocio.fxml"));
            root = loader.load();
            CrearSocioController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }else if(obj == item_VerSocios){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifySocio.fxml"));
            root = loader.load();
            ModifySocioController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }else if(obj == item_VerCuotas){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearCuotas.fxml"));
            root = loader.load();
            CrearCuotasController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }else if(obj == item_eventos){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CrearEventos.fxml"));
            root = loader.load();
            CrearEventosController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            MainViewController controller = loader.getController();
            controller.initializeAttributes(socio, admin);
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //-----------Aqui termina metodos del navBar y adminBar
}
