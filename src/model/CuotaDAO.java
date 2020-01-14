/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import object.Cuota;

/**
 *
 * @author cesar31
 */
public class CuotaDAO {
    
    private Conexion conexion = new Conexion();
    
    public void setCuota(String nombre, double valor){
        try{
            conexion.conectar();
            String query = "INSERT INTO cuotas (nombre_cuota, valor_cuota) VALUES (?, ?)";
            PreparedStatement set_cuota = conexion.conectar().prepareStatement(query);
            set_cuota.setString(1, nombre);
            set_cuota.setDouble(2, valor);
            set_cuota.executeUpdate();
            conexion.desconectar();
        }catch(SQLException e){
        
        }
    }
    
    public List<Cuota> getCuotas(){
        List<Cuota> cuotas = new ArrayList<>();
        Cuota tmp;
        try{
            conexion.conectar();
            String query = "SELECT * FROM cuotas";
            Statement get_cuotas = conexion.conectar().createStatement();
            ResultSet r = get_cuotas.executeQuery(query);
            
            while(r.next()){
                tmp = new Cuota(r.getInt("id_cuotas"), r.getString("nombre_cuota"), r.getDouble("valor_cuota"));
                cuotas.add(tmp);
            }
            
            conexion.desconectar();
        }catch(SQLException ex){
        
        }
        
        return cuotas;
    }
    
    public void updateCuota(Cuota cuota){
        try{
            conexion.conectar();
            String query = "UPDATE cuotas SET nombre_cuota = ?, valor_cuota = ? WHERE id_cuotas = ? ";
            PreparedStatement update = conexion.conectar().prepareStatement(query);
            update.setString(1, cuota.getNombre_cuota());
            update.setDouble(2, cuota.getValor_cuota());
            update.setInt(3, cuota.getId_cuotas());
            update.executeUpdate();
            conexion.desconectar();
        }catch(SQLException ex){
            
        }
    }
    
}
