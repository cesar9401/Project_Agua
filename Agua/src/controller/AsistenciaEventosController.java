/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import object.Eventos;

/**
 * FXML Controller class
 *
 * @author cesar31
 */
public class AsistenciaEventosController implements Initializable {
    
    private Eventos evt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //Metodo para recibir el objeto de tipo Eventos con la informacion del evento correspondiente
    public void initializeAttributes(Eventos evt){
        this.evt = evt;
    } 
}
