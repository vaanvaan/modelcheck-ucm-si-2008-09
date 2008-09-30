/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.Iterator;
import java.util.List;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;

/**
 *
 * @author nico
 */
public class DefaultModelChecker<S> implements ModelChecker<S>{

    public Resultado chequear(Interprete<S> interprete, Formula formula) {
        Resultado<S> parcial = new Resultado<S>(Resultado.COD_TRUE);
        List<S> iniciales = interprete.iniciales();
        Visitante<S> v;
        boolean seguir = true;
        Iterator<S> it = iniciales.iterator();
        while (seguir&&it.hasNext()) {
            S e = it.next();
            v = new Visitante<S>(interprete,e);
            if (!v.visita(formula).equals(Resultado.COD_TRUE))
                seguir = false;
        }
        return parcial;
    }

    public Resultado chequear(Interprete<S> interprete, Formula formula, S estado) {
        Visitante<S> v = new Visitante<S>(interprete,estado);
        return v.visita(formula);
    }

    

}
