/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.modular.defecto;

import ucm.si.Checker.Resultado;

/**
 *
 * @author nico
 */
public class ModuloVisitante {
  protected Resultado resParcial = new Resultado(Resultado.COD_TRUE);

    public Resultado getResParcial() {
        return resParcial;
    }

    public void setResParcial(Resultado resParcial) {
        this.resParcial = resParcial;
    }
}
