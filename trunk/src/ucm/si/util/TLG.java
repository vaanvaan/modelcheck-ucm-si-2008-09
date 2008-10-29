/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pilar
 */
public class TLG<S> {

    private HashMap<S, Set<S>> tabla;

    public TLG() {
        this.tabla = new HashMap<S, Set<S>>();
    }

    public TLG(int tam) {
        this.tabla = new HashMap<S, Set<S>>(tam);
    }

    public TLG(TLG<S> t) {
        this.tabla = new HashMap<S, Set<S>>();
        Set<S> claves = t.tabla.keySet();        
        for (Iterator<S> it = claves.iterator(); it.hasNext();) {
            S s = it.next();  
            this.tabla.put(s, new HashSet<S>(t.tabla.get(s)));
        }        
    }

    public Set<S> getHijo(S e) {
        return this.tabla.get(e);
    }

    public void setArista(S eini, S efin) {
        HashSet<S> l;
        if (this.tabla.containsKey(eini)) {
            this.tabla.get(eini).add(efin);
        } else {
            l = new HashSet<S>();
            l.add(efin);
            this.tabla.put(eini, l);
        }
        if (!this.tabla.containsKey(efin)) {
            l = new HashSet<S>();
            this.tabla.put(efin, l);
        }
    }
    
    // Nuevo codigo para añadir varias arista que sutituyen a las anteriores.
     public void setAristas(S eni, Set<S> c)
     {
         this.tabla.put(eni, c);
     }
     
     //Añadido para AÑADIR sin sustituir las aristas anteriores.
     public  void addAristass(S eini, Set<S> c)
     {
         this.tabla.get(eini).addAll(c);
     }       

    public HashMap<S, Set<S>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<S, Set<S>> tabla) {
        this.tabla = tabla;
    }
    // apaños para el chache
    /*public void removeArista()
    {}*/
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TLG other = (TLG) obj;
        if (this.tabla != other.tabla && (this.tabla == null || !this.tabla.equals(other.tabla))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.tabla != null ? this.tabla.hashCode() : 0);
        return hash;
    }
}
