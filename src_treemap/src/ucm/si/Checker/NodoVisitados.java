/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;


/**
 *
 * @author nico
 */
class NodoVisitados<S> {
    NodoVisitados<S> ant;
    S set;
    
    public NodoVisitados() {
        this.ant = null;
        this.set = null;
    }
    
    public NodoVisitados(NodoVisitados<S> ant) {
        this.ant = ant;
        this.set = null;
    }
    
    public boolean contains(S s){
        if ((set!=null)&&(set.equals(s))){
            return true;
        } else if (ant==null){
            return false;
        } else return ant.contains(s);
    }
    
    public void add(S s){
        this.set = s;
    }

}
