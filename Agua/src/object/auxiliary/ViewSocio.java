/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.auxiliary;

/**
 *
 * @author julio
 */
public class ViewSocio {
    
    private int idSocio;
    private String codigo;
    private String nombre;
    
    private String apellidos;
  
    public ViewSocio(int idSocio, String codigo, String nombre) {
        this.idSocio = idSocio;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }    
}
