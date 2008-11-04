/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author nico
 */
// Es posible que los subgrafos se guarden en paralelismo con la parte de formula a analizar
//  eso hay que estudiarlo con tranquilidad, por que sino en la navegacion pueden aparacer problemas.

public class GrafoUnico<S> extends GrafoCaminos<S> implements Comparable<GrafoUnico<S>>
{
    private LinkedList<GrafoCaminos<S>> ant = new LinkedList<GrafoCaminos<S>>();
    private TLG<S> camino;
    private S inicial;
    private static int id = 0;
    private int numid;
    
    public GrafoUnico()
    {
        this.camino = new TLG<S>(); 
        numid = id++;
        // Genera un grafo unico
    }
    
    public GrafoUnico(S eini){
        this.camino = new TLG<S>();
        this.setS(eini, new TreeSet<S>());
        this.setInicio(eini);        
    }

    public GrafoUnico(GrafoCaminos<S> g){
        this.ant.add(g);
        this.camino = new TLG();
        this.inicial = g.getInicio();
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
    public Set<S> getHijos(S e) {
        Set<S> s;        
        if (this.camino.getHijo(e)!=null)
            s = this.camino.getHijo(e);
        else s = new TreeSet<S>();
        Object[] ant2 = ant.toArray();
        for (int i=0; i < ant.size();i++){
            Set<S> h = ((GrafoCaminos<S>)ant2[i]).getHijos(e);
            if ((h!=null)&&(h.size()>0))
                    s.addAll(h);
        }        
        return s;
    }

    @Override
    public void setS(S e, Set<S> Hijos) 
    {
        this.camino.setAristas(e, Hijos);
    }

    @Override
    public S getInicio() {
        return this.inicial;
    }

    @Override
    public void union(GrafoCaminos<S> g) {
        ant.add(g);
        
    }

    @Override
    public int size() {
        int suma = 0;
        GrafoCaminos<S>[] ant2 = (GrafoCaminos<S>[]) ant.toArray();
        for (int i =0; i < ant.size();i++)
            suma = suma + ant2[i].size();
        return this.camino.getListaEstados().size()+suma;
    }

    public int compareTo(GrafoUnico<S> o) {
        if (this.numid>o.numid)
            return 1;
        else if (this.numid<o.numid)
            return -1;
        else return 0;
    }


      
    
    // funciones par aimplementar la chache.
  /*  public List<S> recuperaHijo(S s)
    {
        List<S> l = this.getHijos(s);
        this.camino.
        return this.
    }*/



   
}
