/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.estado;

import ucm.si.TeoriaActividad2.estado.*;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.item.EstadoItem;

/**
 *
 * @author nico
 */
public interface IEstadoDrawable {
    
    public EstadoItem getEstadoItem(String item);
    public EstadoActividad getEstadoActividad(String actividad);
    
}
