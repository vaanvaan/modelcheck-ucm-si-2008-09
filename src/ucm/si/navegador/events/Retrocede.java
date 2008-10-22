/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import java.util.EventObject;
import ucm.si.Checker.Estado;

/**
 *
 * @author Pilar
 */
public class Retrocede extends Accion{

    private Estado estado;
    
    public Retrocede(Object source, Estado st) {
        super(source);
        this.estado = st;
    }

    
}
