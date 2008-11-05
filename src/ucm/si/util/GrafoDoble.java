/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nico
 */
class GrafoDoble<S> extends GrafoCaminos<S> {

    private S inicial = null;
    private GrafoCaminos<S> camino1;
    private GrafoCaminos<S> camino2;
    private TLG<S> caminoFinal;

    protected GrafoDoble(GrafoUnico<S> c1) {
        //this.camino1 = new GrafoUnico<S>(c1);
        //  Cambio realizado de prueba... reversible totalmente
        this.camino1 = c1;
        this.camino2 = new GrafoUnico<S>();
        this.caminoFinal = new TLG<S>();
        this.inicial = c1.getInicio();

    // Almacena el grao mas uno vacio adicional haciendolo pasar por uno solo.
    }

    protected GrafoDoble(GrafoCaminos<S> c1, GrafoCaminos<S> c2) {
        this.camino1 = c1;
        this.camino2 = c2;
        this.caminoFinal = new TLG<S>();
        this.inicial = c1.getInicio();

    //Almacena los dos grafos como si fueran uno.
    }   
    
    protected GrafoDoble(GrafoDoble<S> g){
        this.camino1 = g.camino1;
        this.camino2 = g.camino2;
        this.caminoFinal = new TLG<S>();
        this.inicial = g.inicial;
    }

    protected GrafoDoble(GrafoCaminos<S> c1) {       
        this.camino1 = c1;
        this.camino2 = new GrafoUnico<S>();
        this.caminoFinal = new TLG<S>();
        this.inicial = c1.getInicio();
    }
    
    @Override
    public void setArista(S eini, S efin) {
        caminoFinal.setArista(eini, efin);        
    }

    @Override
    public void setInicio(S ini) {
        this.inicial = ini;
    }

    @Override
    public Set<S> getHijos(S e) {
        Set<S> c1 = this.camino1.getHijos(e);
        Set<S> c2 = this.camino2.getHijos(e);
        // juntamos los 2 caminos contenidos mas luego tambien el del camino final
        // aun no se hace merge completo por problemas "tecnicos"
        
        Set<S> cfinal = new TreeSet<S>();       
        if (c1!=null&&c1.size()>0) cfinal.addAll(c1);
        if (c2!=null&&c2.size()>0) cfinal.addAll(c2);
        
        Set<S> lfin = caminoFinal.getHijo(e);
        if (lfin!=null&&lfin.size()>0) cfinal.addAll(lfin);
        
        // Añadido para guardar la aristas recien calculadas en el caminoFinal para no tener que recalcularlas de nuevo.
        this.caminoFinal.setAristas(e, lfin);
        
        
        return cfinal;
        //return merge ( merge(c1,c2), this.caminoFinal.getHijo(e)  );
    }
    
    

    @Override
    public void setS(S e, Set<S> Hijos) {
        caminoFinal.setAristas(e, Hijos);
    }

    private List merge(List a, List b) {
        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }
        //  si ninguno de los dos es vacio los juntamos
        int la = a.size();
        int lb = b.size();
        ArrayList l = null;
        List ldirec = null;
        List lbase = null;
        // escojemos el mas corto para ahorranos iteraciones
        if (la < lb) {
            l = new ArrayList(b);
            ldirec = a;
            lbase = b;
        } else {
            l = new ArrayList(a);
            ldirec = b;
            lbase = a;
        }
        // juntamos los dos List
        for (int i = 0; i < ldirec.size(); i++) {
            Object o = ldirec.get(i);
            if (!lbase.contains(o)) {
                l.add(o);
            }
        }

        // devolvemos l que es la list final.
        return l;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        GrafoDoble<S> g = new GrafoDoble<S>(this.camino1,this.camino2);
        g.caminoFinal = new TLG<S>(this.caminoFinal);
        g.inicial = this.inicial;
        return g;
    }

    @Override
    public S getInicio() {
        return this.inicial;
    }

    @Override
    public void union(GrafoCaminos<S> g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
