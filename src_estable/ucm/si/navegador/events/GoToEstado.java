/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import ucm.si.Checker.*;
import java.util.EventObject;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class GoToEstado<S> extends Accion<S> {
    
    private S estado;
    public GoToEstado(S source, S st ) 
    {
        super(source);
        this.estado = st;
		
    }

    public S getEstado() {
        return estado;
    }
    
    

}
