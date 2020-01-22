/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.auxiliary;

import java.math.BigDecimal;

/**
 *
 * @author cesar31
 */
public class ViewEventos {
    
    private int id_eventos;
    private String nombre;
    private String fecha;
    private BigDecimal cuota;

    public ViewEventos(int id_eventos, String nombre, String fecha, BigDecimal cuota) {
        this.id_eventos = id_eventos;
        this.nombre = nombre;
        this.fecha = fecha;
        this.cuota = cuota;
    }

    public int getId_eventos() {
        return id_eventos;
    }

    public void setId_eventos(int id_eventos) {
        this.id_eventos = id_eventos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }
}
