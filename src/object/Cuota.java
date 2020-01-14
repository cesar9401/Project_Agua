/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author cesar31
 */
public class Cuota {
    
    private int id_cuotas;
    private String nombre_cuota;
    private double valor_cuota;

    public Cuota(int id_cuotas, String nombre_cuota, double valor_cuota) {
        this.id_cuotas = id_cuotas;
        this.nombre_cuota = nombre_cuota;
        this.valor_cuota = valor_cuota;
    }

    public int getId_cuotas() {
        return id_cuotas;
    }

    public void setId_cuotas(int id_cuotas) {
        this.id_cuotas = id_cuotas;
    }

    public String getNombre_cuota() {
        return nombre_cuota;
    }

    public void setNombre_cuota(String nombre_cuota) {
        this.nombre_cuota = nombre_cuota;
    }

    public double getValor_cuota() {
        return valor_cuota;
    }

    public void setValor_cuota(double valor_cuota) {
        this.valor_cuota = valor_cuota;
    }
}
