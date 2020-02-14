/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.apache.commons.io.IOUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.org.apache.bcel.internal.generic.PopInstruction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.SociosJpaController;
import object.Administradores;
import object.Socios;
import object.auxiliary.ViewSocio;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class CrearSocioController implements Initializable {
    
//Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;
    
    private int idSocio;
    private String codigosocio;
    
    @FXML
    private JFXButton Crear;
    @FXML
    private ImageView img;
    @FXML
    private JFXToggleButton isExonerated;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtCui;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXToggleButton mancomunado;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtCodePropietario;
    
    private boolean changeImg;
    private String pathImg;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Image imgUsr = new Image("/img/usr+.png");
        img.setImage(imgUsr);
        changeImg = false;
        txtCodePropietario.setVisible(false);
        
        txtCui.setOnKeyTyped(event -> validationOfNumber(event));
        txtCode.setOnKeyTyped(event -> validationOfNumber(event));
        
        mancomunado.setOnAction(e -> {
            
            txtCodePropietario.setVisible(mancomunado.isSelected());
            
        });
        
        Query forGetSocios = getEntityManager().createNamedQuery("Socios.findAll");
        
         ObservableList<ViewSocio> showDataSocio = FXCollections.observableArrayList();
        
        for (Iterator<Socios> iterator = forGetSocios.getResultList().iterator(); iterator.hasNext();) {
            Socios next = iterator.next();
            showDataSocio.add(new ViewSocio(next.getIdSocio(), next.getCodigo(), next.getNombres()));
          //  System.out.println(next.getNombre());
         
            
        }
        popOverMancomunado(showDataSocio);
        
    }   
   
    //Metodo para recibir los objetos de tipo Socios y Aministradores con informacion del admin logueado
    public void initializeAttributes(Socios socio, Administradores admin){
        this.socio = socio;
        this.admin = admin;
    }    

    @FXML
    private void crearSocio(ActionEvent event) {
        captureData();
        clearData();
    }
    
    @FXML
    private void loadImg(MouseEvent event) {
    
        FileChooser filechooser = new FileChooser();
        File selectFile = filechooser.showOpenDialog(null);
        
        if (selectFile != null) {
            if (selectFile.isFile() && 
                    (selectFile.getName().contains(".png") || selectFile.getName().contains(".jpg") || selectFile.getName().contains(".bmp") || selectFile.getName().contains(".gif"))) {
                    
                System.out.println("Path: "+ selectFile.getAbsolutePath());
                Image imgChange = new Image("file:"+selectFile.getAbsolutePath());
                img.setImage(imgChange);
                changeImg = true;
                pathImg = selectFile.getAbsolutePath();
                
            }else{
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setHeaderText("Archivo Invalido");
                info.setContentText("Debe seleccionar una cuota para poder eliminarla");
                info.show(); 
                
            }
        }
    }
    
    
    /**
     * verifica que rellene los campos obligatorios sin tener duplicados en la base de datos
     * si cumple con lo anterior guarda al nuevo socio
     */
    public void captureData(){
        
        Socios nuevo = new Socios();
        
      
        if (!txtCode.getText().isEmpty() && !txtLastName.getText().isEmpty() && !txtName.getText().isEmpty() &&  datePicker.getValue() != null) {
            
            nuevo.setNombres(txtName.getText());
            nuevo.setApellidos(txtLastName.getText());
            nuevo.setDpi(txtCui.getText());
            nuevo.setDireccion(txtDireccion.getText());
            nuevo.setFechaInicioPago(java.sql.Date.valueOf(datePicker.getValue()));
            
            //Estado Activo/Inactivo para los socios
            nuevo.setEstado(true);
            
            if (checkCode() !=  null) {
                nuevo.setCodigo(checkCode());
            }
            
            if (changeImg) {
            
                try {
                    
                    FileInputStream myStream = new FileInputStream(pathImg);
                    byte[] imageInBytes = IOUtils.toByteArray(myStream); 
                    nuevo.setFotografia(imageInBytes);
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CrearSocioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Alert info = new Alert(Alert.AlertType.ERROR);
                    info.setTitle("Error");
                    info.setHeaderText("Imagen");
                    info.setContentText("Ocurrio un error al intentar guardar la imagen");
                    info.show(); 

                    
                    
                    Logger.getLogger(CrearSocioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
            SociosJpaController saveSocio = new SociosJpaController(emf);
            saveSocio.create(nuevo);
            
            
            
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setHeaderText("Nuevo Socio");
                info.setContentText("Almacenado Correctamente");
                info.show(); 
                
        }else{
            Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setHeaderText("Informacion ");
                info.setContentText("Debe llenar los campos obligatorios");
                info.show(); 
                
            
        }        
    }
    public String checkCode(){
        
        
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        EntityManager em = emf.createEntityManager();

        Query consultaCodigo;
        String useThisCode;

            if (mancomunado.isSelected()) {
                useThisCode = "B-"+txtCode.getText();
                 consultaCodigo = em.createNamedQuery("Socios.findByCodigo").setParameter("codigo", "B-"+txtCode.getText());

            }else{
                useThisCode = "A-"+txtCode.getText();
                 consultaCodigo = em.createNamedQuery("Socios.findByCodigo").setParameter("codigo", "A-"+txtCode.getText());

            }
        if (consultaCodigo.getResultList().size()>0) {

            
            Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Informacion");
                info.setHeaderText("Codigo ");
                info.setContentText("El Codigo que Ingreso ya existe");
                info.show(); 
                
            
            
            txtCode.setText("");
            return null;

        }
        return useThisCode;
        

    }
    /**
     * metodo que sirve para que el usuario ingrese solo numeros
     * 
     * @param keyEvent 
     */
    public void validationOfNumber(KeyEvent keyEvent){
        try{
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();
            
            if(keyEvent.getSource().equals(txtCui) && txtCui.getText().length()==13){
                keyEvent.consume();
            }
        } catch (Exception ex){ }
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
                txtCodePropietario.setText("");
                txtCodePropietario.setText(tableView.getSelectionModel().getSelectedItem().getCodigo());
                
                System.out.println("Id"+idSocio);
            }
        });
        
        AnchorPane anchorPane = new AnchorPane(searchSocio,tableView);
        
//        anchorPane.setClip(searchSocio);
//        anchorPane.setClip(tableView);
        
        PopOver popOver = new PopOver(anchorPane);
        txtCodePropietario.setEffect(new Blend());
        
        
        txtCodePropietario.setOnKeyPressed(e->{
            System.out.println("Pop oVert visible");
            popOver.show(txtCodePropietario);
        });
        
        
    }
    private EntityManager getEntityManager(){
         EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        
        return emf.createEntityManager();
    }
    public void clearData(){
            
        txtCode.setText("");
        txtCodePropietario.setText("");
        txtCodePropietario.setVisible(false);
        txtCui.setText("");
        txtDireccion.setText("");
        txtLastName.setText("");
        txtName.setText("");
        
       mancomunado.setSelected(false);
       isExonerated.setSelected(false);
       changeImg = false;
       
       
        Image imgUsr = new Image("/img/usr+.png");
        img.setImage(imgUsr);
        datePicker.setValue(null);
    }
}
