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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
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
import model.EventosJpaController;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.Administradores;
import object.Eventos;
import object.Socios;
import object.auxiliary.ViewEventos;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class CrearEventosController implements Initializable {
    
    //Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;
    
    private double precio = 0;
    private ObservableList<ViewEventos> eventos;
    
    @FXML
    private Button button_nueva;
    @FXML
    private AnchorPane pane_nuevoEvento;
    @FXML
    private Button btn_agregar;
    @FXML
    private JFXTextField txt_ValorEvento;
    @FXML
    private JFXTextField txt_nombreEvento;
    @FXML
    private Button btn_cancelar;
    @FXML
    private ImageView image_evento;
    @FXML
    private DatePicker fecha;
    @FXML
    private Label label_titulo;
    @FXML
    private TableView<ViewEventos> table_eventos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colValor;
    @FXML
    private Button button_editar;
    @FXML
    private Button button_eliminar;
    @FXML
    private Button button_confirmar;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pane_nuevoEvento.setVisible(false);
        Image calendario = new Image("/img/calendario.png");
        image_evento.setImage(calendario);
        createTable();
        setTableEventos();
        button_editar.setDisable(true);
        button_eliminar.setDisable(true);
        button_confirmar.setDisable(true);
    }
    
    //Metodo para recibir los objetos de tipo Socios y Aministradores con informacion del admin logueado
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
    }

    //Boton nuevos eventos, para mostrar AnchorPane con formulario para nuevoss eventos
    @FXML
    private void nuevoEventoAction(ActionEvent event) {
        if(!pane_nuevoEvento.isVisible()){
            pane_nuevoEvento.setVisible(true);
        }else{
            pane_nuevoEvento.setVisible(false);
        }
    }

    @FXML
    private void agregarEventoAction(ActionEvent event) {
        setEvento();
    }

    @FXML
    private void validar(KeyEvent event) {
        try{
            precio = Double.parseDouble(txt_ValorEvento.getText());
        }catch(NumberFormatException ex){
            txt_ValorEvento.setText("");
        }
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        if(pane_nuevoEvento.isVisible()){
            pane_nuevoEvento.setVisible(false);
        }
    }

    public void setEvento(){
        Eventos nuevo = new Eventos();
        if(!txt_nombreEvento.getText().isEmpty() && fecha.getValue() != null && !txt_ValorEvento.getText().isEmpty()){
            nuevo.setNombre(txt_nombreEvento.getText());
            nuevo.setFecha(java.sql.Date.valueOf(fecha.getValue()));
            nuevo.setCuota(BigDecimal.valueOf(precio));
            nuevo.setEstadoEvento(true);
            
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            EventosJpaController saveEvento = new EventosJpaController(emf);
            saveEvento.create(nuevo);
            
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setHeaderText(" Nuevo Evento");
            info.setContentText("Almacenado Correctamente");
            info.show(); 
            
            txt_nombreEvento.setText("");
            txt_ValorEvento.setText("");
            fecha.setValue(null);
            
            //Agregar a la tabla
            setTableEventos();
        }else{
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setHeaderText(" Informacion");
            info.setContentText("Debe llenar los Campos obligatorios");
            info.show(); 
        }
    }
    
    public List<ViewEventos> getEventos(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        Query getEventos = em.createNamedQuery("Eventos.findAll");
        List<Eventos> evt = null;
        List<ViewEventos> viewEvt = new ArrayList<>();
        try{
            evt = getEventos.getResultList();
        }catch(Exception ex){
        
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        for(Eventos e: evt){
            
            //Comprueba si el evento esta activo para poder mostralo
            if(e.getEstadoEvento()){
                String fecha = format.format(e.getFecha());
                viewEvt.add(new ViewEventos(e.getIdEventos(), e.getNombre(), fecha, e.getCuota()));
            }
        }
        
        return viewEvt;
    }
    
    public void createTable(){
        eventos = FXCollections.observableArrayList();
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.colValor.setCellValueFactory(new PropertyValueFactory("cuota"));
    }
    
    public void setTableEventos(){
        eventos.clear();
        List<ViewEventos> evt = getEventos();
        if(!this.eventos.containsAll(evt)){
            this.eventos.addAll(evt);
            
            //Para poder agregar a la tabla
            this.table_eventos.setItems(eventos);
        }
    }
    
    @FXML
    private void seleccionarAction(MouseEvent event) {
        ViewEventos tmp = table_eventos.getSelectionModel().getSelectedItem();
        if(tmp != null){
            button_editar.setDisable(false);
            button_eliminar.setDisable(false);
            button_confirmar.setDisable(false);
        }
    }

    @FXML
    private void editAction(ActionEvent event) {
        ViewEventos tmp = table_eventos.getSelectionModel().getSelectedItem();
        if(tmp != null){
            Eventos forEdit = getEventoById(tmp);
            if(forEdit != null){
                try {
                    setEditEvento(forEdit);
                } catch (IOException ex) {
                    Logger.getLogger(CrearEventosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            Alert errorInfo = new Alert(Alert.AlertType.ERROR);
            errorInfo.setTitle("Error");
            errorInfo.setHeaderText(" Accion no Valida");
            errorInfo.setContentText("Debe seleccionar un evento para pode editar");
            errorInfo.show(); 
        }
    }
    
    public void setEditEvento(Eventos forEdit) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EditEventos.fxml"));
        Parent root = loader.load();
        EditEventosController controller = loader.getController();
        controller.initializeAttributes(forEdit);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Editar Evento");
        stage.showAndWait();
        
        //Actualizar tabla
        setTableEventos();
    }

    @FXML
    private void eliminarAction(ActionEvent event) {
        ViewEventos tmp = table_eventos.getSelectionModel().getSelectedItem();
        
        if(tmp != null){
            Eventos forDelete = getEventoById(tmp);
            if(forDelete != null){
                //Metodo para eliminar evento
                setDestroyEventos(forDelete);
            }else{
                
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setHeaderText(" Evento eliminado");
                info.setContentText("Evento eliminado Satisfactoriamente");
                info.show(); 
                
            }
        }else{
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setTitle("Error");
            info.setHeaderText(" Accion No Valida");
            info.setContentText("Debe Seleccionar un evento para poder eliminarlo");
            info.show(); 
            
        }
    }
    
    public void setDestroyEventos(Eventos forDelete){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EventosJpaController eventoJpa = new EventosJpaController(emf);
        try {
            eventoJpa.destroy(forDelete.getIdEventos());
            setTableEventos();
            
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Informacion");
            info.setHeaderText(" Evento Eliminado");
            info.setContentText("Se ha eliminado el evento satisfactoriamente");
            info.show(); 

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CrearEventosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CrearEventosController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public Eventos getEventoById(ViewEventos tmp){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        Query getEvento = em.createNamedQuery("Eventos.findByIdEventos").setParameter("idEventos", tmp.getId_eventos());
        Eventos evt = null;
        try{
            evt = (Eventos) getEvento.getResultList().get(0);
        }catch(Exception ex){
            
        }
        
        return evt;
    }

    @FXML
    private void confirmarAction(ActionEvent event) throws IOException {
        ViewEventos tmp = table_eventos.getSelectionModel().getSelectedItem();
        if(tmp != null){
            Eventos evt = getEventoById(tmp);
            if(evt != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AsistenciaEventos.fxml"));
                Parent root = loader.load();
                AsistenciaEventosController controller = loader.getController();
                controller.initializeAttributes(admin, evt);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Confirmar Asistencia - " + evt.getNombre());
                stage.show();
            }
        }else{
            Alert errorInfo = new Alert(Alert.AlertType.ERROR);
            errorInfo.setTitle("Error");
            errorInfo.setHeaderText(" Accion no Valida");
            errorInfo.setContentText("Debe seleccionar un evento para poder confirmar asistencia de los socios");
            errorInfo.show(); 
        }        
        
    }
}
