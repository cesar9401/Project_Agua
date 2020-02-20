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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import model.SociosEventosJpaController;
import model.exceptions.NonexistentEntityException;
import object.Administradores;
import object.Eventos;
import object.Socios;
import object.SociosEventos;
import object.auxiliary.ViewSocio;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class AsistenciaEventosController implements Initializable {

    private Administradores admin;
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
    public void initializeAttributes(Administradores admin, Eventos evt){
        this.admin = admin;
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
        setInasistentesTable();
    }

    @FXML
    private void asistentesAction(ActionEvent event) {
        setAsistentesTable();
    }

    @FXML
    private void confirmarAction(ActionEvent event) {
        setSociosEventos();
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        this.button_cancelar.getScene().getWindow().hide();
    }
    
    public void setInasistentesTable(){
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
    
    public void setAsistentesTable(){
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
        for(ViewSocio s: socioEvt){
            if(asistentes.contains(s)){
                asistentes.remove(s);
            }
        }
        
        for(ViewSocio s: socioNoEvt){
            if(inasistentes.contains(s)){
                inasistentes.remove(s);
            }
        }
        
        //Socios que estaban como inasistentes pero quedaron como asistentes
        if(asistentes.size() > 0){
            List<Socios> sociosAsistentes = getSocios(asistentes);
            setAsistentes(sociosAsistentes);
        }
        
        //Socios que estaban como asitentes pero quedaron como inasistentes
        if(inasistentes.size() > 0){
            List<Socios> sociosInasistentes = getSocios(inasistentes);
            setInasistentes(sociosInasistentes);
        }
        
        //Actualizar tablas
        setTableAsistentes();
        setTableInasistentes();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Informacion");
        info.setContentText("Se ha confirmado la asistencia de los socios");
        info.showAndWait(); 
        
        this.button_confirmar.getScene().getWindow().hide();
   }
    
    //Establece quienes asistieron al evento creado
    public void setAsistentes(List<Socios> socios){
        List<SociosEventos> tmp = new ArrayList<>();
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for(Socios s: socios){
            Query getSociosEvt = em.createQuery("SELECT ev FROM SociosEventos ev WHERE ev.sociosIdSocio = :sociosIdSocio AND ev.eventosIdEventos = :eventosIdEventos");
            getSociosEvt.setParameter("sociosIdSocio", s);
            getSociosEvt.setParameter("eventosIdEventos", evt);
            SociosEventos sEvt = (SociosEventos) getSociosEvt.getResultList().get(0);
            tmp.add(sEvt);
        }
        
        em.getTransaction().commit();
        em.close();
        
        //Elimina los SociosEventos de la BD, de quienes estaban inasistentes pero son asistentes
        deleteSociosEventosBD(tmp);
    }
    
    //Elimina los SociosEventos de la BD, de quienes estaban inasistentes pero son asistentes
    public void deleteSociosEventosBD(List<SociosEventos> sociosEvt){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        SociosEventosJpaController deleteSocioEvento = new SociosEventosJpaController(emf);
       
        for(SociosEventos ev: sociosEvt){
            System.out.println(ev.getIdSociosEventos());
            try {
                deleteSocioEvento.destroy(ev.getIdSociosEventos());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(AsistenciaEventosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        em.getTransaction().commit();
        em.close();
    }
    
    //Establece quienes no asistieron al evento creado
    public void setInasistentes(List<Socios> socios){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        SociosEventosJpaController nuevoSocioEvt = new SociosEventosJpaController(emf);
        
        for(Socios s: socios){
            SociosEventos sociosEvt = new SociosEventos();
            sociosEvt.setAdministradoresIdAdministrador(admin);
            sociosEvt.setCancelado(false);
            sociosEvt.setEventosIdEventos(evt);
            sociosEvt.setIdSociosEventos(evt.getIdEventos());
            sociosEvt.setSociosIdSocio(s);
            
            nuevoSocioEvt.create(sociosEvt);
        }
        
        em.getTransaction().commit();
        em.close();
    }
    
    public List<Socios> getSocios(List<ViewSocio> view){
        List<Socios> socios = new ArrayList<>();
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        for(ViewSocio s: view){
            Query getSocio = em.createNamedQuery("Socios.findByIdSocio").setParameter("idSocio", s.getIdSocio());
            Socios socio = (Socios) getSocio.getResultList().get(0);
            socios.add(socio);
        }
        
        em.getTransaction().commit();
        em.close();
        
        return socios;
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
