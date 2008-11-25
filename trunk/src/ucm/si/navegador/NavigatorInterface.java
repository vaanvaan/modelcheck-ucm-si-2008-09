/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador;

//import java.util.o

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import ucm.si.Checker.util.StateAndLabel;
import ucm.si.navegador.Listener.AccionListener;
import ucm.si.navegador.events.Accion;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;



/**
 *
 * @author José Antonio
 */

// Notengo seguro el usar los objetos ya implementados en la api java para el patron Observer

public abstract class NavigatorInterface<S>  //extends Observable
{
    
    //public static Accion AVANZA = Accion.AVANZA;
    private transient Vector<AccionListener<S>> ListaOyentes = new Vector<AccionListener<S>>(2, 5); 
    
    
    public void removeOyente(AnimadorInterface<S> ia)
    {
        this.ListaOyentes.remove(ia);
    }
    
    public void addOyente(AnimadorInterface<S> ia)
    {
        this.ListaOyentes.add(ia);
    }
    
    
    // Habra que implementar una lista para que guarde los nodos recorridos ya que el grafo no nos sirve para definir el nodo concreto del que partimos
    // 
    
    
    // Todos codigos para Notificar Cambios en los oyentes
       public void notificarOyentes(Avanza<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public void notificarOyentes(GoToEstado<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public void notificarOyentes(Retrocede<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public abstract Set<StateAndLabel<S>> damePosibles() throws Exception;
    
    public abstract S dameInicial();
    
    public abstract List<S> dameRecorrido();
    
    public abstract void GoToEstado(S e);
    
    public abstract void Avanza(S e);
    
    public abstract void Retrocede();

}
