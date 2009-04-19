/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad2.estado;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.TeoriaActividad2.actividad.Actividad;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.item.EstadoItem;
import ucm.si.TeoriaActividad2.item.Item;
import ucm.si.TeoriaActividad2.item.ItemGenerator;
import ucm.si.TeoriaActividad2.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class EstadoTA implements Cloneable, Comparable<EstadoTA> {

    private ListaEstadosItems items;
    private ListaEstadosActividades actividades;
    private boolean numerado = false;
    private LinkedList<Integer> nums;

    public EstadoTA() {
        this.items = new ListaEstadosItems();
        this.actividades = new ListaEstadosActividades();
    }

    public void inicializeEstado() {
        ActividadGenerator refAct = ActividadGenerator.getReference();
        for (Actividad a : refAct.getConjunto().values()) {
            this.actividades.addEstado(a.getNombre(), EstadoActividad.Idle);
        }

        ItemGenerator refItm = ItemGenerator.getReference();
        for (Item a : refItm.getConjunto().values()) {
            this.items.addEstado(a.getClave(), EstadoItem.FREE);
        }
    }

    public void setEstadoItem(String clave, EstadoItem estado) {
        items.setEstado(clave, estado);
    }

    public EstadoItem getEstadoItem(String clave) {
        return items.getEstado(clave);
    }

    public void addEstadoItem(String clave, EstadoItem estado) {
        items.addEstado(clave, estado);
    }

    public void setEstadoActividad(String clave, EstadoActividad estado) {
        actividades.setEstado(clave, estado);
    }

    public EstadoActividad getEstadoActividad(String clave) {
        return actividades.getEstado(clave);
    }

    public void addEstadoActividad(String clave, EstadoActividad estado) {
        actividades.addEstado(clave, estado);
    }

    public int sizeActividades() {
        return actividades.size();
    }

    public ListaEstadosActividades getActividades() {
        return actividades;
    }

    public void setActividades(ListaEstadosActividades actividades) {
        this.actividades = actividades;
    }

    public ListaEstadosItems getItems() {
        return items;
    }

    public void setItems(ListaEstadosItems items) {
        this.items = items;
    }

    @Override
    public int compareTo(EstadoTA arg0) {
        if (this == arg0) {
            return 0;
        }
        if (!this.numerado) this.numerar();
        if (!arg0.numerado) arg0.numerar();
        int a = this.nums.size();
        int b = arg0.nums.size();
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        for (int i = 0; i < a; i++) {
            Integer nthis = this.nums.get(i);
            Integer nother = arg0.nums.get(i);
            if (nthis<nother){
                return -1;
            }
            if (nthis>nother){
                return 1;
            }
        }
        return 0;
    }

    private void numerar() {
        String[] s1 = this.actividades.keySet().toArray(new String[0]);
        java.util.Arrays.sort(s1);
        int na = 0;
        this.nums = new LinkedList<Integer>();
        int pow3 = 1;
        //TreeMap<String, String> propinversa = new TreeMap<String, String>();
        for (int i = 0; i < s1.length; i++) {
            na = na * 3 + this.actividades.getEstado(s1[i]).ordinal();
            pow3 = 3 * pow3;
            /*if (this.propietarias.containsKey(s1[i])) {
            String[] s = this.propietarias.get(s1[i]).toArray(new String[0]);
            for (int j = 0; j < s.length; j++) {
            String item = s[j];
            propinversa.put(item, s1[i]);
            }
            }*/
            if (pow3 >= Integer.MAX_VALUE / 3) {
                this.nums.add(new Integer(na));
                pow3 = 1;
                na = 0;
            }
        }
        if (pow3>1){
            this.nums.add(new Integer(na));
            pow3 = 1;
            na = 0;
        }
        String[] s2 = this.items.keySet().toArray(new String[0]);
        java.util.Arrays.sort(s2);
        for (int i = 0; i < s2.length; i++) {
            na = na * 3 + this.items.getEstado(s2[i]).ordinal();
            pow3 = pow3 * 3;
            if (pow3 >= Integer.MAX_VALUE / 3) {
                this.nums.add(new Integer(na));
                pow3 = 1;
                na = 0;
            }
        }
        if (pow3>1){
            this.nums.add(new Integer(na));
            pow3 = 1;
            na = 0;
        }
        this.numerado = true;
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
        if (this.items != other.items && (this.items == null || !this.items.equals(other.items))) {
            return false;
        }
        if (this.actividades != other.actividades && (this.actividades == null || !this.actividades.equals(other.actividades))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.items != null ? this.items.hashCode() : 0);
        hash = 53 * hash + (this.actividades != null ? this.actividades.hashCode() : 0);
        return hash;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();


            EstadoTA eta = (EstadoTA) clone;
            eta.actividades = (ListaEstadosActividades) this.actividades.clone();
            eta.items = (ListaEstadosItems) this.items.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(EstadoTA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clone;
    }
}
