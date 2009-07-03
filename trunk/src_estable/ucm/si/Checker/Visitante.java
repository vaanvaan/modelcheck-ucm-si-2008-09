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
 * Esta calse funciona como una interfaz.
 * Define el visitante que recorrerá la formula proporcionada al model check. Esta clase implementa un pàtron visitor.
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
 */
public abstract class Visitante<S> {

    protected Resultado resParcial = new Resultado(Resultado.COD_TRUE);

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
