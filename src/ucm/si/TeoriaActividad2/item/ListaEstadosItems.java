/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Antonio
 */
public class ListaEstadosItems implements  Cloneable
{
    ArrayList<EstadoItem> estado;
    ArrayList<String> clavesEstado;

    public ListaEstadosItems(ArrayList<EstadoItem> estado, ArrayList<String> clavesEstado) {
        this.estado = estado;
        this.clavesEstado = clavesEstado;
    }

    public ListaEstadosItems() {
        int capacidad = ItemGenerator.getReference().Elements();
        this.estado = new ArrayList<EstadoItem>(capacidad);
        this.clavesEstado = new ArrayList<String>(capacidad);
    }

    public EstadoItem getEstado(String clave)
    {
        int indice = clavesEstado.indexOf(clave);
        return estado.get( indice );
    }

    public void setEstado(String clave, EstadoItem estado)
    {
        int indice = clavesEstado.indexOf(clave);
        this.estado.set(indice, estado);
    }

    public void addEstado(String clave, EstadoItem estado)
    {
        if(clavesEstado.contains(clave))
            this.estado.set( clavesEstado.indexOf(clave)  , estado);
        else
        {
            this.estado.add(estado);
            clavesEstado.add(clave);
        }
    }

    public boolean estaItem(String clave){
    	return clavesEstado.contains(clave);
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
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.clavesEstado != other.clavesEstado && (this.clavesEstado == null || !this.clavesEstado.equals(other.clavesEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 67 * hash + (this.clavesEstado != null ? this.clavesEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public Object clone() {

        Object clone = null;
        try {
            clone = super.clone();
            ListaEstadosItems l = (ListaEstadosItems) clone;
            l.clavesEstado = (ArrayList<String>) this.clavesEstado.clone();
            l.estado = (ArrayList<EstadoItem>) this.estado.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ListaEstadosItems.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clone;
    }




}