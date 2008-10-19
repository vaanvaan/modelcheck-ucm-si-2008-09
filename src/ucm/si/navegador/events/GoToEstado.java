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
public class GoToEstado extends Accion {
    
    private Estado estado;
    public GoToEstado(Object source, Estado st ) 
    {
        super(source);
        this.estado = st;
		
    }

}
