/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.actividad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author José Antonio
 *
 * Se encarga de
 */
public class ListaEstadosActividades implements Cloneable
{
    HashMap <String, EstadoActividad> map;

    public ListaEstadosActividades() {
        int capacidad = ActividadGenerator.getReference().Elements();
        this.map = new HashMap<String, EstadoActividad>(capacidad);
    }

    public int size()
    {

        return this.map.size();
    }

    public EstadoActividad getEstado(String clave)
    {
        return this.map.get(clave);
    }
    
    public boolean isActivity(String clave){
    	return map.containsKey(clave);
    }

    public void setEstado(String clave, EstadoActividad estado)
    {
        this.map.remove(clave);
        this.map.put(clave, estado);
    }

    public void addEstado(String clave, EstadoActividad estado)
    {
        if(this.map.containsKey(clave))
            this.setEstado(clave, estado);
        else
        {
            this.map.put(clave, estado);
        }
    }

    public Set<String> keySet()
    {
        return this.map.keySet();
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Object clone = null;
        clone = super.clone();
        ListaEstadosActividades l = (ListaEstadosActividades) clone;
        this.map = (HashMap<String, EstadoActividad>) l.map.clone();
        return l;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListaEstadosActividades other = (ListaEstadosActividades) obj;
        if (this.map != other.map && (this.map == null || !this.map.equals(other.map))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.map != null ? this.map.hashCode() : 0);
        return hash;
    }











}
