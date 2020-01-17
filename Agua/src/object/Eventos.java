/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julio
 */
@Entity
@Table(name = "eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e")
    , @NamedQuery(name = "Eventos.findByIdEventos", query = "SELECT e FROM Eventos e WHERE e.idEventos = :idEventos")
    , @NamedQuery(name = "Eventos.findByNombre", query = "SELECT e FROM Eventos e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Eventos.findByFecha", query = "SELECT e FROM Eventos e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Eventos.findByCuota", query = "SELECT e FROM Eventos e WHERE e.cuota = :cuota")})
public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_eventos")
    private Integer idEventos;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "cuota")
    private BigDecimal cuota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventosIdEventos")
    private Collection<SociosEventos> sociosEventosCollection;

    public Eventos() {
    }

    public Eventos(Integer idEventos) {
        this.idEventos = idEventos;
    }

    public Eventos(Integer idEventos, String nombre, Date fecha, BigDecimal cuota) {
        this.idEventos = idEventos;
        this.nombre = nombre;
        this.fecha = fecha;
        this.cuota = cuota;
    }

    public Integer getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(Integer idEventos) {
        this.idEventos = idEventos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    @XmlTransient
    public Collection<SociosEventos> getSociosEventosCollection() {
        return sociosEventosCollection;
    }

    public void setSociosEventosCollection(Collection<SociosEventos> sociosEventosCollection) {
        this.sociosEventosCollection = sociosEventosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEventos != null ? idEventos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.idEventos == null && other.idEventos != null) || (this.idEventos != null && !this.idEventos.equals(other.idEventos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Eventos[ idEventos=" + idEventos + " ]";
    }
    
}
