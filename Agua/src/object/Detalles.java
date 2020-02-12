/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julio
 */
@Entity
@Table(name = "detalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalles.findAll", query = "SELECT d FROM Detalles d")
    , @NamedQuery(name = "Detalles.findByIdDetalles", query = "SELECT d FROM Detalles d WHERE d.idDetalles = :idDetalles")
    , @NamedQuery(name = "Detalles.findByDescripcion", query = "SELECT d FROM Detalles d WHERE d.descripcion = :descripcion")})
public class Detalles implements Serializable {

    @Column(name = "disponible")
    private Boolean disponible;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalles")
    private Integer idDetalles;
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinColumn(name = "comprobantes_id_comprobantes", referencedColumnName = "id_comprobantes")
    @ManyToOne(optional = false)
    private Comprobantes comprobantesIdComprobantes;
    @JoinColumn(name = "pagos_socios_id_pagos_socios", referencedColumnName = "id_pagos_socios")
    @ManyToOne
    private PagosSocios pagosSociosIdPagosSocios;

    public Detalles() {
    }

    public Detalles(Integer idDetalles) {
        this.idDetalles = idDetalles;
    }

    public Integer getIdDetalles() {
        return idDetalles;
    }

    public void setIdDetalles(Integer idDetalles) {
        this.idDetalles = idDetalles;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Comprobantes getComprobantesIdComprobantes() {
        return comprobantesIdComprobantes;
    }

    public void setComprobantesIdComprobantes(Comprobantes comprobantesIdComprobantes) {
        this.comprobantesIdComprobantes = comprobantesIdComprobantes;
    }

    public PagosSocios getPagosSociosIdPagosSocios() {
        return pagosSociosIdPagosSocios;
    }

    public void setPagosSociosIdPagosSocios(PagosSocios pagosSociosIdPagosSocios) {
        this.pagosSociosIdPagosSocios = pagosSociosIdPagosSocios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalles != null ? idDetalles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalles)) {
            return false;
        }
        Detalles other = (Detalles) object;
        if ((this.idDetalles == null && other.idDetalles != null) || (this.idDetalles != null && !this.idDetalles.equals(other.idDetalles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Detalles[ idDetalles=" + idDetalles + " ]";
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
}
