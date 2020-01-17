/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cesar31
 */
public class Conexion {
    
    private Connection connection = null;
    private final String USER = "projects";
    private final String PASS = "toor";
    private final String URL = "jdbc:mysql://localhost:3306/proyecto_agua";
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conectado " + connection.getCatalog());
        }catch(SQLException ex){
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return connection;
    }
    
    public void desconectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection.close();
            System.out.println("Desconectado " + connection.getCatalog());
        }catch(SQLException ex){
            System.out.println("No se pudo desconectar");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
