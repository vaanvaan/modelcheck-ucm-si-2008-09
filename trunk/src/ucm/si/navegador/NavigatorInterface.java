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
import java.util.Vector;
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
    private transient Vector<AccionListener> ListaOyentes = new Vector<AccionListener>(2, 5); 
    
    public void addOyente(AnimadorInterface ia)
    {
        this.ListaOyentes.add(ia);
    }
    
    
    // Habra que implementar una lista para que guarde los nodos recorridos ya que el grafo no nos sirve para definir el nodo concreto del que partimos
    // 
    
    
    // Todos codigos para Notificar Cambios en los oyentes
       public void notificarOyentes(Avanza accion)
    {
        int tope = this.ListaOyentes.capacity();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public void notificarOyentes(GoToEstado accion)
    {
        int tope = this.ListaOyentes.capacity();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public void notificarOyentes(Retrocede accion)
    {
        int tope = this.ListaOyentes.capacity();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }
    
    public abstract List damePosibles();
    
    public abstract S dameInicial();

}
