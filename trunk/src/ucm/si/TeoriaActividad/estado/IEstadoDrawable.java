/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.estado;

import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.item.EstadoItem;

/**
 *
 * @author nico
 */
public interface IEstadoDrawable {
    
    public EstadoItem getEstadoItem(String item);
    public EstadoActividad getEstadoActividad(String actividad);
    
}
