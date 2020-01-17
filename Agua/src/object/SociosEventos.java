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
@Table(name = "socios_eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SociosEventos.findAll", query = "SELECT s FROM SociosEventos s")
    , @NamedQuery(name = "SociosEventos.findByIdSociosEventos", query = "SELECT s FROM SociosEventos s WHERE s.idSociosEventos = :idSociosEventos")
    , @NamedQuery(name = "SociosEventos.findByCancelado", query = "SELECT s FROM SociosEventos s WHERE s.cancelado = :cancelado")})
public class SociosEventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_socios_eventos")
    private Integer idSociosEventos;
    @Basic(optional = false)
    @Column(name = "cancelado")
    private boolean cancelado;
    @JoinColumn(name = "eventos_id_eventos", referencedColumnName = "id_eventos")
    @ManyToOne(optional = false)
    private Eventos eventosIdEventos;
    @JoinColumn(name = "socios_id_socio", referencedColumnName = "id_socio")
    @ManyToOne(optional = false)
    private Socios sociosIdSocio;

    public SociosEventos() {
    }

    public SociosEventos(Integer idSociosEventos) {
        this.idSociosEventos = idSociosEventos;
    }

    public SociosEventos(Integer idSociosEventos, boolean cancelado) {
        this.idSociosEventos = idSociosEventos;
        this.cancelado = cancelado;
    }

    public Integer getIdSociosEventos() {
        return idSociosEventos;
    }

    public void setIdSociosEventos(Integer idSociosEventos) {
        this.idSociosEventos = idSociosEventos;
    }

    public boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Eventos getEventosIdEventos() {
        return eventosIdEventos;
    }

    public void setEventosIdEventos(Eventos eventosIdEventos) {
        this.eventosIdEventos = eventosIdEventos;
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
        hash += (idSociosEventos != null ? idSociosEventos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SociosEventos)) {
            return false;
        }
        SociosEventos other = (SociosEventos) object;
        if ((this.idSociosEventos == null && other.idSociosEventos != null) || (this.idSociosEventos != null && !this.idSociosEventos.equals(other.idSociosEventos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.SociosEventos[ idSociosEventos=" + idSociosEventos + " ]";
    }
    
}
