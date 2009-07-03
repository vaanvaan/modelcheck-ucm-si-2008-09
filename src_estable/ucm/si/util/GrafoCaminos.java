/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public abstract class GrafoCaminos<S>
{
       
    
    
    public static GrafoCaminos CreateGrafo()
    {
        return new GrafoUnico();
    };
    
    public static GrafoCaminos CreateGrafo(GrafoCaminos c1)
    {
        return new GrafoUnico(c1);
    };
    
    public static GrafoCaminos CreateGrafo(GrafoCaminos c1, GrafoCaminos c2)
    {   
        GrafoUnico g = new GrafoUnico(c1);
        g.union(c2);
        return g;
    };

    //abstract public List getPadres(S e);
    
    abstract public void setArista (S eini, S efin);
    
    abstract public void setInicio (S ini);
    
    abstract public S getInicio ();
    
    abstract public Set<S> getHijos (S e);
    
    abstract public void setS (S e, Set<S> Hijos);
    
    abstract public void union(GrafoCaminos<S> g);
    
    abstract public int size();
    
    abstract public Set<S> getEstados();
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    
}



