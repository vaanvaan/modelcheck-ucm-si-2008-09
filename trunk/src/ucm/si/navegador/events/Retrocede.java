/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import java.util.EventObject;

/**
 *
 * @author Pilar
 */
public class Retrocede<S> extends Accion{

    private S estado;
    
    public Retrocede(Object source, S st) {
        super(source);
        this.estado = st;
    }

    
}
