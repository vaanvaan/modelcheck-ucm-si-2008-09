/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author José Antonio
 */
public class ListaEstadosItems
{
    List<EstadoItem> estado;
    List<String> clavesEstado;

    public ListaEstadosItems(ListaEstadosItems items) {
        this.estado = new ArrayList<EstadoItem>(items.estado);
        this.clavesEstado = new ArrayList<String>(items.clavesEstado);
    }

    public ListaEstadosItems(List<EstadoItem> estado, List<String> clavesEstado) {
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
        int indice = this.clavesEstado.indexOf(clave);
        return this.estado.get( indice );
    }

    public void setEstado(String clave, EstadoItem estado)
    {
        int indice = this.clavesEstado.indexOf(clave);
        this.estado.set(indice, estado);
    }

    public void addEstado(String clave, EstadoItem estado)
    {
        if(this.clavesEstado.contains(clave))
            this.estado.set( this.clavesEstado.indexOf(clave)  , estado);
        else
        {
            this.estado.add(estado);
            this.clavesEstado.add(clave);
        }
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


}