/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
// Es posible que los subgrafos se guarden en paralelismo con la parte de formula a analizar
//  eso hay que estudiarlo con tranquilidad, por que sino en la navegacion pueden aparacer problemas.

public class GrafoUnico<S> extends GrafoCaminos<S>
{
    private TLG camino;
    private S inicial;
    
    public GrafoUnico()
    {
        this.camino = new TLG(); 
        // Genera un grafo unico
    }
    
    public GrafoUnico(S eini){
        this.camino = new TLG();
        this.setS(eini, new ArrayList<S>());
        this.setInicio(eini);        
    }

    @Override
    public void setArista(S eini, S efin) 
    {
        this.camino.setArista(eini, efin);
    }

    @Override
    public void setInicio(S ini) {
        this.inicial = ini;
    }

    @Override
    public List<S> getHijos(S e) {
        return this.camino.getHijo(e);
    }

    @Override
    public void setS(S e, List<S> Hijos) 
    {
        this.camino.getTabla().put(e, Hijos);
    }

    @Override
    public S getInicio() {
        return this.inicial;
    }
  
    
    // funciones par aimplementar la chache.
  /*  public List<S> recuperaHijo(S s)
    {
        List<S> l = this.getHijos(s);
        this.camino.
        return this.
    }*/



   
}
