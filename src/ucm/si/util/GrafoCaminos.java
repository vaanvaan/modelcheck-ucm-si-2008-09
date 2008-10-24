/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Pilar
 */
public abstract class GrafoCaminos<S> 
{
    public static GrafoCaminos CreateGrafo()
    {
        return new GrafoUnico();
    };
    
    public static GrafoCaminos CreateGrafo(GrafoCaminos c1)
    {
        return new GrafoDoble(c1);
    };
    
    public static GrafoCaminos CreateGrafo(GrafoCaminos c1, GrafoCaminos c2)
    {
        return new GrafoDoble(c1,c2);
    };

    //abstract public List getPadres(S e);
    
    abstract public void setArista (S eini, S efin);
    
    abstract public void setInicio (S ini);
    
    abstract public S getInicio ();
    
    abstract public List<S> getHijos (S e);
    
    abstract public void setS (S e, List<S> Hijos);
    

}



