/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.util;

import ucm.si.basico.ecuaciones.Formula;

/**
 *
 * @author José Antonio
 */
public abstract class NamedFormula
{
    private Formula formula = null;
    private String nombre = "";

    public abstract void loadFormula();

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

}
