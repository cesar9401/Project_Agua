/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alerta;

import conexion.ConexionJPA;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.jpa.JpaEntityManager;

/**
 *
 * @author 
 */
public class Alerta {
    
    /** clase dedicada a lanzar las excepciones de acuerdo a al tipo de alerta que deberia ser lanzada al usuario
     * 
     * 
     type:
     * 1 : Confirmacion
     * 2 : Error
     * 3 : Information
     * 4 : None
     * 5 : Warning
     
     */
    
   public static void Alerta(int type, String Title, String HeaderTxt, String Contenttxt){
       if(type==1){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
       }else{
           if(type==2){
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
           }else{
               if(type==3){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
               }else{
                   if(type==4){
                       Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setTitle(Title);
                        alert.setHeaderText(HeaderTxt);
                        alert.setContentText(Contenttxt);
                        alert.showAndWait();
                   }else{
                       if(type==5){
                           Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle(Title);
                            alert.setHeaderText(HeaderTxt);
                            alert.setContentText(Contenttxt);
                            alert.showAndWait();
                       }
                   }
               }
               
           }
           
       }
   
   }
   
   public static void AlertConfirmation(String Title, String HeaderTxt, String Contenttxt){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
                
   }

     public static void AlertError(String Title, String HeaderTxt, String Contenttxt){
          Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   }
     
     public static void AlertInformation(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
     public static void AlertNone(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
    public static void AlertWarning(String Title, String HeaderTxt, String Contenttxt){
         Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Title);
                alert.setHeaderText(HeaderTxt);
                alert.setContentText(Contenttxt);
                alert.showAndWait();
   } 
    
     public int val;
     
     
     static public void AlertWithException(Exception ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Look, an Exception Dialog");
            alert.setContentText("Could not find file blabla.txt!");

           

            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
         }
     
     
     
     static public void CleanCache(){
         EntityManagerFactory emf= ConexionJPA.getInstancia().getEMF();
         EntityManager em=emf.createEntityManager();
         ((JpaEntityManager)em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
     }
    
}

