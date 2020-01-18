/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julio
 */
@Entity
@Table(name = "administradores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administradores.findAll", query = "SELECT a FROM Administradores a")
    , @NamedQuery(name = "Administradores.findByIdAdministrador", query = "SELECT a FROM Administradores a WHERE a.idAdministrador = :idAdministrador")
    , @NamedQuery(name = "Administradores.findByPassword", query = "SELECT a FROM Administradores a WHERE a.password = :password")
        
    //Nuestras consultas
        
    , @NamedQuery(name = "Administradores.findBySociosIdSocio", query = "SELECT a FROM Administradores a WHERE a.sociosIdSocio = :sociosIdSocio")})

public class Administradores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_administrador")
    private Integer idAdministrador;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "socios_id_socio", referencedColumnName = "id_socio")
    @ManyToOne(optional = false)
    private Socios sociosIdSocio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administradoresIdAdministrador")
    private Collection<PagosSocios> pagosSociosCollection;

    public Administradores() {
    }

    public Administradores(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Administradores(Integer idAdministrador, String password) {
        this.idAdministrador = idAdministrador;
        this.password = password;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Socios getSociosIdSocio() {
        return sociosIdSocio;
    }

    public void setSociosIdSocio(Socios sociosIdSocio) {
        this.sociosIdSocio = sociosIdSocio;
    }

    @XmlTransient
    public Collection<PagosSocios> getPagosSociosCollection() {
        return pagosSociosCollection;
    }

    public void setPagosSociosCollection(Collection<PagosSocios> pagosSociosCollection) {
        this.pagosSociosCollection = pagosSociosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdministrador != null ? idAdministrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administradores)) {
            return false;
        }
        Administradores other = (Administradores) object;
        if ((this.idAdministrador == null && other.idAdministrador != null) || (this.idAdministrador != null && !this.idAdministrador.equals(other.idAdministrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "object.Administradores[ idAdministrador=" + idAdministrador + " ]";
    }
    
}
