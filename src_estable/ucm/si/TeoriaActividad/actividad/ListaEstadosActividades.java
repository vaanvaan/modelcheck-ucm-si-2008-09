/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.actividad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Antonio
 *
 * Se encarga de
 */
public class ListaEstadosActividades
{
    List<EstadoActividad> estado;
    List<String> clavesActividad;

    public ListaEstadosActividades(ListaEstadosActividades actividades) {
        this.estado = new ArrayList<EstadoActividad>(actividades.estado);
        this.clavesActividad = new ArrayList<String>(actividades.clavesActividad);
    }

    public ListaEstadosActividades(List<EstadoActividad> estado, List<String> clavesEstado) {
        this.estado = estado;
        this.clavesActividad = clavesEstado;
    }

    public ListaEstadosActividades() {
        int capacidad = ActividadGenerator.getReference().Elements();
        this.estado = new ArrayList<EstadoActividad>(capacidad);
        this.clavesActividad = new ArrayList<String>(capacidad);
    }

    public List<String> keySet() {
        return this.clavesActividad;
    }

    public int size()
    {
        return this.estado.size();
    }

    public EstadoActividad getEstado(String clave)
    {
        int indice = this.clavesActividad.indexOf(clave);
        return this.estado.get( indice );
    }

    public void setEstado(String clave, EstadoActividad estado)
    {
        int indice = this.clavesActividad.indexOf(clave);
        this.estado.set(indice, estado);
    }

    public void addEstado(String clave, EstadoActividad estado)
    {
        if(this.clavesActividad.contains(clave))
            this.estado.set( this.clavesActividad.indexOf(clave)  , estado);
        else
        {
            this.estado.add(estado);
            this.clavesActividad.add(clave);
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
        final ListaEstadosActividades other = (ListaEstadosActividades) obj;
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.clavesActividad != other.clavesActividad && (this.clavesActividad == null || !this.clavesActividad.equals(other.clavesActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 73 * hash + (this.clavesActividad != null ? this.clavesActividad.hashCode() : 0);
        return hash;
    }



}
