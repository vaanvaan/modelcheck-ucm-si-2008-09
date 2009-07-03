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
 * @author Niko, Jose Antonio, Ivan
 */
// Es posible que los subgrafos se guarden en paralelismo con la parte de formula a analizar
//  eso hay que estudiarlo con tranquilidad, por que sino en la navegacion pueden aparacer problemas.

public class GrafoUnico<S> extends GrafoCaminos<S> implements Comparable<GrafoUnico<S>>
{
    //private LinkedList<GrafoCaminos<S>> ant = new LinkedList<GrafoCaminos<S>>();
    private TLG<S> camino;
    private TreeSet<S> estados;
    private S inicial;
    private static int id = 0;
    private int numid;
    
    public GrafoUnico()
    {
        this.camino = new TLG<S>();        
        this.estados = new TreeSet<S>();
        numid = id++;
        // Genera un grafo unico
    }
    
    public GrafoUnico(S eini){
        this.camino = new TLG<S>();
        this.estados = new TreeSet<S>();
        this.setS(eini, new TreeSet<S>());
        this.setInicio(eini);        
    }

    public GrafoUnico(GrafoCaminos<S> g){
        this.camino = new TLG();
        this.estados = new TreeSet<S>();
        this.union(g);
        this.inicial = g.getInicio();
    }
    
    @Override
    public void setArista(S eini, S efin) 
    {
        this.camino.setArista(eini, efin);
        this.estados.add(eini);
    }

    @Override
    public void setInicio(S ini) {
        this.inicial = ini;
    }

    @Override
    public Set<S> getHijos(S e) {
        Set<S> s = this.camino.getHijo(e);
        if (s==null) s = new TreeSet<S>();
        return s;
    }

    @Override
    public void setS(S e, Set<S> Hijos) 
    {
        this.camino.setAristas(e, Hijos);
        this.estados.add(e);
    }

    @Override
    public S getInicio() {
        return this.inicial;
    }

    @Override
    public void union(GrafoCaminos<S> g) {
        Set<S> s = g.getEstados();
        for (Iterator<S> it = s.iterator(); it.hasNext();) {
            S s1 = it.next();
            this.camino.addAristas(s1, new TreeSet<S>(g.getHijos(s1)));
            this.estados.add(s1);
        }
    }

    @Override
    public int size() {
        return this.camino.size();
    }

    public int compareTo(GrafoUnico<S> o) {
        if (this.numid>o.numid)
            return 1;
        else if (this.numid<o.numid)
            return -1;
        else return 0;
    }

    @Override
    public Set<S> getEstados() {
        return this.estados;
    }

   
}
