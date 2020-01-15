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

/**
 *
 * @author julio
 */
@Entity
@Table(name = "pagos_morosos")
@NamedQueries({
    @NamedQuery(name = "PagosMorosos.findAll", query = "SELECT p FROM PagosMorosos p"),
    @NamedQuery(name = "PagosMorosos.findByIdPagosMorosos", query = "SELECT p FROM PagosMorosos p WHERE p.idPagosMorosos = :idPagosMorosos"),
    @NamedQuery(name = "PagosMorosos.findByMesesAtrasados", query = "SELECT p FROM PagosMorosos p WHERE p.mesesAtrasados = :mesesAtrasados")})
public class PagosMorosos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pagos_morosos")
    private Integer idPagosMorosos;
    @Basic(optional = false)
    @Column(name = "meses_atrasados")
    private int mesesAtrasados;
    @JoinColumn(name = "cuotas_id_cuotas", referencedColumnName = "id_cuotas")
    @ManyToOne(optional = false)
    private Cuotas cuotasIdCuotas;
    @JoinColumn(name = "socios_id_socio", referencedColumnName = "id_socio")
    @ManyToOne(optional = false)
    private Socios sociosIdSocio;

    public PagosMorosos() {
    }

    public PagosMorosos(Integer idPagosMorosos) {
        this.idPagosMorosos = idPagosMorosos;
    }

    public PagosMorosos(Integer idPagosMorosos, int mesesAtrasados) {
        this.idPagosMorosos = idPagosMorosos;
        this.mesesAtrasados = mesesAtrasados;
    }

    public Integer getIdPagosMorosos() {
        return idPagosMorosos;
    }

    public void setIdPagosMorosos(Integer idPagosMorosos) {
        this.idPagosMorosos = idPagosMorosos;
    }

    public int getMesesAtrasados() {
        return mesesAtrasados;
    }

    public void setMesesAtrasados(int mesesAtrasados) {
        this.mesesAtrasados = mesesAtrasados;
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
        hash += (idPagosMorosos != null ? idPagosMorosos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagosMorosos)) {
            return false;
        }
        PagosMorosos other = (PagosMorosos) object;
        if ((this.idPagosMorosos == null && other.idPagosMorosos != null) || (this.idPagosMorosos != null && !this.idPagosMorosos.equals(other.idPagosMorosos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.PagosMorosos[ idPagosMorosos=" + idPagosMorosos + " ]";
    }
    
}
