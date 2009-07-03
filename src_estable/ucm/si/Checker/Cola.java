package ucm.si.Checker;

import java.util.List;
/**
 *
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
 */
public class Cola<S> {
    NodoLista<S> ini, fin;
    int n;

    /**
     * Contructor por defecto
     */
    public Cola(){
        ini = null;
        fin = null;
        n = 0;
    }
    /**
     * Constructor, necesita los elementos que se van a añadir a la cola.
     * @param l
     */
    public Cola(List<S> l){
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

    /**
     * Devuelve el tamaño de la cola
     * @return Tamaño actual de la cola
     */
    public int size(){
        return n;
    }

    /**
     * Se añade a la cola el elemento s
     * @param s Elemento a añadir
     */
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

    /**
     * Devuelve si la Cola esta vacia
     * @return
     */
    public boolean isEmpty() {
        return n==0;
    }

    /**
     * Devuelve el primer elemento de la cola y lo elimina de la cabecera
     * @return Primer elemento de la cola.
     */
    public S poll(){
        S s = ini.getS();
        ini = ini.getAnt();
        if (ini == null) fin = null;
        n--;
        return s;
    }
    
    
}
