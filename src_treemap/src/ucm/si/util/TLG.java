/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
    private LinkedList<S> listaEstados;
    private LinkedList<Set<S>> listaSets;

    public LinkedList<S> getListaEstados() {
        return listaEstados;
    }

    public TLG() {
        this.listaEstados = new LinkedList<S>();
        this.listaSets = new LinkedList<Set<S>>();
    }

    public TLG(TLG<S> t) {
        this.listaEstados = new LinkedList<S>(t.listaEstados);
        this.listaSets = new LinkedList<Set<S>>(t.listaSets);
        this.ant = t;
    }

    public Set<S> getHijo(S e) {
        Set<S> set;
        if (this.listaEstados.contains(e)) {
            set = this.listaSets.get(this.listaEstados.indexOf(e));            
        } else {
            set = new TreeSet<S>();
            this.listaEstados.add(e);
            this.listaSets.add(set);
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
        if (this.listaEstados.contains(eini)) {
            l = (TreeSet<S>)this.listaSets.get(this.listaEstados.indexOf(eini));
            if (!l.contains(efin))
                l.add(efin);
        } else {
            l = new TreeSet<S>();
            l.add(efin);
            this.listaEstados.add(eini);
            this.listaSets.add(l);
        }
        /*if (!this.tabla.containsKey(efin)) {
            l = new TreeSet<S>();
            this.tabla.put(efin, l);
        }*/
    }
    // Nuevo codigo para añadir varias arista que sutituyen a las anteriores.
    public void setAristas(S eini, Set<S> c) {
        this.listaEstados.add(eini);
        this.listaSets.add(c);
    }
    //Añadido para AÑADIR sin sustituir las aristas anteriores.
    public void addAristas(S eini, Set<S> c) {
        if (this.listaEstados.contains(eini)) {
            this.listaSets.get(this.listaEstados.indexOf(eini)).addAll(c);
        } else {
            this.listaEstados.add(eini);
            this.listaSets.add(c);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TLG<S> other = (TLG<S>) obj;
        if (this.listaEstados != other.listaEstados && (this.listaEstados == null || !this.listaEstados.equals(other.listaEstados))) {
            return false;
        }
        if (this.listaSets != other.listaSets && (this.listaSets == null || !this.listaSets.equals(other.listaSets))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.listaEstados != null ? this.listaEstados.hashCode() : 0);
        hash = 59 * hash + (this.listaSets != null ? this.listaSets.hashCode() : 0);
        return hash;
    }

    /*public void setTabla(TreeMap<S, Set<S>> tabla) {
        this.tabla = tabla;
    }*/
    // apaños para el chache
    /*public void removeArista()
    {}*/
    

}
