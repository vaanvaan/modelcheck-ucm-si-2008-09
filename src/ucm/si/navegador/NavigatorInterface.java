/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador;

//import java.util.o

import java.util.List;
import java.util.Vector;

import ucm.si.Checker.util.StateAndLabel;
import ucm.si.navegador.Listener.AccionListener;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;



/**
 * Clase abstracta que sive de interfaz y base para la creacion de un Navegador
 * Navegador: Objeto encargado de realziar operacion del tipo Avanza a un estado, Retrocede a el estado anterior,..
 *      sobre el grafo que representa el contrajemplo de nuestro model checker. 
 *      Aparte de realizar estas acciones tambien debe realizar operacion para registrar e informar a oentes del tipo AnimadorInterface
 *      Encargados de Representar estas acciones. 
 *      Ademas de poder solicitar su realizacion.
 *
 * @author José Antonio
 */

// Notengo seguro el usar los objetos ya implementados en la api java para el patron Observer

public abstract class NavigatorInterface<S>  //extends Observable
{
    
    //public static Accion AVANZA = Accion.AVANZA;
    private transient Vector<AccionListener<S>> ListaOyentes = new Vector<AccionListener<S>>(2, 5); 
    
    /**
     * Se encarga de eliminar un oyente de la lista a informar
     * @param ia Oyente a Eliminar si es que esta en la lista
     */
    public void removeOyente(AnimadorInterface<S> ia)
    {
        this.ListaOyentes.remove(ia);
    }

    /**
     * Agraga un oyente de la lista a informar
     * @param ia Oyente a agregar
     */
    public void addOyente(AnimadorInterface<S> ia)
    {
        this.ListaOyentes.add(ia);
    }
    
    
    // Habra que implementar una lista para que guarde los nodos recorridos ya que el grafo no nos sirve para definir el nodo concreto del que partimos
    // 
    
    
    // Todos codigos para Notificar Cambios en los oyentes
    /**
     * Se encarga de notificar a los oyentes una accion del tipo Avanzar
     * @param accion
     */
    public void notificarOyentes(Avanza<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }

    /**
     * Se encarga de notificar a los oyentes una accion del tipo Go To (ir a.. estado en concreto)
     * @param accion
     */
    public void notificarOyentes(GoToEstado<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }

    /**
     * Se encarga de notificar a los oyentes una accion del tipo Retrocede a el estado imediatamente anterior del recorrido
     * @param accion
     */
    public void notificarOyentes(Retrocede<S> accion)
    {
        int tope = this.ListaOyentes.size();
        for(int i = 0 ; i< tope; i++)
        {
            this.ListaOyentes.get(i).manejaAccion(accion);
        }
    }

    /**
     * Se encarga de devolcer todos los posibles transiciones para el setado actual
     * (es decir el ultimo estado del recorrido por el grafo contra-jemplo)
     * @return
     * @throws java.lang.Exception
     */
    public abstract List<StateAndLabel<S>> damePosibles() throws Exception;

    /**
     * Devuelve el estado incil del grafo contra-ejemplo
     * @return
     */
    public abstract S dameInicial();

    /**
     * Lista con todos los estados que se han recorrido en este mismo instantne
     * @return
     */
    public abstract List<S> dameRecorrido();

    /**
     * Accion de Ir a un estado en concreto del todo grafo contra-ejemplo
     * @param e
     */
    public abstract void GoToEstado(S e);

    /**
     * Accion de Avanzar a un estado en concreto de entre todos alzanzables inmediatamente desde el estadoactual (estado mas nuevo del recorrido realizado sobre el grafo contra-ejemplo)
     * @param e
     */
    public abstract void Avanza(S e);

    /**
     * Retrocede a el estado imediatemente anterior del recorrido.
     */
    public abstract void Retrocede();

}
