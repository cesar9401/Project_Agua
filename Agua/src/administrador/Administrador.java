
package administrador;

import object.Administradores;
import object.Socios;

/**
 *
 * @author cesar31
 */
public class Administrador {
    
    private static Administradores admin;
    private static Socios socio;
 
    public static Administradores getAdmin() {
        return admin;
    }

    public static void setAdmin(Administradores admin) {
        Administrador.admin = admin;
    }

    public static Socios getSocio() {
        return socio;
    }

    public static void setSocio(Socios socio) {
        Administrador.socio = socio;
    }
}