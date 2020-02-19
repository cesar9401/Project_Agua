/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.ComprobantesJpaController;
import model.DetallesJpaController;
import model.GenerarReporte;
import model.PagosSociosJpaController;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import object.Administradores;
import object.Comprobantes;
import object.Cuotas;
import object.DTO.GenericoDTO;
import object.Detalles;
import object.PagosSocios;
import object.Socios;
import object.SociosEventos;
import object.auxiliary.DetallePago;
import object.auxiliary.Mes_ES;
import object.auxiliary.PopSocios;
import object.auxiliary.ViewSocio;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author julio
 */
public class PagosController implements Initializable {

    //Atributos del administrador que inicia sesion
    private Socios socio;
    private Administradores admin;

    @FXML
    private JFXTextField txtCodigoSocio;
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombreSocio;
    @FXML
    private JFXTextField txtCui;
    @FXML
    private JFXDatePicker dateUltimo;
    @FXML
    private JFXDatePicker dateActual;
    @FXML
    private JFXDatePicker datePagarHasta;
    @FXML
    private JFXTextField txtSancion;
    @FXML
    private JFXTextField txtSancionDeTrabajo;
    @FXML
    private JFXTextField txtTotalDeConstruccion;
    @FXML
    private JFXTextField txtTotalDeMora;
    @FXML
    private TableView<DetallePago> tblDetalle;
    @FXML
    private TableColumn<DetallePago, String> columnCodigo;
    @FXML
    private TableColumn<DetallePago, String> columnDescripcion;
    @FXML
    private TableColumn<DetallePago, Double> columnPrecio;
    @FXML
    private JFXButton btnVisualizarPago;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtNoRecibo;
    @FXML
    private JFXTextField txtTotalPago;
    @FXML
    private JFXButton btnEliminarFIla;
    private JFXToggleButton togglePropietario;
    @FXML
    private Label lblPagarHasta;
    @FXML
    private JFXComboBox<Integer> comboCantidad;

    @FXML
    private JFXButton btnAdd;
    //   private Socios socio;
    private int idSocio;

    private Socios tmp;
    private PopSocios popSocio;
    private LocalDate datePay;

    private BigDecimal mora;
    private BigDecimal sancionTrabajo;
    private BigDecimal sancionAsamblea;
    private BigDecimal mensualidad;
    private BigDecimal construccion;
    private LocalDate mesCancelado;
    @FXML
    private Label lblTotalMensualidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("ANio =" + LocalDate.now().getYear());
        inicializarTable();

        txtInit();
        this.comboCantidad.setVisible(false);
        datePagarHasta.setVisible(false);
        dateActual.setVisible(false);
        txtNombreSocio.setEditable(false);
        txtCui.setEditable(false);

        popSocio = new PopSocios();
        popSocio.setSocio(tmp);

        fillComboBox();
        tmp = popSocio.popOverMancomunado(txtCodigoSocio);

