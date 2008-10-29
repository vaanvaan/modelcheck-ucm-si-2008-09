/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import ucm.si.basico.ecuaciones.Formula;

/**
 *
 * @author Pilar
 */
public class TabulacionMemSistema<S> implements TabulacionFormulas<S>
{

    private HashMap<S,Set<Formula>> mapa = new HashMap<S, Set<Formula>>();
    
    public Set<Formula> getEtiquetas(S estado) 
    {
        return this.mapa.get(estado);
    }

    public void setEtiquetas(S estado, Set<Formula> etiquetas) {
        this.mapa.put(estado, etiquetas);
        //si el estado no existe se crea o se lanza una excepcion
    }

    public void aniadirEtiqueta(S estado, Formula formula) {
        if (tieneEstado(estado))
        this.mapa.get(estado).add(formula);        
        else{
            HashSet<Formula> set = new HashSet<Formula>();
            set.add(formula);
            this.mapa.put(estado, set);
        }
    }

    public boolean tieneEtiqueta(S estado, Formula formula) 
    {
        Set<Formula> s = this.mapa.get(this);
        if(s != null)
        {
            return s.contains(estado);
        }
        return false;
    }

    public void aniadirEstado(S estado, Set<Formula> etiquetas) 
    {
        this.mapa.put(estado, etiquetas);
        
    }

    public boolean tieneEstado(S estado) {
        return this.mapa.containsKey(estado);
    }
    

}
