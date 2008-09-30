/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nico
 */
public class InterpreteWraper<S> implements Interprete<S> {
    
    private String nombre;
    private Interprete interprete;

    public InterpreteWraper(Interprete interprete) {
        this.interprete = interprete;
        this.nombre = this.interprete.getClass().getName();
    }

    public InterpreteWraper(String nombre) {
        try {
            this.nombre = nombre;
        
            this.interprete = (Interprete) Class.forName(this.nombre).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(InterpreteWraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InterpreteWraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterpreteWraper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<S> iniciales() {
       return this.interprete.iniciales(); 
    }

    public List<S> transitar(S state) {
        return this.interprete.transitar(state);
    }

    
    
    
}
