/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.navegador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import ucm.si.Checker.util.Roseta;
import ucm.si.Checker.util.StateAndLabel;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;
import ucm.si.util.GrafoCaminos;

/**
 * Esta clase se encarga realizar las operaciones de navegacion sobre el grafo resultado. Ya sea un ejemplo o un contrajemplo.
 * Y tambien de avisar a los oyentes que se ayan registrado.
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
 */
public class Navegador<S> extends NavigatorInterface<S> {

    private GrafoCaminos<S> grafo;
    private Stack<S> recorrido;
    private Roseta<S> roseta;
    private List<String> transiciones;

    public List<String> dameTransiciones() {
        return transiciones;
    }

    public GrafoCaminos<S> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoCaminos<S> grafo) {
        this.grafo = grafo;
    }

    public Navegador(GrafoCaminos<S> grafo, Roseta<S> roseta, List<String> transiciones) {
        this.grafo = grafo;
        this.recorrido = new Stack<S>();
        this.recorrido.push(this.grafo.getInicio());
        this.roseta = roseta;
        this.transiciones = transiciones;
    }

    
    @Override
    public void GoToEstado(S e) {
        synchronized (this) {
            int pos = this.recorrido.search(e);
            if (pos >= 0) {
                this.recorrido.retainAll(this.recorrido.subList(0, pos));
            } else // significa que el estado no sta en la pila y se queire partir de uno a cero
            {
                this.recorrido = null;
                this.recorrido = new Stack<S>();
                this.recorrido.push(e);
            }
        }

        super.notificarOyentes(new GoToEstado(this, e));
    }
    
    @Override
    public  void Avanza(S e)
    {
        synchronized(this)   
        {
            this.recorrido.push(e);
        }
        
        super.notificarOyentes(new Avanza(this, e));
        
    }
    
    @Override
    public void Retrocede()
    {
        S e = null;
        synchronized(this)
        {
            S peek = this.recorrido.pop();
            if(!this.recorrido.empty())
                e = this.recorrido.peek();
            else
                this.recorrido.push(peek);
        }
        
        super.notificarOyentes(new Retrocede(this, e));
    }

    @Override
    public List<StateAndLabel<S>> damePosibles() throws Exception { 
        S e = this.recorrido.peek();
        if(e == null)
        {
            List<StateAndLabel<S>> l = new ArrayList<StateAndLabel<S>>();
            l.add(this.roseta.getSAL(e, this.grafo.getInicio()));
            
            return l;
        }
        
        Iterator<S> laux = this.grafo.getHijos(e).iterator();
        ArrayList<StateAndLabel<S>> taux = new ArrayList<StateAndLabel<S>>();
        while(laux.hasNext())
        {
                S aux = laux.next();
                StateAndLabel<S> salAux = this.roseta.getSAL(e,  aux);
        	taux.add(salAux);
        }
        
        return taux;
    }

    @Override
    public S dameInicial() {
        return this.grafo.getInicio();
    }

    @Override
    public List<S> dameRecorrido() {
        return this.recorrido;
    }
    
    
    
    

}
