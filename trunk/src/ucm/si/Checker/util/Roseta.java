/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.util;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pilar
 */
public class Roseta<S> extends HashMap<S, List<StateAndLabel<S>>>
{
    public Roseta()
    {
        super();
    }
    
    public Roseta( int cap)
    {
        super(cap);
    }
    
    public Roseta( int cap, float coef)
    {
        super(cap, coef);
    }
    
    
    /**
     * Devuelve la lista de StateAndLabel (estados y las etiquetas de sus transiciones)
     * de los estados hijos al padre
     * 
     * @param s Estado del cual queremos saber sus posibles hijos y sus respectivas etiquetas
     * @return 
     */
    public List<StateAndLabel<S>> getNextSAL(S s)
    {
        return super.get(s);
    }
    
    /**
     * Para una arista compuesta por los estado padre e hijo (primer y ultimo estado de la arista)
     * se devuelve el nombre de esa transicion.
     * @param padre Estado primero de la arista
     * @param hijo Segundo y ultimo estado de la arista dirigida.
     * @return String (etiqueta de la arsita o transicion)
     * @throws java.lang.Exception ("Arista no encontrada")
     */
    public String getLabel (S padre, S hijo) throws Exception
    {
        List<StateAndLabel<S>> l = super.get(hijo);
        if(l.contains(l))
        {
            int i = l.indexOf(l);
            StateAndLabel<S> sal = l.get(i);
            return sal.getLabel();
            
        }
        else
            throw (new Exception("Arista no encontrada"));
    }

}