        txtCodigoSocio.setOnAction(e -> {
            System.out.println("Nombre evento " + e.getEventType().getName());
            if (e.getEventType().getName().contains("ACTION")) {
                colocarDatosSocio();
            }
        });
        eventoCombo();

    }

    public void initializeAttributes(Socios socio, Administradores admin) {
        this.socio = socio;
        this.admin = admin;
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();

        return emf.createEntityManager();
    }

    public void colocarDatosSocio() {
        System.out.println("colocar datos");
        txtNombreSocio.setText(tmp.getNombres());
        txtCui.setText(tmp.getDpi());

        //Query buscarUltimoPago = getEntityManager().createNamedQuery("PagosSocios.findByMesCancelado")
    }

    public void inicializarTable() {
        columnCodigo.setCellValueFactory(new PropertyValueFactory<DetallePago, String>("codigo"));
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<DetallePago, String>("descripcion"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<DetallePago, Double>("precio"));
    }

    @FXML
    private void btnBusccar(ActionEvent event) {

        this.tmp = popSocio.getSocio();
        this.comboCantidad.setVisible(true);
        this.lblTotalMensualidad.setVisible(true);
        this.lblTotalMensualidad.setText("Q. 35.00");
        txtNombreSocio.setText(popSocio.getSocio().getNombres() + " " + popSocio.getSocio().getApellidos());
        txtCui.setText(popSocio.getSocio().getDpi());
        dateActual.setValue(LocalDate.now());
        searchLatestPay();

    }

    private void searchLatestPay() {

        Query searchPay = getEntityManager().createNamedQuery("PagosSocios.findLatestPago").setParameter("idSocio", this.tmp.getIdSocio());
        searchPay.setMaxResults(1);

        if (searchPay.getResultList().size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("EL socio que desea pagar no cuenta con un registro de pagos,\npor favor verificar ");
            alert.setTitle("Informacion");
            alert.setHeaderText("Verificar Socio");
            alert.show();
        } else {
            PagosSocios nuevo = (PagosSocios) searchPay.getResultList().get(0);

            java.sql.Date date = new java.sql.Date(nuevo.getMesCancelado().getTime());
            dateUltimo.setValue(date.toLocalDate());

            LocalDate actual = LocalDate.now();
            int yearLast = dateUltimo.getValue().getYear();
            int cantMeses = 0;
            int mesesAtrasados = 0;
            if (yearLast < actual.getYear()) {
                cantMeses = (actual.getYear() - yearLast) * 12;
            }
            if (dateUltimo.getValue().getMonthValue() < actual.getMonthValue()) {
                cantMeses += ((actual.getMonthValue() - dateUltimo.getValue().getMonthValue()) - 1);
            }

            BigDecimal moraFinal = new BigDecimal(String.valueOf(cantMeses));

            moraFinal = moraFinal.multiply(this.mora);
            txtTotalDeMora.setText(String.valueOf(moraFinal));

        }
    }

    @FXML
    private void btnRegistrarAction(ActionEvent event) {
        if (txtNoRecibo.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ingrese el Numero de Recibo");
            alert.setTitle("Informacion");
            alert.setHeaderText("Numero De Recibo");
            alert.show();
        } else {
            createPay();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Se Almaceno el Registro Correctamente");
            alert.setTitle("Informacion");
            alert.setHeaderText("Regitro");
            alert.show();
            tmp = null;
            cleanField();

        }

    }
    private void cleanField(){
        
        txtCodigoSocio.setText("");
        txtCui.setText("");
        txtNombreSocio.setText("");
        txtNoRecibo.setText("");
        txtSancion.setText("");
        txtSancionDeTrabajo.setText("");
        txtTotalDeConstruccion.setText("");
        txtTotalDeMora.setText("");
        txtTotalPago.setText("");
        dateUltimo.setValue(null);
        lblTotalMensualidad.setText("");
        lblTotalMensualidad.setVisible(false);
        comboCantidad.getSelectionModel().select(0);
        comboCantidad.setVisible(false);
    }

    private void createPay() {

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());

        PagosSocios pagoSocio = new PagosSocios();

//        pagoSocio.setAdministradoresIdAdministrador(admin);
//        pagoSocio.setFechaPago(date);
//        pagoSocio.setSociosIdSocio(tmp);
//        pagoSocio.setMesCancelado(Date.valueOf(dateUltimo.getValue().plusMonths(comboCantidad.getValue())));
//        pagoSocio.setDescripcion("Mensualidad");
//        
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        PagosSociosJpaController savePay = new PagosSociosJpaController(emf);
        ComprobantesJpaController saveComprobante = new ComprobantesJpaController(emf);
        DetallesJpaController saveDetail = new DetallesJpaController(emf);
        Query deCoutas;
        Detalles deComprobante;

        Comprobantes comprobante = new Comprobantes();
        comprobante.setEstado(true);
        comprobante.setFechaComprobante(Date.valueOf(LocalDate.now()));
        comprobante.setNoComprobante(txtNoRecibo.getText());
        double total = Double.parseDouble(txtTotalPago.getText());

        comprobante.setSubTotal(BigDecimal.valueOf(total));
        saveComprobante.create(comprobante);

        

        for (DetallePago item : tblDetalle.getItems()) {
            if (item.getDescripcion().contains("Cancela")) {

                for (int i = 1; i <= comboCantidad.getValue(); i++) {

                    deCoutas = getEntityManager().createNamedQuery("Cuotas.findByNombreCuota").setParameter("nombreCuota", "Mensualidad");
                    Cuotas cuotaMensual = (Cuotas) deCoutas.getResultList().get(0);

                    pagoSocio.setMesCancelado(Date.valueOf(dateUltimo.getValue().plusMonths(i)));
                    pagoSocio.setFechaPago(date);
                    pagoSocio.setSociosIdSocio(tmp);
                    pagoSocio.setCuotasIdCuotas(cuotaMensual);
                    pagoSocio.setDescripcion("Mensualidad");
                    pagoSocio.setAdministradoresIdAdministrador(admin);

                    savePay.create(pagoSocio);

                    deComprobante = new Detalles();
                    deComprobante.setComprobantesIdComprobantes(comprobante);
                    deComprobante.setDisponible(true);
                    deComprobante.setPagosSociosIdPagosSocios(pagoSocio);
                    deComprobante.setSubTotal(BigDecimal.valueOf(item.getPrecio()));
                    deComprobante.setDescripcion("Mes de: " + dateUltimo.getValue().plusMonths(i));

                    saveDetail.create(deComprobante);
                    

                }

            } else if (item.getDescripcion().contains("Mora")) {
                if (!(item.getPrecio() == 0.0)) {

                    saveData(comprobante, item.getPrecio(), "Mora");

                }
            } else if (item.getDescripcion().contains("Trabajo")) {
                if (item.getPrecio()!= 0) {
                    saveData(comprobante, item.getPrecio()  ,"Sancion De Trabajo" );
                }
                
            } else if (item.getDescripcion().contains("Construccion")) {
               if (item.getPrecio()!= 0) {
                    saveData(comprobante, item.getPrecio()  ,"Construccion" );
                }
                

            } else if (item.getDescripcion().contains("Sesion")) {

                if (item.getPrecio()!= 0) {
                    saveData(comprobante, item.getPrecio()  ,"Sancion De Sesion" );
                }
                
            }
        }

    }
    public void saveData(Comprobantes comprobante, double precio, String nameCouta){
        
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());

        PagosSocios pagoSocio = new PagosSocios();

