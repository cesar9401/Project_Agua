/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Socios;
import object.auxiliary.ViewSocio;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class ModifySocioController implements Initializable {

    @FXML
    private TableView<ViewSocio> tableSocio;
    @FXML
    private TableColumn<ViewSocio, String> colCode;
    @FXML
    private TableColumn<ViewSocio, String> colName;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton btnSelect;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtCui;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXToggleButton mancomunado;
    @FXML
    private JFXToggleButton isExonerated;
    @FXML
    private JFXButton btnSaveChange;
    @FXML
    private JFXCheckBox chkBox;
    @FXML
    private JFXDatePicker txtDate;

    private int idSocio;
    Socios aMdoficar;
    @FXML
    private ImageView imgSocio;
    @FXML
    private JFXCheckBox chkMancomunados;
    @FXML
    private JFXCheckBox chkPropietarios;
    @FXML
    private JFXCheckBox chkTodo;
    @FXML
    private JFXTextField txtIdSuperSocio;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colCode.setCellValueFactory(new PropertyValueFactory<ViewSocio,String>("codigo"));
        colName.setCellValueFactory(new PropertyValueFactory<ViewSocio,String>("nombre"));
        
        Query forGetSocios = getEntityManager().createNamedQuery("Socios.findAll");
        
        getAndShowAllSocios(forGetSocios.getResultList());
        eventTable();
        
        txtSearch.setOnAction(e -> {
            Query consulta;
            String buscar ;// = (txtSearch.getText().contains("*"))?txtSearch.getText().substring(1):txtSearch.getText();
            System.out.println(e.getEventType());
            if (txtSearch.getText().contains("*")) {
               buscar = txtSearch.getText().substring(1);
               consulta = getEntityManager().createNamedQuery("Socios.findByNombres").setParameter("nombres", buscar);
            }else
                consulta = getEntityManager().createNamedQuery("Socios.findByCodigo").setParameter("codigo", txtSearch.getText());
            
            getAndShowAllSocios(consulta.getResultList());
//            
//            if (e.getEventType().getName()) {
//                
//            }
        });
    }    

    @FXML
    private void btnSelectOnAction(ActionEvent event) {
        try {
            trasladarDatos(aMdoficar);
        } catch (IOException ex) {
            Logger.getLogger(ModifySocioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException np){
            System.out.println("No se encontro una Imagen");
        }
    }

    @FXML
    private void btnSaveChangeOnAction(ActionEvent event) {
        
    }
    @FXML
    private void changeImg(MouseEvent event) {
    }
    
    private EntityManager getEntityManager(){
         EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        
        return emf.createEntityManager();
    }
    
    private void getAndShowAllSocios(List forGetSocios ){
         
       
        
        
        
        ObservableList<ViewSocio> showDataSocio = FXCollections.observableArrayList();
        
        for (Iterator<Socios> iterator = forGetSocios.iterator(); iterator.hasNext();) {
            Socios next = iterator.next();
            showDataSocio.add(new ViewSocio(next.getIdSocio(), next.getCodigo(), next.getNombres()));
          //  System.out.println(next.getNombre());
         
            
        }
        tableSocio.setItems(showDataSocio);
        
    }
    public void eventTable(){
        tableSocio.setOnMouseClicked(e->{
            if (e.getClickCount()==2) {
                System.out.println("copiar item a los textField");
               // questionMom();
                    
                    idSocio = tableSocio.getSelectionModel().getSelectedItem().getIdSocio();
                    
                    Query getSocio = getEntityManager().createNamedQuery("Socios.findByIdSocio").setParameter("idSocio", idSocio);
                    aMdoficar =(Socios) getSocio.getResultList().get(0);
                try {
                    trasladarDatos(aMdoficar);
                } catch (IOException ex) {
                    Logger.getLogger(ModifySocioController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
       
    }
    public void trasladarDatos(Socios socio) throws IOException{
        
        String[] codigoSocio = socio.getCodigo().split("-");
        
        txtName.setText(socio.getNombres());
        txtLastName.setText(socio.getApellidos());
        txtCui.setText(socio.getDpi());
        txtCode.setText(codigoSocio[1]);
        //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        txtDate.setValue(socio.getFechaInicioPago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mancomunado.setSelected(codigoSocio[0].contains("B"));
        isExonerated.setSelected(socio.getExonerado());
        
      InputStream in = new ByteArrayInputStream(socio.getFotografia());
        javafx.scene.image.Image image = new javafx.scene.image.Image(in);
        imgSocio.setImage(image);
        
        // imgSocio.setImage(mostrar);
       
        
       
    }
    public void modifySocio(){
        
        aMdoficar.setNombres(txtName.getText());
        aMdoficar.setApellidos(txtLastName.getText());
        aMdoficar.setDireccion(txtDireccion.getText());
        aMdoficar.setDpi(txtCui.getText());
        aMdoficar.setFechaInicioPago(Date.valueOf(txtDate.getValue()));
        aMdoficar.setExonerado(isExonerated.isSelected());
        
        boolean socioMancomunado = mancomunado.isSelected();
        String codigoModificado = (mancomunado.isSelected())? "B":"A"; 
        
        codigoModificado = codigoModificado+"-"+txtCode.getText();
        
        if (aMdoficar.getCodigo().equals(codigoModificado)) {
            System.out.println("el codigo no fue modificado");
        }else{
            Query checkCodigo = getEntityManager().createNamedQuery("Socios.findByCodigo").setParameter("codigo", codigoModificado);
            if (checkCodigo.getResultList().size()>0) {
                Alerta.Alerta.AlertInformation("Informacion", "Codigo", "El Codigo ingresado ya existe");
            }else{
                aMdoficar.setCodigo(codigoModificado);
            }
        }
        
       
    }

    
    
}
