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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pagos_socios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagosSocios.findAll", query = "SELECT p FROM PagosSocios p")
    , @NamedQuery(name = "PagosSocios.findByIdPagosSocios", query = "SELECT p FROM PagosSocios p WHERE p.idPagosSocios = :idPagosSocios")
    , @NamedQuery(name = "PagosSocios.findByMesCancelado", query = "SELECT p FROM PagosSocios p WHERE p.mesCancelado = :mesCancelado")
    , @NamedQuery(name = "PagosSocios.findByFechaPago", query = "SELECT p FROM PagosSocios p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "PagosSocios.findLatestPago", query = "select p from PagosSocios p  where p.sociosIdSocio.idSocio = :idSocio ORDER BY p.mesCancelado DESC ")
        //select * from pagos_socios p where p.socios_id_socio = 3 order by mes_cancelado asc limit 1
        //ORDER BY p.mesCancelado DESC
})
public class PagosSocios implements Serializable {

    @Column(name = "descripcion")
    private String descripcion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pagos_socios")
    private Integer idPagosSocios;
    @Basic(optional = false)
    @Column(name = "mes_cancelado")
    @Temporal(TemporalType.DATE)
    private Date mesCancelado;
    @Basic(optional = false)
    @Column(name = "fecha_pago")
    //@Temporal(TemporalType.DATE)
   // private Date fechaPago;
    @OneToMany(mappedBy = "pagosSociosIdPagosSocios")
    private Collection<Detalles> detallesCollection;
    @JoinColumn(name = "administradores_id_administrador", referencedColumnName = "id_administrador")
    @ManyToOne(optional = false)
    private Administradores administradoresIdAdministrador;
    @JoinColumn(name = "cuotas_id_cuotas", referencedColumnName = "id_cuotas")
    @ManyToOne
    private Cuotas cuotasIdCuotas;
    @JoinColumn(name = "socios_id_socio", referencedColumnName = "id_socio")
    @ManyToOne(optional = false)
    private Socios sociosIdSocio;

    public PagosSocios() {
    }

    public PagosSocios(Integer idPagosSocios) {
        this.idPagosSocios = idPagosSocios;
    }

    public PagosSocios(Integer idPagosSocios, Date mesCancelado, Date fechaPago) {
        this.idPagosSocios = idPagosSocios;
        this.mesCancelado = mesCancelado;
//        this.fechaPago = fechaPago;
    }

    public Integer getIdPagosSocios() {
        return idPagosSocios;
    }

    public void setIdPagosSocios(Integer idPagosSocios) {
        this.idPagosSocios = idPagosSocios;
    }

    public Date getMesCancelado() {
        return mesCancelado;
    }

    public void setMesCancelado(Date mesCancelado) {
        this.mesCancelado = mesCancelado;
    }

//    public Date getFechaPago() {
//        return fechaPago;
//    }
//
//    public void setFechaPago(Date fechaPago) {
//        this.fechaPago = fechaPago;
//    }

    @XmlTransient
    public Collection<Detalles> getDetallesCollection() {
        return detallesCollection;
    }

    public void setDetallesCollection(Collection<Detalles> detallesCollection) {
        this.detallesCollection = detallesCollection;
    }

    public Administradores getAdministradoresIdAdministrador() {
        return administradoresIdAdministrador;
    }

    public void setAdministradoresIdAdministrador(Administradores administradoresIdAdministrador) {
        this.administradoresIdAdministrador = administradoresIdAdministrador;
    }

    public Cuotas getCuotasIdCuotas() {
        return cuotasIdCuotas;
    }

    public void setCuotasIdCuotas(Cuotas cuotasIdCuotas) {
        this.cuotasIdCuotas = cuotasIdCuotas;
    }

    public Socios getSociosIdSocio() {
        return sociosIdSocio;
    }

    public void setSociosIdSocio(Socios sociosIdSocio) {
        this.sociosIdSocio = sociosIdSocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagosSocios != null ? idPagosSocios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagosSocios)) {
            return false;
        }
        PagosSocios other = (PagosSocios) object;
        if ((this.idPagosSocios == null && other.idPagosSocios != null) || (this.idPagosSocios != null && !this.idPagosSocios.equals(other.idPagosSocios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.PagosSocios[ idPagosSocios=" + idPagosSocios + " ]";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
