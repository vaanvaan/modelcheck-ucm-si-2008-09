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
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Pilar
 */
public class TLG<S> {

    private TLG<S> ant = null;
    private TreeMap<S, Set<S>> tabla;

    public TLG() {
        this.tabla = new TreeMap<S, Set<S>>();
    }

    public TLG(int tam) {
        this.tabla = new TreeMap<S, Set<S>>();
    }

    public TLG(TLG<S> t) {
        this.tabla = new TreeMap<S, Set<S>>();
        this.ant = t;
    }

    public Set<S> getHijo(S e) {
        Set<S> set;
        if ((this.tabla.get(e) != null) && (this.tabla.get(e).size() > 0)) {
            set = this.tabla.get(e);
        } else {
            set = new TreeSet<S>();
        }
        if (ant != null) {
            Set<S> setant = ant.getHijo(e);
            if ((setant != null) && (setant.size() > 0)) {
                set.addAll(setant);
            }
        }
        return set;
    }

    public void setArista(S eini, S efin) {
        TreeSet<S> l;
        if (this.tabla.containsKey(eini)) {
            this.tabla.get(eini).add(efin);
        } else {
            l = new TreeSet<S>();
            l.add(efin);
            this.tabla.put(eini, l);
        }
        if (!this.tabla.containsKey(efin)) {
            l = new TreeSet<S>();
            this.tabla.put(efin, l);
        }
    }
    // Nuevo codigo para añadir varias arista que sutituyen a las anteriores.
    public void setAristas(S eni, Set<S> c) {
        this.tabla.put(eni, c);
    }
    //Añadido para AÑADIR sin sustituir las aristas anteriores.
    public void addAristas(S eini, Set<S> c) {
        if (this.tabla.containsKey(eini)) {
            this.tabla.get(eini).addAll(c);
        } else {
            this.tabla.put(eini, c);
        }
    }

    public TreeMap<S, Set<S>> getTabla() {
        return tabla;
    }

    public void setTabla(TreeMap<S, Set<S>> tabla) {
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
