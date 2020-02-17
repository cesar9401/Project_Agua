/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Eventos;
import object.Socios;
import object.auxiliary.ViewSocio;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class AsistenciaEventosController implements Initializable {
    
    private Eventos evt;
    private ObservableList<ViewSocio> asistentes;
    private ObservableList<ViewSocio> inasistentes;
    
    //Listado de socios asistentes e inasistentes
    private List<ViewSocio> socioEvt;
    private List<ViewSocio> socioNoEvt;
    
    @FXML
    private TableView<ViewSocio> table_asistentes;
    @FXML
    private TableColumn colCodigoA;
    @FXML
    private TableColumn colNombresA;
    @FXML
    private TableColumn colApellidosA;
    @FXML
    private TableView<ViewSocio> table_inasistentes;
    @FXML
    private TableColumn colNombresI;
    @FXML
    private TableColumn colApellidosI;
    @FXML
    private Label label_asistentes;
    @FXML
    private Label label_inasistentes;
    @FXML
    private Button button_inasistentes;
    @FXML
    private Button button_asistentes;
    @FXML
    private Button button_confirmar;
    @FXML
    private Button button_cancelar;
    @FXML
    private TableColumn colCodigoI;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        button_asistentes.setDisable(true);
        button_inasistentes.setDisable(true);
    }    
    
    //Metodo para recibir el objeto de tipo Eventos con la informacion del evento correspondiente
    public void initializeAttributes(Eventos evt){
        this.evt = evt;
        createTables();
        setTableAsistentes();
        setTableInasistentes();
    } 

    @FXML
    private void seleccionarAsistentes(MouseEvent event) {
        ViewSocio tmp = table_asistentes.getSelectionModel().getSelectedItem();
        if(tmp != null){
            button_inasistentes.setDisable(false);
            button_asistentes.setDisable(true);
        }else{
            button_inasistentes.setDisable(true);
        }
    }

    @FXML
    private void seleccionarInasistentes(MouseEvent event) {
        ViewSocio tmp = table_inasistentes.getSelectionModel().getSelectedItem();
        if(tmp != null){
            button_asistentes.setDisable(false);
            button_inasistentes.setDisable(true);
        }else{
            button_asistentes.setDisable(true);
        }
    }

    @FXML
    private void inasistentesAction(ActionEvent event) {
        setInasistentes();
    }

    @FXML
    private void asistentesAction(ActionEvent event) {
        setAsistentes();
    }

    @FXML
    private void confirmarAction(ActionEvent event) {
        setSociosEventos();
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        this.button_cancelar.getScene().getWindow().hide();
    }
    
    public void setInasistentes(){
        ViewSocio tmp = table_asistentes.getSelectionModel().getSelectedItem();
        if(tmp != null){
            inasistentes.add(tmp);
            asistentes.remove(tmp);
            
        }else{
            Alert errorInfo = new Alert(Alert.AlertType.ERROR);
            errorInfo.setTitle("Error");
            errorInfo.setHeaderText(" Accion no Valida");
            errorInfo.setContentText("Debe seleccionar un Socio");
            errorInfo.show(); 
        }
    }
    
    public void setAsistentes(){
        ViewSocio tmp = table_inasistentes.getSelectionModel().getSelectedItem();
        if(tmp != null){
            asistentes.add(tmp);
            inasistentes.remove(tmp);
        }else{
            Alert errorInfo = new Alert(Alert.AlertType.ERROR);
            errorInfo.setTitle("Error");
            errorInfo.setHeaderText(" Accion no Valida");
            errorInfo.setContentText("Debe seleccionar un Socio");
            errorInfo.show(); 
        }    
    }
    
    public void setSociosEventos(){
        //Acciones para ingresar/eliminar en la tabla socios_eventos
        
    }
    
    public void createTables(){
        //Tabla asistentes
        socioEvt = new ArrayList<>();
        asistentes = FXCollections.observableArrayList();
        colCodigoA.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresA.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosA.setCellValueFactory(new PropertyValueFactory("apellidos"));
        
        //Tabla inasistentes
        socioNoEvt = new ArrayList<>();
        inasistentes = FXCollections.observableArrayList();
        colCodigoI.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresI.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosI.setCellValueFactory(new PropertyValueFactory("apellidos"));
    }
    
    public void setTableAsistentes(){
        asistentes.clear();
        List<ViewSocio> socios = getSociosAsistentes();
        socioEvt.addAll(socios);
        
        if(!this.asistentes.containsAll(socios)){
            this.asistentes.addAll(socios);
            this.table_asistentes.setItems(asistentes);
        }
    }
    
    public void setTableInasistentes(){
        inasistentes.clear();
        List<ViewSocio> socios = getSociosInasistentes();
        socioNoEvt.addAll(socios);
        
        if(!this.inasistentes.containsAll(socios)){
            this.inasistentes.addAll(socios);
            this.table_inasistentes.setItems(inasistentes);
        }
    }
    
    public List<ViewSocio> getSociosAsistentes(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
                
        Query getSocios = em.createNativeQuery("SELECT * FROM socios LEFT JOIN (SELECT * FROM socios_eventos WHERE eventos_id_eventos = ?1) AS socio_evento ON id_socio = socio_evento.socios_id_socio WHERE estado = true AND socio_evento.socios_id_socio IS NULL", Socios.class);
        getSocios.setParameter(1, evt.getIdEventos());
        
        List<Socios> socios = new ArrayList<>();
        List<ViewSocio> viewSocios = new ArrayList<>();
        try{
            socios = getSocios.getResultList();
        }catch(Exception ex){
        
        }
        
        for(Socios s: socios){
            viewSocios.add(new ViewSocio(s.getIdSocio(), s.getCodigo(), s.getNombres(), s.getApellidos()));
        }
        
        em.getTransaction().commit();
        em.close();
        
        return viewSocios;
    }
    
    public List<ViewSocio> getSociosInasistentes(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query getSocios = em.createNativeQuery("SELECT * FROM socios INNER JOIN (SELECT * FROM socios_eventos WHERE eventos_id_eventos = ?1) AS socio_evento ON id_socio = socio_evento.socios_id_socio WHERE estado = true", Socios.class);
        getSocios.setParameter(1, evt.getIdEventos());
        
        List<Socios> socios = new ArrayList<>();
        List<ViewSocio> viewSocios = new ArrayList<>();
        try{
            socios = getSocios.getResultList();
        }catch(Exception e){
            
        }
        
        for(Socios s: socios){
            viewSocios.add(new ViewSocio(s.getIdSocio(), s.getCodigo(), s.getNombres(), s.getApellidos()));
        }
        
        em.getTransaction().commit();
        em.close();
        
        return viewSocios;
    }
}
