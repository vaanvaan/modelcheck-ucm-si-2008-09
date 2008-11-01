/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.TreeSet;

/**
 *
 * @author nico
 */
class NodoVisitados<S> {
    NodoVisitados<S> ant;
    TreeSet<S> set;
    
    public NodoVisitados() {
        this.ant = null;
        this.set = new TreeSet<S>();
    }
    
    public NodoVisitados(NodoVisitados<S> ant) {
        this.ant = ant;
        this.set = new TreeSet<S>();
    }
    
    public boolean contains(S s){
        if (set.contains(s)){
            return true;
        } else if (ant==null){
            return false;
        } else return ant.contains(s);
    }
    
    public void add(S s){
        set.add(s);
    }

}
