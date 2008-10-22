/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.List;
import ucm.si.Checker.Estado;


/**
 *
 * @author Pilar
 */
public abstract class GrafoCaminos 
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
    
    abstract public void setEstado (Estado e, List Hijos);
    

}

// Es posible que los subgrafos se guarden en paralelismo con la parte de formula a analizar
//  eso hay que estudiarlo con tranquilidad, por que sino en la navegacion pueden aparacer problemas.

class GrafoUnico extends GrafoCaminos
{
    private TLG camino;
    private Estado inicial;
    
    protected GrafoUnico()
    {
        this.camino = new TLG(); 
        // Genera un grafo unico
    }

    @Override
    public void setArista(Estado eini, Estado efin) 
    {
        this.camino.setArista(eini, efin);
    }

    @Override
    public void setInicio(Estado ini) {
        this.inicial = ini;
    }

    @Override
    public List getHijos(Estado e) {
        return this.camino.getHijo(e);
    }

    @Override
    public void setEstado(Estado e, List Hijos) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }



   
}
class GrafoDoble extends GrafoCaminos
{
    private Estado inicial =null;
    private GrafoCaminos camino1;
    private GrafoCaminos camino2;
    private TLG caminoFinal;

    protected GrafoDoble(GrafoCaminos c1)
    {
        this.camino1 = c1;
        this.camino2 = new GrafoUnico(); 
        this.caminoFinal = new TLG();
 
        // Almacena el grao mas uno vacio adicional haciendolo pasar por uno solo.
    }
    
    protected GrafoDoble(GrafoCaminos c1, GrafoCaminos c2)
    {
        this.camino1 = c1;
        this.camino2 = c2;
        this.caminoFinal = new TLG();
        
        //Almacena los dos grafos como si fueran uno.
    }

    @Override
    public void setArista(Estado eini, Estado efin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInicio(Estado ini) {
        this.inicial = ini;
    }

    @Override
    public List getHijos(Estado e) 
    {
        List c1 = this.camino1.getHijos(e);
        List c2 =this.camino2.getHijos(e);
        // juntamos los 2 caminos contenidos mas luego tambien el del camino final
        // aun no se hace merge completo por problemas "tecnicos"
        return merge ( merge(c1,c2), this.caminoFinal.getHijo(e)  );
    }


    @Override
    public void setEstado(Estado e, List Hijos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private List merge (List a, List b)
    {
        if (a == null)
        {
            return b;
        }
        
        if (b == null)
        {
            return a;
        }
        //  si ninguno de los dos es vacio los juntamos
        int la = a.size();
        int lb = b.size();
        ArrayList l = null;
        List ldirec = null;
        List lbase = null;
        // escojemos el mas corto para ahorranos iteraciones
        if(la  < lb )
        {
            l = new ArrayList(b);
            ldirec = a;
            lbase = b;
        }
        else
        {
            l = new ArrayList(a);
            ldirec = b;
            lbase = a;
        }
        // busiconamos los dos List
        for(int i =0; i<ldirec.size();i++ )
        {
            Object o = ldirec.get(i);
            if(! lbase.contains(o))
            {
                l.add(o);
            }
        }
        
        // devolvemos l que es la list final.
        return l;
        
    }

   

   
}
