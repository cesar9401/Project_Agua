/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import object.Socio;

/**
 *
 * @author cesar31
 */
public class SocioDAO {
    
    private Conexion conexion = new Conexion();
    
    public Socio getAdministrador(String codigo, String password){
        Socio admin = null;
        try{
            conexion.conectar();
            String query = "SELECT * FROM administradores INNER JOIN socios ON administradores.socios_id_socio = socios.id_socio WHERE socios.codigo = ? AND administradores.password = ?";
            PreparedStatement getAdmin = conexion.conectar().prepareStatement(query);
            getAdmin.setString(1, codigo);
            getAdmin.setString(2, password);
            ResultSet r = getAdmin.executeQuery();
            
            if(r.next()){
                admin = new Socio(r.getInt("id_socio"), r.getString("codigo"), r.getString("nombres"), r.getString("apellidos"), r.getString("dpi"));
                
            }
            conexion.desconectar();
            
        }catch(SQLException ex){
        
        }
    
        return admin;
    }    
    
    public void setSocio(Socio tmp){
        
        try{
            conexion.conectar();
            String query = "INSERT INTO socios (codigo, nombres, apellidos, dpi, direccion, fecha_inicio_pago, exonerado, socios_id_socio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement setSocio = conexion.conectar().prepareStatement(query);
            setSocio.setString(1, tmp.getCodigo());
            setSocio.setString(2, tmp.getNombres());
            setSocio.setString(3, tmp.getApellidos());
            setSocio.setString(4, tmp.getDpi());
            setSocio.setString(5, tmp.getDireccion());
            setSocio.setDate(6, tmp.getFecha_inicio_pago());
            setSocio.setBoolean(7, tmp.isExonerado());
            setSocio.setInt(8, tmp.getId_socio());
            setSocio.executeUpdate();
            conexion.desconectar();
        }catch(SQLException ex){
        
        }
    }    
    
    
}
