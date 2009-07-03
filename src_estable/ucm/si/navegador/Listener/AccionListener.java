/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador.Listener;

import java.util.EventListener;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public interface  AccionListener<S> extends EventListener
{
    
    public void manejaAccion(Avanza<S> accion);
    public void manejaAccion(GoToEstado<S> accion);
    public void manejaAccion(Retrocede<S> accion);
    
}
