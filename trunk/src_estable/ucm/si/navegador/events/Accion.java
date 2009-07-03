/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.events;

import java.util.EventObject;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class Accion<S> extends EventObject{

    public Accion(S source)
    {
        super(source);
    }
}
