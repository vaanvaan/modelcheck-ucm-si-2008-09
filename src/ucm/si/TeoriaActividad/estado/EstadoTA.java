/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.estado;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import ucm.si.TeoriaActividad.Interprete.Pruebas;
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
    private boolean numerado = false;
    private int hash;

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
                if ((item.getPropietaria()==null)||(!item.getPropietaria().getNombre().equalsIgnoreCase(a))){
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
        if (this==arg0) return 0;
        if (this.hashCode()<arg0.hashCode()){
            return -1;
        } else if (this.hashCode()>arg0.hashCode()){
            return 1;
        } else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoTA other = (EstadoTA) obj;
        return this.compareTo(other)==0;
    }

    @Override
    public int hashCode() {
        if (this.numerado){
            return this.hash;
        } else {
            String[] s1 = this.actividades.keySet().toArray(new String[0]);
            java.util.Arrays.sort(s1);
            int na = 0;
            int pow3 = 1;
            TreeMap<String,String> propinversa = new TreeMap<String,String>();
            for (int i = 0; i < s1.length; i++) {
                na = na*3 + this.actividades.getEstado(s1[i]).ordinal();
                pow3 = 3*pow3;
                if (this.propietarias.containsKey(s1[i])){
                    String[] s = this.propietarias.get(s1[i]).toArray(new String[0]);
                    for (int j = 0; j < s.length; j++) {
                        String item = s[j];
                        propinversa.put(item, s1[i]);
                    }
                }
            }
            String[] s2 = this.items.keySet().toArray(new String[0]);
            java.util.Arrays.sort(s2);
            int nb = 0;
            int nc = 0;
            int pow5 = 1;
            for (int i=0; i<s2.length;i++) {
                nb = nb*5 + this.items.getEstado(s2[i]).ordinal();
                nc = nc*(s1.length+1);
                if (propinversa.containsKey(s2[i])){
                  nc = nc + java.util.Arrays.binarySearch(s1, propinversa.get(s2[i])) + 1;
                }
                pow5 = pow5*5;
            }
            this.hash = nc*pow3*pow5 + (nb*pow3 + na);
            this.numerado = true;
            return hash;
        }
    }




    
    
}
