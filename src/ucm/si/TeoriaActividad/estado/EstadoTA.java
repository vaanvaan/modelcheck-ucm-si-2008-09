/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.estado;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import ucm.si.TeoriaActividad.Interprete.Pruebas;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class EstadoTA
{
    public ListaEstadosItems items;
    public ListaEstadosActividades actividades;
    public TreeMap<Actividad,Set<String>> propietarias;

    public EstadoTA(ListaEstadosItems items, ListaEstadosActividades actividades, TreeMap<Actividad,Set<String>> propietarias) {
        this.items = items;
        this.actividades = actividades;
        this.propietarias = propietarias;
    }

    public EstadoTA(EstadoTA eini) {
        this.actividades = new ListaEstadosActividades(eini.actividades);
        this.items = new ListaEstadosItems(eini.items);
        this.propietarias = new TreeMap<Actividad,Set<String>>(eini.propietarias);
    }
    

    public void lanzarPosibles(Pruebas p) {
        for (Iterator<Actividad> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            Actividad a = it.next();            
            for (Iterator<String> it2 = this.propietarias.get(a).iterator(); it2.hasNext();) {
                String itemaux = it2.next();
                this.items.setEstado(itemaux, EstadoItem.BUSY);
                p.itemGen.getItem(itemaux).setPropietaria(a);
            }
        }
        for (Iterator<Actividad> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            Actividad a = it.next();            
            Item[] itemsNecesarios = a.getItemNecesarios();
            boolean lanzar=true;
            int i=0;
            while (lanzar&&(i<itemsNecesarios.length)){
                Item item = itemsNecesarios[i];
                if (!item.getPropietaria().getNombre().equalsIgnoreCase(a.getNombre())){
                    lanzar=false;
                }
                i++;
            }
            if (lanzar) {
                this.actividades.setEstado(a.getNombre(), EstadoActividad.Idle);
            } else {
                this.actividades.setEstado(a.getNombre(), EstadoActividad.Waiting);
            }
        }
    }
    
}