//        pagoSocio.setAdministradoresIdAdministrador(admin);
//        pagoSocio.setFechaPago(date);
//        pagoSocio.setSociosIdSocio(tmp);
//        pagoSocio.setMesCancelado(Date.valueOf(dateUltimo.getValue().plusMonths(comboCantidad.getValue())));
//        pagoSocio.setDescripcion("Mensualidad");
//        
        EntityManagerFactory emf = conexion.ConexionJPA.getInstancia().getEMF();
        PagosSociosJpaController savePay = new PagosSociosJpaController(emf);
        ComprobantesJpaController saveComprobante = new ComprobantesJpaController(emf);
        DetallesJpaController saveDetail = new DetallesJpaController(emf);
        Query deCoutas;
        Detalles deComprobante;

        
                    deCoutas = getEntityManager().createNamedQuery("Cuotas.findByNombreCuota").setParameter("nombreCuota", nameCouta);
                    Cuotas cuotaMensual = (Cuotas) deCoutas.getResultList().get(0);

                    pagoSocio = new PagosSocios();

                    //pagoSocio.setMesCancelado());
                    pagoSocio.setFechaPago(date);
                    pagoSocio.setSociosIdSocio(tmp);
                    pagoSocio.setCuotasIdCuotas(cuotaMensual);
                    pagoSocio.setDescripcion(nameCouta);
                    pagoSocio.setAdministradoresIdAdministrador(admin);

                    savePay.create(pagoSocio);

                    deComprobante = new Detalles();
                    deComprobante = new Detalles();
                    deComprobante.setComprobantesIdComprobantes(comprobante);
                    deComprobante.setDisponible(true);
                    deComprobante.setPagosSociosIdPagosSocios(pagoSocio);
                    deComprobante.setSubTotal(BigDecimal.valueOf(precio));
                    deComprobante.setDescripcion(nameCouta);
                    
                    saveDetail.create(deComprobante);
    }

    
    @FXML
    private void btnDeleteAction(ActionEvent event) {

    }

    @FXML
    private void btnVisualizarAction(ActionEvent event) {

        //     System.out.println((dateUltimo.getValue()<datePagarHasta.getValue()));
        if (dateUltimo.getValue() != null) {
            datePay = dateUltimo.getValue().plusMonths(comboCantidad.getValue());
        } else {

            dateActual.setValue(LocalDate.now());
            dateUltimo.setValue(dateActual.getValue());
            datePay = dateActual.getValue().plusMonths(comboCantidad.getValue());
        }
        String strMes = lblTotalMensualidad.getText().replace("Q. ", "");
        double totalDeMes = Double.parseDouble(strMes);

        DetallePago mensualidad = new DetallePago(tmp.getCodigo(), "Cancela " + comboCantidad.getValue() + " del " + dateUltimo.getValue() + " al    " + datePay.toString(), totalDeMes);

        ObservableList<DetallePago> detail = FXCollections.observableArrayList();

        double total = Double.parseDouble(txtSancion.getText()) + Double.parseDouble(txtTotalDeConstruccion.getText()) + Double.parseDouble(txtTotalDeConstruccion.getText()) + Double.parseDouble(txtTotalDeMora.getText()) + totalDeMes;
        txtTotalPago.setText("" + total);
        detail.add(new DetallePago(tmp.getCodigo(), "Sancion de Sesion", Double.parseDouble(txtSancion.getText())));
        detail.add(new DetallePago(tmp.getCodigo(), "Por Construccion ", Double.parseDouble(txtTotalDeConstruccion.getText())));
        detail.add(new DetallePago(tmp.getCodigo(), "Sancion de Trabajo", Double.parseDouble(txtSancion.getText())));
        detail.add(new DetallePago(tmp.getCodigo(), "Mora", Double.parseDouble(txtTotalDeMora.getText())));

        detail.add(mensualidad);
        tblDetalle.setItems(detail);
    }

    public void fillComboBox() {
        ObservableList<Integer> meses = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            meses.add(new Integer(i));
        }

        comboCantidad.setItems(meses);
        comboCantidad.getSelectionModel().selectFirst();
    }

    public BigDecimal searchSancion(String nombre) {
        Query couta = getEntityManager().createNamedQuery("Cuotas.findByNombreCuota").setParameter("nombreCuota", nombre);
        couta.setMaxResults(1);
        return ((Cuotas) couta.getResultList().get(0)).getValorCuota();

    }

    public void asignarValores() {
        mora = searchSancion("Mora");

        sancionTrabajo = searchSancion("Sancion De Trabajo");
        sancionAsamblea = searchSancion("Sancion De Sesion");
        construccion = searchSancion("Construccion");

        mensualidad = searchSancion("Mensualidad");
    }

    public void eventosNoCancelados() {
        Query multas = getEntityManager().createNamedQuery("SociosEventos.findByIdSociosEventos").setParameter("idSociosEventos", tmp.getIdSocio());
        List<SociosEventos> noAsistio = multas.getResultList();
        if (multas.getResultList().size() == 0) {
            System.out.println("Sin Sanciones ");
        } else {
            for (int i = 0; i < multas.getResultList().size(); i++) {

                if (noAsistio.get(i).getEventosIdEventos().getNombre().equalsIgnoreCase("Sancion De Sesion")) {
                    BigDecimal sesion = BigDecimal.valueOf(Double.parseDouble(txtSancion.getText()));
                    sesion = sesion.add(sancionAsamblea);
                    txtSancion.setText(sesion.toString());

                } else if (noAsistio.get(i).getEventosIdEventos().getNombre().equalsIgnoreCase("Sancion De Trabajo")) {
                    BigDecimal sesion = BigDecimal.valueOf(Double.parseDouble(txtSancionDeTrabajo.getText()));
                    sesion = sesion.add(sancionTrabajo);
                    txtSancionDeTrabajo.setText(sesion.toString());
                } else if (noAsistio.get(i).getEventosIdEventos().getNombre().equalsIgnoreCase("Construccion")) {
                    BigDecimal sesion = BigDecimal.valueOf(Double.parseDouble(txtTotalDeConstruccion.getText()));
                    sesion = sesion.add(construccion);
                    txtTotalDeConstruccion.setText(sesion.toString());
                }

            }
        }
    }

    public void eventoCombo() {

        asignarValores();
        comboCantidad.setOnAction((e) -> {

            double mes = mensualidad.doubleValue();
            double total = mes * comboCantidad.getValue();

            lblTotalMensualidad.setText("Q. " + total);
            lblTotalMensualidad.setVisible(true);
            //txt

        });
    }

    public void txtInit() {
        txtSancionDeTrabajo.setText("0.0");
        txtSancion.setText("0.0");
        txtSancionDeTrabajo.setText("0.0");
        txtTotalDeMora.setText("0.0");
        txtTotalDeConstruccion.setText("0.0");
    }

    public void testReporte() {

        GenerarReporte nuevoReporte = new GenerarReporte();
        HashMap parametros = new HashMap<String, Object>();
        parametros.put("titulo", "Mancomunados");

        parametros.put("columnUno", "Codigo");
        parametros.put("columnDos", "nombre");
        parametros.put("columnTres", "Apellido");
        parametros.put("columnCuatro", "Cui");
        parametros.put("columnCinco", "Tipo");
        parametros.put("columnSeis", "Telefono");

        Query socios = getEntityManager().createNamedQuery("Socios.findAll");
        List<GenericoDTO> listado = new ArrayList<>();

        for (Iterator<Socios> iterator = socios.getResultList().iterator(); iterator.hasNext();) {

            Socios temp = iterator.next();
            System.out.println("Socio" + temp.getCodigo());
            if (temp.getCodigo().contains("A")) {
                listado.add(new GenericoDTO(temp.getCodigo(), temp.getNombres(), temp.getApellidos(), temp.getDpi(), "Propietario", "telefono"));
            } else {
                listado.add(new GenericoDTO(temp.getCodigo(), temp.getNombres(), temp.getApellidos(), temp.getDpi(), "Mancomunado", "Telefono"));
            }

        }

        nuevoReporte.reportWithParameter("/report/Mancomunados.jrxml", parametros, listado);

    }
}
