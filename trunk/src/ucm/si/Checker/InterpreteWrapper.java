/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.Checker.util.Roseta;
import ucm.si.Checker.util.StateAndLabel;
import ucm.si.Checker.util.StateLabeledList;

/**
 *
 * @author nico
 */
public class InterpreteWrapper<S> implements Interprete<S> {
    
    private String nombre;
    private Interprete interprete;
    
    private HashMap<S, List<S>> hijos = new HashMap<S, List<S>>(); // PAra un estado dado los hijos que genera
    
    private HashMap<S, List<String>> transiciones = new HashMap<S, List<String>>();
    
    
    private HashMap<S, List<S>> padres = new HashMap<S, List<S>>();
    
    public InterpreteWrapper(Interprete interprete) {
        this.interprete = interprete;
        this.nombre = this.interprete.getClass().getName();
        
    }

    public InterpreteWrapper(String nombre) {
        try {
            this.nombre = nombre;
        
            this.interprete = (Interprete) Class.forName(this.nombre).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(InterpreteWrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InterpreteWrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterpreteWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<S> iniciales() 
    {
       List<S> l = this.interprete.iniciales();       
       return l;
       
    }

   /* 
    
    * Funcion antigua reservada para otras operaciones
    
    public List<S> transitar(S state) 
    {
        // buscamos si hay una entrada para ese estado en la tabla de hijos para asi conocer a los hijos
        
        if( this.hijos.containsKey(state) )
        {
            return this.hijos.get(state);
        }
        else
            // si no hay entrada le ordenamos al estado transitar que genere la lista de estados y luego actualizas hijos con esa lista y despues pares con el estado dado
        {
            List<S> retorno = this.interprete.transitar(state);
            
            this.hijos.put(state, retorno );
            
            this.colocarPadre(state, retorno);
            // devolvemos la lista original que genera transitar;
            return retorno;
            
            
        }
    }*/
    
    public List<S> transitar(S state) 
    {
        // buscamos si hay una entrada para ese estado en la tabla de hijos para asi conocer a los hijos
        
        if( this.hijos.containsKey(state) )
        {
            return this.hijos.get(state);
        }
        else
            // si no hay entrada le ordenamos al estado transitar que genere la lista de estados y luego actualizas hijos con esa lista y despues pares con el estado dado
        {
            List<S> retorno; // Lista de estados a los que transita
            
            List<String> etiq; // Lista de los nombre de las transiciones 
                               //(las posiciones son semejantes a las de la lista de estados transitados)
            
            // pedimos al metodo trnsitarConEtiqueta que nos devuelva ls lista de stados y trnsiciones.
            StateLabeledList<S> stl = this.interprete.transitarConEtiqueta(state);
            
            // extraemos del objeto stl la lista de transitados y la de transiciones respectivamente
            retorno = stl.getListaEstados();
            etiq = stl.getListaEtiquetas();
            
            // actualizamos las listas de trnsitados y de transiciones
            this.hijos.put(state, retorno);
            this.transiciones.put(state, etiq);
            
            // actuliazamos añadimos como padre a state para todos los estados en la lista de transitados
            this.colocarPadre(state, retorno);
            
            // devolvemos la lista original que genera transitar;
            return retorno;
            
            
        }
    }

    private void colocarPadre(S state, List<S> retorno)
    {
        for(int i = 0; i< retorno.size(); i++)
        {
            S s = retorno.get(i);
            List<S> l = this.padres.get(s);
            if(l == null)
            {
                l = new ArrayList<S>();
                l.add(state);
                this.padres.put(s, l);
            }
            else
            {
                if( !l.contains(s))
                {
                    l.add(state);
                }
            }
        }
        
        
    }

    public StateLabeledList<S> transitarConEtiqueta(S state) 
    {
        // comprobamos que existan entradas para esa key
        if( this.hijos.containsKey(state) )
        {
            // pedimos las listas de transitados y transiciones
            List<S> ls = this.hijos.get(state);
            List<String> letiq = this.transiciones.get(state);
            // construimos un objeto StaeLabledList a partir de las lista obtenidas y lo devolvemos
            return new StateLabeledList<S>(ls, letiq);
        }
        
        //si no existen entradas apra esa key
        // invocmoa al interprete para uqe nos devuleva el objeto stl
        StateLabeledList<S> stl = this.interprete.transitarConEtiqueta(state);
        
        // actualizamos los mapas de hijos y transiciones respectivamente con los
        // listas de trnsitados y transiciones contenidos en stl.
        this.hijos.put(state, stl.getListaEstados());
        this.transiciones.put(state, stl.getListaEtiquetas());
        
        return stl;
        
    }
    
    
    public Roseta<S> getRoseta()
    {
        Roseta<S> roseta = (Roseta<S>) new HashMap<S, List<StateAndLabel<S>>>();
        
        Set<S> set = this.hijos.keySet();
        Iterator<S> it = set.iterator();
        S s;
        while(it.hasNext())
        {
            s = it.next();
            
            
            List<S> ls =this.hijos.get(s);
            List<String> letiq = this.transiciones.get(s);
            
            List<StateAndLabel<S>> listaRos = new ArrayList<StateAndLabel<S>>();
            for(int i = 0; i < ls.size(); i++)
            {
                StateAndLabel<S> sal = new StateAndLabel<S>(ls.get(i), letiq.get(i));
                listaRos.add(sal);
            }
            roseta.put(s, listaRos);
            
        }
        
        return roseta;
        
    }
    
    
}
