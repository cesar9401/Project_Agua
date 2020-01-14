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

    public Socio getSocio(String codigo){
        Socio socio = null;
        try{
            conexion.conectar();
            String query = "SELECT * FROM socios WHERE codigo = ?";
            PreparedStatement obtenerSocio = conexion.conectar().prepareStatement(query);
            obtenerSocio.setString(1, codigo);
            ResultSet r = obtenerSocio.executeQuery();
            if(r.next()){
                socio = new Socio(r.getInt("id_socio"), r.getString("codigo"), r.getString("nombres"), r.getString("apellidos"), r.getString("dpi"));
                socio.setDireccion(r.getString("direccion"));
                socio.setFecha_inicio_pago(r.getDate("fecha_inicio_pago"));
                socio.setExonerado(r.getBoolean("exonerado"));
                socio.setSocios_id_socio(r.getInt("socios_id_socio"));
            }
            
            conexion.desconectar();
        }catch(SQLException ex){
        
        }
    
        return socio;
    }
    
    public void updateSocio(Socio socio){
        try{
            conexion.conectar();
            String query = "UPDATE socios SET codigo = ?, nombres = ?, apellidos = ?, dpi = ?, direccion = ?, fecha_inicio_pago = ?, exonerado = ?, socios_id_socio = ? WHERE id_socio = ?";
            PreparedStatement update_socio = conexion.conectar().prepareStatement(query);
            update_socio.setString(1, socio.getCodigo());
            update_socio.setString(2, socio.getCodigo());
            update_socio.setString(3, socio.getApellidos());
            update_socio.setString(4, socio.getDpi());
            update_socio.setString(5, socio.getDireccion());
            update_socio.setDate(6, socio.getFecha_inicio_pago());
            update_socio.setBoolean(7, socio.isExonerado());
            update_socio.setInt(8, socio.getSocios_id_socio());
            update_socio.setInt(9, socio.getId_socio());
            update_socio.executeUpdate();
            conexion.desconectar();
        }catch(SQLException ex){
        
        }
    }
    
    
}
