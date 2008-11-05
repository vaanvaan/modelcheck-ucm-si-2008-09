/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import ucm.si.Checker.Resultado;
import ucm.si.basico.ecuaciones.Formula;

/**
 *
 * @author Pilar
 */
public class TabulacionMemSistema<S> implements TabulacionFormulas<S>
{

    private TreeMap<S,HashMap<Formula,Resultado<S>>> mapa =
            new TreeMap<S, HashMap<Formula,Resultado<S>>>();
    
    
    

    public void aniadirEtiqueta(S estado, Formula formula, Resultado r) {
        HashMap<Formula,Resultado<S>> set;
        if (!tieneEstado(estado)){            
            set = new HashMap<Formula,Resultado<S>>();            
        } else set = this.mapa.get(estado);
        Resultado<S> r2 = new Resultado(r.getResultado());   
        r2.setEjemplo(r.getEjemplo());
        r2.setContraejemplo(r.getContraejemplo());
        set.put(formula,r2);
        this.mapa.put(estado, set);
    }

    public boolean tieneEtiqueta(S estado, Formula formula) 
    {
        HashMap<Formula,Resultado<S>> s = this.mapa.get(estado);
        if(s != null)
        {
            return s.containsKey(formula);            
        }
        return false;
    }

    public boolean tieneEstado(S estado) {
        return this.mapa.containsKey(estado);
    }

    public Set<Formula> getEtiquetas(S estado) {
         return this.mapa.get(estado).keySet();
    }


    public Resultado getResultado(S estado, Formula formula) {
        return this.mapa.get(estado).get(formula);
    }
    

}
