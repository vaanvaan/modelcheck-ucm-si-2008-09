/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import ucm.si.Checker.*;
import java.util.EventObject;

/**
 *
 * @author Pilar
 */
public class GoToEstado<S> extends Accion {
    
    private S estado;
    public GoToEstado(Object source, S st ) 
    {
        super(source);
        this.estado = st;
		
    }

}
