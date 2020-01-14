/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.InputStream;
import java.sql.Date;

/**
 *
 * @author cesar31
 */
public class Socio {
    private int id_socio;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String dpi;
    private String direccion;
    private java.sql.Date fecha_inicio_pago;
    private InputStream imagen;
    private byte[] foto;
    private boolean exonerado;
    private int socios_id_socio;
    
    private String password;//Cuando el socio sea administrador

    public Socio(int id_socio, String codigo, String nombres, String apellidos, String dpi) {
        this.id_socio = id_socio;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dpi = dpi;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
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

    public Date getFecha_inicio_pago() {
        return fecha_inicio_pago;
    }

    public void setFecha_inicio_pago(Date fecha_inicio_pago) {
        this.fecha_inicio_pago = fecha_inicio_pago;
    }

    public InputStream getImagen() {
        return imagen;
    }

    public void setImagen(InputStream imagen) {
        this.imagen = imagen;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isExonerado() {
        return exonerado;
    }

    public void setExonerado(boolean exonerado) {
        this.exonerado = exonerado;
    }

    public int getSocios_id_socio() {
        return socios_id_socio;
    }

    public void setSocios_id_socio(int socios_id_socio) {
        this.socios_id_socio = socios_id_socio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
