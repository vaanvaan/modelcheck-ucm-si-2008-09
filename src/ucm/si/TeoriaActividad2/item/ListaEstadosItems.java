/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Antonio
 */
public class ListaEstadosItems implements  Cloneable
{
    HashMap<String,EstadoItem> map;


    public ListaEstadosItems() {
        int capacidad = ItemGenerator.getReference().Elements();
        this.map = new HashMap<String, EstadoItem>(capacidad);
    }

    public EstadoItem getEstado(String clave)
    {
        return this.map.get(clave);
    }

    public void setEstado(String clave, EstadoItem estado)
    {
        this.map.remove(clave);
        this.map.put(clave, estado);
    }

    public void addEstado(String clave, EstadoItem estado)
    {
        if(this.map.containsKey(clave))
            this.setEstado(clave, estado);
            
        else
        {
            this.map.put(clave, estado);
        }
    }

    public boolean containsItem(Item item)
    {
        return this.map.containsKey(item.getClave());
    }

    public boolean containsItem(String claveItem)
    {
        return this.map.containsKey(claveItem);
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListaEstadosItems other = (ListaEstadosItems) obj;
        if (this.map != other.map && (this.map == null || !this.map.equals(other.map))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.map != null ? this.map.hashCode() : 0);
        return hash;
    }


    @Override
    public Object clone() {

        Object clone = null;
        try {
            clone = super.clone();
            ListaEstadosItems l = (ListaEstadosItems) clone;
            l.map = (HashMap<String, EstadoItem>) this.map.clone();
            //l.clavesEstado = (ArrayList<String>) this.clavesEstado.clone();
            //l.estado = (ArrayList<EstadoItem>) this.estado.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ListaEstadosItems.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clone;
    }




}