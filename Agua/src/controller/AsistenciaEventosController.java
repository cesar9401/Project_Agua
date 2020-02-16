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
import object.SociosEventos;
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
        createTables();
        setTableAsistentes();
        setTableInasistentes();
    }    
    
    //Metodo para recibir el objeto de tipo Eventos con la informacion del evento correspondiente
    public void initializeAttributes(Eventos evt){
        this.evt = evt;
    } 

    @FXML
    private void seleccionarAsistentes(MouseEvent event) {
        
    }

    @FXML
    private void seleccionarInasistentes(MouseEvent event) {
        
    }

    @FXML
    private void inasistentesAction(ActionEvent event) {
        
    }

    @FXML
    private void asistentesAction(ActionEvent event) {
        
    }

    @FXML
    private void confirmarAction(ActionEvent event) {
    }

    @FXML
    private void cancelarAction(ActionEvent event) {
        this.button_cancelar.getScene().getWindow().hide();
    }
    
    public void createTables(){
        //Tabla asistentes
        asistentes = FXCollections.observableArrayList();
        colCodigoA.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresA.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosA.setCellValueFactory(new PropertyValueFactory("apellidos"));
        
        //Tabla inasistentes
        inasistentes = FXCollections.observableArrayList();
        colCodigoI.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNombresI.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidosI.setCellValueFactory(new PropertyValueFactory("apellidos"));
    }
    
    public void setTableAsistentes(){
        asistentes.clear();
        List<ViewSocio> socios = getSociosAsistentes();
        
        if(!this.asistentes.containsAll(socios)){
            this.asistentes.addAll(socios);
            this.table_asistentes.setItems(asistentes);
        }
    }
    
    public void setTableInasistentes(){
        inasistentes.clear();
        List<ViewSocio> socios = getSociosInasistentes();
        System.out.println(socios.size());
        
        if(!this.inasistentes.containsAll(socios)){
            this.inasistentes.addAll(socios);
            this.table_inasistentes.setItems(inasistentes);
        }
    }
    
    public List<ViewSocio> getSociosAsistentes(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        
        Query getSocios = em.createQuery("SELECT s FROM Socios s LEFT JOIN s.sociosEventosCollection e ON s.idSocio = e.idSociosEventos WHERE s.estado=true AND e.idSociosEventos IS NULL");
        List<Socios> socios = null;
        List<ViewSocio> viewSocios = new ArrayList<>();
        try{
            socios = getSocios.getResultList();
        }catch(Exception ex){
        
        }
        
        for(Socios s: socios){
            ViewSocio view = new ViewSocio(s.getIdSocio(), s.getCodigo(), s.getNombres());
            view.setApellidos(s.getApellidos());
            
            viewSocios.add(view);
        }
        
        return viewSocios;
    }
    
    public List<ViewSocio> getSociosInasistentes(){
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();
        
        Query getSocios = em.createQuery("SELECT s FROM Socios s INNER JOIN s.sociosEventosCollection e ON s.idSocio = e.idSociosEventos WHERE s.estado=true");
        List<Socios> socios = null;
        List<ViewSocio> viewSocios = new ArrayList<>();
        try{
            socios = getSocios.getResultList();
        }catch(Exception e){
            
        }
        
        for(Socios s: socios){
            ViewSocio view = new ViewSocio(s.getIdSocio(), s.getCodigo(), s.getNombres());
            view.setApellidos(s.getApellidos());
            
            viewSocios.add(view);
        }
                
        return viewSocios;
    }
}
