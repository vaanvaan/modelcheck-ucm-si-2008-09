/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.estado;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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
public class EstadoTA implements IEstadoDrawable, Comparable<EstadoTA>
{
    public ListaEstadosItems items;
    public ListaEstadosActividades actividades;
    public TreeMap<String,Set<String>> propietarias;

    public EstadoTA(ListaEstadosItems items, ListaEstadosActividades actividades, TreeMap<String,Set<String>> propietarias) {
        this.items = items;
        this.actividades = actividades;
        this.propietarias = propietarias;
    }

    public EstadoTA(EstadoTA eini) {
        this.actividades = new ListaEstadosActividades(eini.actividades);
        this.items = new ListaEstadosItems(eini.items);
        this.propietarias = new TreeMap<String,Set<String>>();
        for (Iterator<String> it = eini.propietarias.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            this.propietarias.put(s, new TreeSet<String>(eini.propietarias.get(s)));
        }
    }
    

    public void lanzarPosibles(Pruebas p) {
        for (Iterator<String> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();            
            for (Iterator<String> it2 = this.propietarias.get(a).iterator(); it2.hasNext();) {
                String itemaux = it2.next();
                this.items.setEstado(itemaux, EstadoItem.BUSY);
                p.itemGen.getItem(itemaux).setPropietaria(p.activGen.getItem(a));
            }
        }
        for (Iterator<String> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();            
            Item[] itemsNecesarios = p.activGen.getItem(a).getItemNecesarios();
            boolean lanzar=true;
            int i=0;
            while (lanzar&&(i<itemsNecesarios.length)){
                Item item = itemsNecesarios[i];
                if (!item.getPropietaria().getNombre().equalsIgnoreCase(a)){
                    lanzar=false;
                }
                i++;
            }
            if (lanzar) {
                this.actividades.setEstado(a, EstadoActividad.Executing);
            } else {
                this.actividades.setEstado(a, EstadoActividad.Waiting);
            }
        }
        for (Iterator<String> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();            
            for (Iterator<String> it2 = this.propietarias.get(a).iterator(); it2.hasNext();) {
                String itemaux = it2.next();
                p.itemGen.getItem(itemaux).setPropietaria(null);
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer strbuf = new StringBuffer();        
        for (Iterator<String> it = this.actividades.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            strbuf.append(s);
            strbuf.append(" => ");
            strbuf.append(actividades.getEstado(s) + ", ");            
        }
        strbuf.append("\n");
        /*
        for (Iterator<String> it = this.items.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            strbuf.append(s);
            strbuf.append(" => ");
            strbuf.append(items.getEstado(s) + ", ");
            if (items.getEstado(s)==EstadoItem.BUSY){
                
            }
        }*/
        for (Iterator<String> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();
            strbuf.append(a);
            strbuf.append(" tiene ");
            for (Iterator<String> it2 = this.propietarias.get(a).iterator(); it2.hasNext();) {
                String s = it2.next();
                strbuf.append(s+" ");
                strbuf.append(", ");                
            }
            strbuf.append("\n");
        }
        return strbuf.toString();
    }

    public EstadoItem getEstadoItem(String item) {
        return this.items.getEstado(item);
    }

    public EstadoActividad getEstadoActividad(String actividad) {
        return this.actividades.getEstado(actividad);
    }

    public String[] getItemsPoseidos(String actividad) {
        return this.propietarias.get(actividad).toArray(new String[0]);
    }

    public int compareTo(EstadoTA arg0) {
        for (Iterator<String> it = arg0.actividades.keySet().iterator(); it.hasNext();) {
            String a = it.next();
            int aux = arg0.actividades.getEstado(a).compareTo(this.actividades.getEstado(a));
            if (aux!=0) return aux;
        }
        for (Iterator<String> it = arg0.items.keySet().iterator(); it.hasNext();) {
            String item = it.next();
            int aux = arg0.items.getEstado(item).compareTo(this.items.getEstado(item));
            if (aux!=0) return aux;
        }
        return 0;
    }
    
    
    
}
