/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.List;

/**
 *
 * @author nico
 */
class LinkedBlockingQueue2<S> {
    NodoLista<S> ini, fin;
    int n;
    
    public LinkedBlockingQueue2(){
        ini = null;
        fin = null;
        n = 0;
    }
    
    public LinkedBlockingQueue2(List<S> l){
        NodoLista<S> aux;
        n = l.size();
        for(int i=0;i<n;i++){            
            if (i==0){
                fin = new NodoLista<S>(null,l.get(i));
                ini = fin;
            } else {
                aux = new NodoLista<S>(null,l.get(i));
                fin.ant = aux;
                fin = aux;
            }             
        }
    }
    
    public int size(){
        return n;
    }
    
    public void offer(S s){
        if (ini!=null){
        NodoLista<S> aux = new NodoLista<S>(null,s);
        fin.ant = aux;
        fin = aux;
        } else {
            fin = new NodoLista<S>(null,s);
            ini = fin;
        }        
        n++;
    }

    boolean isEmpty() {
        return n==0;
    }
    
    S poll(){
        S s = ini.getS();
        ini = ini.getAnt();
        if (ini == null) fin = null;
        n--;
        return s;
    }
    
    
}
