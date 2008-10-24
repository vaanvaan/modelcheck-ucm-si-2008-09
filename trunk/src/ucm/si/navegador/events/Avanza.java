/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import ucm.si.Checker.*;
import java.security.spec.ECField;
import java.util.EventObject;
import java.util.List;

/**
 *
 * @author Pilar
 */
public class Avanza<S> extends Accion{
  
    private S estado;
    public Avanza(Object source, S st ) 
    {
        super(source);
        this.estado = st;
		
    }
    
}

