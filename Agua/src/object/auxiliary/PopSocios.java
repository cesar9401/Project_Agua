/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.auxiliary;

import com.jfoenix.controls.JFXTextField;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import object.Socios;
import org.controlsfx.control.PopOver;

/**
 *
 * @author julio
 */
public class PopSocios {

    private int idSocio;
    private Socios socio;
    private ObservableList<ViewSocio> items;
    TableView<ViewSocio> tableView ;

    public PopSocios() {
        items = FXCollections.observableArrayList();
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public Socios getSocio() {
        return socio;
    }

    public void setSocio(Socios socio) {
        
        this.socio = socio;
    }

    public Socios popOverMancomunado( JFXTextField nodeWhereWillAdd) {

        tableView = new TableView<>();
        String codigoSearch = "";
        PopOver popOver = new PopOver();

        JFXTextField txtCodeSearch = new JFXTextField();
        txtCodeSearch.getStylesheets().add("/css/SearchByCode.css");
        txtCodeSearch.setPrefSize(15, 15);
        txtCodeSearch.setPromptText("Ingrese el codigo del Socio a Buscar");
        txtCodeSearch.setEditable(true);
        txtCodeSearch.setLabelFloat(true);

        txtCodeSearch.setVisible(true);
        ObservableList<ViewSocio> keyObservableList;
        txtCodeSearch.setOnKeyTyped(e -> {
            searchSocio(txtCodeSearch.getText() + e.getCharacter());
            //codigoSearch =codigoSearch + e.getCharacter();
            // items = searchSocio();

        });
        TableColumn<ViewSocio, String> forCodigo = new TableColumn<ViewSocio, String>("Codigo");
        TableColumn<ViewSocio, String> forSocio = new TableColumn<ViewSocio, String>("Nombre");

        forCodigo.setCellValueFactory(new PropertyValueFactory<ViewSocio, String>("codigo"));
        forSocio.setCellValueFactory(new PropertyValueFactory<ViewSocio, String>("nombre"));

        forCodigo.setPrefWidth(250);
        forSocio.setPrefWidth(250);

        tableView.setLayoutX(0);
        tableView.setLayoutY(40);
        tableView.getColumns().addAll(forCodigo, forSocio);

        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                this.idSocio = tableView.getSelectionModel().getSelectedItem().getIdSocio();
                nodeWhereWillAdd.setText("");
                nodeWhereWillAdd.setText(tableView.getSelectionModel().getSelectedItem().getCodigo());
                 itemSelected();
                popOver.hide();
                System.out.println("Id" + idSocio);
                System.out.println("---"+socio.getCodigo());

            }
        });

        AnchorPane anchorPane = new AnchorPane(txtCodeSearch, tableView);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.getChildren().add(txtCodeSearch);
        vBox.getChildren().add(tableView);

        popOver.setContentNode(vBox);
        nodeWhereWillAdd.setEffect(new Blend());

        nodeWhereWillAdd.setOnKeyPressed(e -> {
            System.out.println("Pop oVert visible");
            popOver.show(nodeWhereWillAdd);
        });
        
        if (socio!=null) {
            
        System.out.println(socio.getCodigo());
        }
        return socio;

    }

    public Socios searchSocio(String codigo) {

        System.out.println("Buscar codigo keyTyped");
        Query buscar = getEntityManager().createNamedQuery("Socios.findByCodeUseLike").setParameter("codigo", "%" + codigo + "%");
        System.out.println(buscar.getResultList().size());
        this.items = null;
        
        items = FXCollections.observableArrayList();
            for (Iterator<Socios> iterator = buscar.getResultList().iterator(); iterator.hasNext();) {
                Socios next = iterator.next();
                this.items.add(new ViewSocio(next.getIdSocio(), next.getCodigo(), next.getNombres()));
                tableView.setItems(items);
            }

        
        return null;
    }

    public Socios itemSelected() {
        Query searchSocio = getEntityManager().createNamedQuery("Socios.findByIdSocio").setParameter("idSocio", this.idSocio);
         return this.socio = (Socios) searchSocio.getResultList().get(0);
        

    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();

        return emf.createEntityManager();
    }

}


