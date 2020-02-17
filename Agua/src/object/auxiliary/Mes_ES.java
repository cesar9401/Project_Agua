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
public enum Mes_ES {
    Enero(2),
    Febrero(3),
    Marzo(3),
    Abril(4),
    Mayo(5),
    Junio(6),
    Julio(7),
    Agosto(8),
    Septiembre(9),
    Octubre(10),
    Noviembre(11),
    Diciembre(12);
    
    private Mes_ES(int monthValue) {
        this.monthValue = monthValue;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }
    
    private int monthValue;

    
}
