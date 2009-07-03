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
 * @author Niko, Jose Antonio, Ivan
 */
public class Avanza<S> extends Accion<S>{
  
    private S estado;
    public Avanza(S source, S st ) 
    {
        super(source);
        this.estado = st;
		
    }

    public S getEstado() {
        return estado;
    }
    
    
}

