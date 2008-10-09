/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.Laberinto.Posicion;

/**
 *
 * @author nico
 */
public class InterpreteWrapper<S> implements Interprete<S> {
    
    private String nombre;
    private Interprete interprete;
    
    private HashMap<S, List<S>> hijos = new HashMap(); // PAra un estado dado los hijos que genera
    private HashMap<S, List<S>> padres = new HashMap(); // Para un estado dado desde los estados que se alcanza a este

    
    
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
       /*
       // cojemos los iniciales y los añandimos al grafo
       int max = l.size();
       for(int i = 0; i<max ; i++)
       {
           S s = l.get(i);
           this.hijos.put(s, new ArrayList<S>() ) ;
           this.padres.put(s, new ArrayList<S>());
       }*/
       
       return l;
       
    }

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
            ArrayList<S> l = new ArrayList(retorno);
            
            // colocamos los estados recibidos por transitar en la lista padres 
            int max = l.size();
            for(int i = 0; i < max; i++)
            {
                S s = l.get(i);
                
                 List<S> laux = null;
                if( padres.containsKey(s))
                {
                    laux = this.padres.get(s);
                    if( !laux.contains(state) )
                    {
                        laux.add(state);
                    }
                }
                else
                {
                     laux = new ArrayList<S>();
                     laux.add(state);
                }
                this.padres.put(s,laux);
            }
            
            // ahora actualizamos la lista de hijos
            
            this.hijos.put(state, l );
            
            // devolvemos la lista original que genera transitar;
            return retorno;
            
            
        }
    }

    
    
    
}
