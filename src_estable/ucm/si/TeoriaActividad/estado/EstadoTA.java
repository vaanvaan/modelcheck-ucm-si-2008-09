/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.estado;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import ucm.si.TeoriaActividad.Interprete.InterpreteTA;
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
public class EstadoTA implements Comparable<EstadoTA> {

    public ListaEstadosItems items;
    public ListaEstadosActividades actividades;
    public TreeMap<String, Set<String>> propietarias;
    private boolean numerado = false;
    private TreeMap<String, Set<ItemRole>> historiaRoles;
    private LinkedList<Integer> nums;

    public EstadoTA(ListaEstadosItems items, ListaEstadosActividades actividades,
            TreeMap<String, Set<String>> propietarias) {
        this.items = items;
        this.actividades = actividades;
        this.propietarias = propietarias;
        this.historiaRoles = new TreeMap<String, Set<ItemRole>>();
        for (Iterator<String> it = this.items.keySet().iterator(); it.hasNext();) {
            String item = it.next();
            this.historiaRoles.put(item, new TreeSet<ItemRole>());
        }
        for (Iterator<String> it = this.actividades.keySet().iterator(); it.hasNext();) {
            String actividad = it.next();
            this.historiaRoles.put(actividad, new TreeSet<ItemRole>());
        }
    }

    public EstadoTA(EstadoTA eini) {
        this.actividades = new ListaEstadosActividades(eini.actividades);
        this.items = new ListaEstadosItems(eini.items);
        this.propietarias = new TreeMap<String, Set<String>>();
        for (Iterator<String> it = eini.propietarias.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            this.propietarias.put(s, new TreeSet<String>(eini.propietarias.get(s)));
        }
        this.historiaRoles = new TreeMap<String, Set<ItemRole>>();
        for (Iterator<String> it = eini.historiaRoles.keySet().iterator(); it.hasNext();) {
            String s = it.next();
            this.historiaRoles.put(s, new TreeSet<ItemRole>(eini.historiaRoles.get(s)));
        }
    }

    public void lanzarPosibles(InterpreteTA p) {
        for (Iterator<String> it = this.propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();
            if (this.getEstadoActividad(a).equals(EstadoActividad.Waiting)) {
                for (Iterator<String> it2 = this.propietarias.get(a).iterator(); it2.hasNext();) {
                    String itemaux = it2.next();
                    this.items.setEstado(itemaux, EstadoItem.BUSY);
                }
                this.actividades.setEstado(a, EstadoActividad.Executing);
                this.marcarRoles(p.activGen.getItem(a));
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
                strbuf.append(s + " ");
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
        if (!this.propietarias.containsKey(actividad)) {
            return null;
        } else {
            return this.propietarias.get(actividad).toArray(new String[0]);
        }
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoTA other = (EstadoTA) obj;
        return this.compareTo(other) == 0;
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

    public Set<ItemRole> getRoles(String item) {
        return historiaRoles.get(item);
    }

    public void addRole(String item, ItemRole role) {
        this.historiaRoles.get(item).add(role);
    }

    public boolean was(String item, ItemRole role) {
        return this.historiaRoles.get(item).contains(role);
    }

    private void marcarRoles(Actividad a) {
        for (int i = 0; i < a.getSubjects().length; i++) {
            String item = a.getSubjects()[i].getClave();
            this.addRole(item, ItemRole.Subject);
        }
        for (int i = 0; i < a.getObjects().length; i++) {
            String item = a.getObjects()[i].getClave();
            this.addRole(item, ItemRole.Object);
        }
        for (int i = 0; i < a.getTools().length; i++) {
            String item = a.getTools()[i].getClave();
            this.addRole(item, ItemRole.Tool);
        }
        for (int i = 0; i < a.getObjetives().length; i++) {
            String item = a.getObjetives()[i].getClave();
            this.addRole(item, ItemRole.Objetive);
        }
        for (int i = 0; i < a.getOutcomes().length; i++) {
            String item = a.getOutcomes()[i].getClave();
            this.addRole(item, ItemRole.Outcome);
        }
    }
}
