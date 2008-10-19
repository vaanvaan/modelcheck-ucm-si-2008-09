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
 * @author Pilar
 */
public interface  AccionListener extends EventListener
{
    
    public void manejaAccion(Avanza accion);
    public void manejaAccion(GoToEstado accion);
    public void manejaAccion(Retrocede accion);
    
}
