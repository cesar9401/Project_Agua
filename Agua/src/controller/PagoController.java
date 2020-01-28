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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class PagoController implements Initializable {

    @FXML
    private AnchorPane navBar;
    @FXML
    private MenuButton admin_button;
    @FXML
    private MenuItem item_cerrarSesion;
    @FXML
    private MenuButton menu_btnSocios;
    @FXML
    private MenuItem item_nuevoSocio;
    @FXML
    private MenuItem item_VerSocios;
    @FXML
    private MenuButton menu_btnPagos;
    @FXML
    private MenuItem item_eventos;
    @FXML
    private MenuButton menu_btnCuotas;
    @FXML
    private MenuItem item_VerCuotas;
    @FXML
    private Label label_inicio;
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
    private TableView<?> tableSocio;
    @FXML
    private TableColumn<?, ?> colCode;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton btnSelect;
    @FXML
    private ImageView imgSocio;
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
    private JFXDatePicker txtDate;
    @FXML
    private JFXToggleButton isExonerated;
    @FXML
    private JFXButton btnSaveChange;
    @FXML
    private JFXCheckBox chkBox;
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
    }    

    @FXML
    private void cerrarSesion(ActionEvent event) {
    }

    @FXML
    private void handleItemAction(ActionEvent event) {
    }

    @FXML
    private void handleItemActionMouse(MouseEvent event) {
    }

    @FXML
    private void btnSelectOnAction(ActionEvent event) {
    }

    @FXML
    private void changeImg(MouseEvent event) {
    }

    @FXML
    private void btnSaveChangeOnAction(ActionEvent event) {
    }
    
}
