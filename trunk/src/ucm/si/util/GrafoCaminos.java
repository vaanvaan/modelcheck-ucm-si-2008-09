/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.List;
import ucm.si.Checker.Estado;


/**
 *
 * @author Pilar
 */
abstract class GrafoCaminos 
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

    //abstract public List getPadres(Estado e);
    
    abstract public void setArista (Estado eini, Estado efin);
    
    abstract public void setInicio (Estado ini);
    
    abstract public List getHijos (Estado e);
    
    abstract public void setEstado (List padres, Estado e, List Hijos);
    

}

// Es posible que los subgrafos se guarden en paralelismo con la parte de formula a analizar
//  eso hay que estudiarlo con tranquilidad, por que sino en la navegacion pueden aparacer problemas.

class GrafoUnico extends GrafoCaminos
{
    protected GrafoUnico()
    {
        // Genera un grafo unico
    }

    @Override
    public void setArista(Estado eini, Estado efin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInicio(Estado ini) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getHijos(Estado e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEstado(List padres, Estado e, List Hijos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



   
}
class GrafoDoble extends GrafoCaminos
{
    protected GrafoDoble(GrafoCaminos c1)
    {
        // Almacena el grao mas uno vacio adicional haciendolo pasar por uno solo.
    }
    
    protected GrafoDoble(GrafoCaminos c1, GrafoCaminos c2)
    {
        //Almacena los dos grafos como si fueran uno.
    }

    @Override
    public void setArista(Estado eini, Estado efin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInicio(Estado ini) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getHijos(Estado e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEstado(List padres, Estado e, List Hijos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

   
}