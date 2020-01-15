/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Julio Fernando Ixcoy
 */
public class ConexionJPA {
    private static final ConexionJPA objetoUnico = new ConexionJPA();
    
    protected EntityManagerFactory emf;
     
    protected ConexionJPA(){ }
    
    public static ConexionJPA getInstancia(){
        return objetoUnico;
    }
    
    public EntityManagerFactory getEMF() {
        if (emf == null)
            setEmf();
        return emf;
    }
    
    public void setEmf() {
        if (emf == null)
           emf = Persistence.createEntityManagerFactory("Project_AguaPU");
    }
    
    public void cerrarEMF() {
        if (this.emf != null)
            emf.close();
    }
}
