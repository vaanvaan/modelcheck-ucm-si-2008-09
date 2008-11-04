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

    private InterpreteWrapper<S> interprete;
    
    public Resultado chequear( Formula formula) {
        Resultado<S> parcial = new Resultado<S>(Resultado.COD_MAYBET);
        List<S> iniciales = interprete.iniciales();
        Visitante<S> v;
        boolean seguir = true;
        Iterator<S> it = iniciales.iterator();
        while (seguir&&it.hasNext()) {
            S e = it.next();
            v = new Visitante<S>(e,interprete);
            formula.accept(v);
            parcial = v.getResParcial();
            if (!parcial.equals(Resultado.COD_TRUE))
                seguir = false;
        }
        return parcial;
    }

    

    public DefaultModelChecker() {        
    }

    
    
    public Resultado chequear(Interprete<S> interprete, Formula formula, S estado) {
        S s = estado;
        this.interprete = new InterpreteWrapper<S>(interprete);
        if( estado == null)
           s = this.interprete.iniciales().get(0); 
        Visitante<S> v = new Visitante(estado,this.interprete);
        formula.accept(v);
        return v.getResParcial();
    }

    

}
