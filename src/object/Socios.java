/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author julio
 */
@Entity
@Table(name = "socios")
@NamedQueries({
    @NamedQuery(name = "Socios.findAll", query = "SELECT s FROM Socios s"),
    @NamedQuery(name = "Socios.findByIdSocio", query = "SELECT s FROM Socios s WHERE s.idSocio = :idSocio"),
    @NamedQuery(name = "Socios.findByCodigo", query = "SELECT s FROM Socios s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Socios.findByNombres", query = "SELECT s FROM Socios s WHERE s.nombres = :nombres"),
    @NamedQuery(name = "Socios.findByApellidos", query = "SELECT s FROM Socios s WHERE s.apellidos = :apellidos"),
    @NamedQuery(name = "Socios.findByDpi", query = "SELECT s FROM Socios s WHERE s.dpi = :dpi"),
    @NamedQuery(name = "Socios.findByDireccion", query = "SELECT s FROM Socios s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Socios.findByFechaInicioPago", query = "SELECT s FROM Socios s WHERE s.fechaInicioPago = :fechaInicioPago"),
    @NamedQuery(name = "Socios.findByExonerado", query = "SELECT s FROM Socios s WHERE s.exonerado = :exonerado"),
//Nuestras consultas
    @NamedQuery(name = "Socios.paraElLogin", query = "SELECT s FROM administradores  INNER JOIN socios ON administradores.socios_id_socio = socios.id_socio WHERE socios.codigo = :codigo AND administradores.password = :password")
    })
public class Socios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_socio")
    private Integer idSocio;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "dpi")
    private String dpi;
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "fecha_inicio_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioPago;
    @Lob
    @Column(name = "fotografia")
    private byte[] fotografia;
    @Basic(optional = false)
    @Column(name = "exonerado")
    private boolean exonerado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sociosIdSocio")
    private Collection<Administradores> administradoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sociosIdSocio")
    private Collection<PagosMorosos> pagosMorososCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sociosIdSocio")
    private Collection<PagosSocios> pagosSociosCollection;
    @OneToMany(mappedBy = "sociosIdSocio")
    private Collection<Socios> sociosCollection;
    @JoinColumn(name = "socios_id_socio", referencedColumnName = "id_socio")
    @ManyToOne
    private Socios sociosIdSocio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sociosIdSocio")
    private Collection<SociosEventos> sociosEventosCollection;

    public Socios() {
    }

    public Socios(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public Socios(Integer idSocio, String codigo, String nombres, String apellidos, Date fechaInicioPago, boolean exonerado) {
        this.idSocio = idSocio;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaInicioPago = fechaInicioPago;
        this.exonerado = exonerado;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaInicioPago() {
        return fechaInicioPago;
    }

    public void setFechaInicioPago(Date fechaInicioPago) {
        this.fechaInicioPago = fechaInicioPago;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public boolean getExonerado() {
        return exonerado;
    }

    public void setExonerado(boolean exonerado) {
        this.exonerado = exonerado;
    }

    public Collection<Administradores> getAdministradoresCollection() {
        return administradoresCollection;
    }

    public void setAdministradoresCollection(Collection<Administradores> administradoresCollection) {
        this.administradoresCollection = administradoresCollection;
    }

    public Collection<PagosMorosos> getPagosMorososCollection() {
        return pagosMorososCollection;
    }

    public void setPagosMorososCollection(Collection<PagosMorosos> pagosMorososCollection) {
        this.pagosMorososCollection = pagosMorososCollection;
    }

    public Collection<PagosSocios> getPagosSociosCollection() {
        return pagosSociosCollection;
    }

    public void setPagosSociosCollection(Collection<PagosSocios> pagosSociosCollection) {
        this.pagosSociosCollection = pagosSociosCollection;
    }

    public Collection<Socios> getSociosCollection() {
        return sociosCollection;
    }

    public void setSociosCollection(Collection<Socios> sociosCollection) {
        this.sociosCollection = sociosCollection;
    }

    public Socios getSociosIdSocio() {
        return sociosIdSocio;
    }

    public void setSociosIdSocio(Socios sociosIdSocio) {
        this.sociosIdSocio = sociosIdSocio;
    }

    public Collection<SociosEventos> getSociosEventosCollection() {
        return sociosEventosCollection;
    }

    public void setSociosEventosCollection(Collection<SociosEventos> sociosEventosCollection) {
        this.sociosEventosCollection = sociosEventosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSocio != null ? idSocio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Socios)) {
            return false;
        }
        Socios other = (Socios) object;
        if ((this.idSocio == null && other.idSocio != null) || (this.idSocio != null && !this.idSocio.equals(other.idSocio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Socios[ idSocio=" + idSocio + " ]";
    }
    
}
