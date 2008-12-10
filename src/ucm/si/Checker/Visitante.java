/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;

/**
 *
 * @author nico
 */
public abstract class Visitante<S> {
    protected S estado;
    protected Resultado resParcial = new Resultado(Resultado.COD_TRUE);


    public void actualizar(S estado, Resultado<S> resParcial) {
        this.estado = estado;
        this.resParcial = resParcial;
    }

    public Resultado getResParcial() {
        return resParcial;
    }
    
    public void setResParcial(Resultado resParcial) {
        this.resParcial = resParcial;
    }

    public abstract void visita(Proposicion<S> p);

    public abstract void visita(Not n);

    public abstract void visita(Or or);

    public abstract void visita(And and);

    public abstract void visita(AX allnext);

    public abstract void visita(EX eventx);

    public abstract void visita(AU au);

    public abstract void visita(EU eu);


}
