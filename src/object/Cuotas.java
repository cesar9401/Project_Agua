/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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

/**
 *
 * @author julio
 */
@Entity
@Table(name = "cuotas")
@NamedQueries({
    @NamedQuery(name = "Cuotas.findAll", query = "SELECT c FROM Cuotas c"),
    @NamedQuery(name = "Cuotas.findByIdCuotas", query = "SELECT c FROM Cuotas c WHERE c.idCuotas = :idCuotas"),
    @NamedQuery(name = "Cuotas.findByNombreCuota", query = "SELECT c FROM Cuotas c WHERE c.nombreCuota = :nombreCuota"),
    @NamedQuery(name = "Cuotas.findByValorCuota", query = "SELECT c FROM Cuotas c WHERE c.valorCuota = :valorCuota")})
public class Cuotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuotas")
    private Integer idCuotas;
    @Basic(optional = false)
    @Column(name = "nombre_cuota")
    private String nombreCuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "valor_cuota")
    private BigDecimal valorCuota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuotasIdCuotas")
    private Collection<PagosMorosos> pagosMorososCollection;
    @OneToMany(mappedBy = "cuotasIdCuotas")
    private Collection<PagosSocios> pagosSociosCollection;

    public Cuotas() {
    }

    public Cuotas(Integer idCuotas) {
        this.idCuotas = idCuotas;
    }

    public Cuotas(Integer idCuotas, String nombreCuota, BigDecimal valorCuota) {
        this.idCuotas = idCuotas;
        this.nombreCuota = nombreCuota;
        this.valorCuota = valorCuota;
    }

    public Integer getIdCuotas() {
        return idCuotas;
    }

    public void setIdCuotas(Integer idCuotas) {
        this.idCuotas = idCuotas;
    }

    public String getNombreCuota() {
        return nombreCuota;
    }

    public void setNombreCuota(String nombreCuota) {
        this.nombreCuota = nombreCuota;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuotas != null ? idCuotas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuotas)) {
            return false;
        }
        Cuotas other = (Cuotas) object;
        if ((this.idCuotas == null && other.idCuotas != null) || (this.idCuotas != null && !this.idCuotas.equals(other.idCuotas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Cuotas[ idCuotas=" + idCuotas + " ]";
    }
    
}
