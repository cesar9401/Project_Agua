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
@Table(name = "comprobantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobantes.findAll", query = "SELECT c FROM Comprobantes c")
    , @NamedQuery(name = "Comprobantes.findByIdComprobantes", query = "SELECT c FROM Comprobantes c WHERE c.idComprobantes = :idComprobantes")
    , @NamedQuery(name = "Comprobantes.findByNoComprobante", query = "SELECT c FROM Comprobantes c WHERE c.noComprobante = :noComprobante")
    , @NamedQuery(name = "Comprobantes.findByFechaComprobante", query = "SELECT c FROM Comprobantes c WHERE c.fechaComprobante = :fechaComprobante")})
public class Comprobantes implements Serializable {

    @Column(name = "descripcion")
    private String descripcion;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subTotal")
    private BigDecimal subTotal;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comprobantes")
    private Integer idComprobantes;
    @Column(name = "no_comprobante")
    private String noComprobante;
    @Column(name = "fecha_comprobante")
    @Temporal(TemporalType.DATE)
    private Date fechaComprobante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comprobantesIdComprobantes")
    private Collection<Detalles> detallesCollection;

    public Comprobantes() {
    }

    public Comprobantes(Integer idComprobantes) {
        this.idComprobantes = idComprobantes;
    }

    public Integer getIdComprobantes() {
        return idComprobantes;
    }

    public void setIdComprobantes(Integer idComprobantes) {
        this.idComprobantes = idComprobantes;
    }

    public String getNoComprobante() {
        return noComprobante;
    }

    public void setNoComprobante(String noComprobante) {
        this.noComprobante = noComprobante;
    }

    public Date getFechaComprobante() {
        return fechaComprobante;
    }

    public void setFechaComprobante(Date fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
    }

    @XmlTransient
    public Collection<Detalles> getDetallesCollection() {
        return detallesCollection;
    }

    public void setDetallesCollection(Collection<Detalles> detallesCollection) {
        this.detallesCollection = detallesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComprobantes != null ? idComprobantes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comprobantes)) {
            return false;
        }
        Comprobantes other = (Comprobantes) object;
        if ((this.idComprobantes == null && other.idComprobantes != null) || (this.idComprobantes != null && !this.idComprobantes.equals(other.idComprobantes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Comprobantes[ idComprobantes=" + idComprobantes + " ]";
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
