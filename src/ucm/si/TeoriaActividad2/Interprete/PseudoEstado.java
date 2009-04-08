/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.Interprete;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.TeoriaActividad2.actividad.Actividad;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad2.actividad.Contexto;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.item.EstadoItem;
import ucm.si.TeoriaActividad2.item.Item;
import ucm.si.TeoriaActividad2.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class PseudoEstado implements Cloneable{
    public boolean finalizado = false;
    public EstadoTA estado;

    public PseudoEstado(EstadoTA estado) {
        this.estado = estado;
    }

    public PseudoEstado()
    {
        this.estado = new EstadoTA();
    }

    public boolean isEmpty()
    {
        return this.estado.sizeActividades() == 0;
    }

    /*
     *  generar la logica para lanzar estados
    private
    */
    public List<String> ActividadesThrowables()
    {
        List<String> c = new ArrayList<String>();
        ActividadGenerator ag = ActividadGenerator.getReference();
        for( String s : ag.getConjunto().keySet())
        {
            Actividad a = ag.getActividad(s);
            EstadoActividad estadoA = this.estado.getEstadoActividad(s);
            boolean b = true;
            if(estadoA == EstadoActividad.Idle)
            {
                 b = a.CondicionesSatisfy(Contexto.getReference());
                 
                 // Colocar aqui el calculo segun estados
            }
            //Actividad a= ag.getItem(s);

        }

        return c;
    }

    public EstadoActividad estadoActividad(Actividad a)
    {
        return this.estado.getEstadoActividad(a.getNombre());
    }

    public void setEstadoActividad(Actividad a, EstadoActividad estadoActividad)
    {
        this.setEstadoActividad(a.getNombre(), estadoActividad);
    }

    public void setEstadoActividad(String nombre, EstadoActividad estadoActividad)
    {
        this.estado.setEstadoActividad(nombre, estadoActividad);
    }

    public EstadoItem estadoItem(Item i)
    {
        return this.estado.getEstadoItem(i.getClave());
    }

    /**
     * Lanza una actividad
     * @param a
     */
    public void launchActivdad(Actividad a)
    {
        List<Item> l = a.getItemNecesarios();
        for(Item i : l)
        {
            this.setEstadoItem(i.getClave(), EstadoItem.BUSY);
        }

        if(a.getSubActividades().isEmpty())
        {
            this.setEstadoActividad(a.getNombre(), EstadoActividad.Finalized);
        }
        else
        {
            this.setEstadoActividad(a.getNombre(), EstadoActividad.Waiting);
        }
    }

    /**
     * Funcion que se encarga de realizar los avances de las actividades que no dependen de los items
     * es decri trnsiciones de finalizad a idle o que una funcion padre pase a idle cunado todas sus hijas esten en idle o finalized
     * @return
     */
    public PseudoEstado avanza()
    {
        PseudoEstado avance = null;
        avance = (PseudoEstado) this.clone();
        ActividadGenerator ref = ActividadGenerator.getReference();
        for (Actividad a : ref.getConjunto().values())
        {
            EstadoActividad ea = this.estadoActividad(a);
            if(ea == EstadoActividad.Finalized)
            {
                avance.setEstadoActividad(a, EstadoActividad.Idle);
            }

            if(ea == EstadoActividad.Waiting )
            {
                if (this.subActividadesFinalizadas(a))
                {
                    avance.setEstadoActividad(a, EstadoActividad.Finalized);
                }
            }

        }
        return avance;
    }

    /**
     *  Esta funcion se encarga de hacer la trancision de finalizad a idle para una Actividad en concreto
     * @param a
     */
    public void finalizarActividad(Actividad a)
    {
        for (Item i : a.getItemToDispose())
        {
            this.setEstadoItem(i, EstadoItem.DISPOSED);
        }

        for (Item i : a.getItemToGenerate())
        {
            EstadoItem ei = this.getEstadoItem(i);
            if(ei == EstadoItem.DISPOSED)
                this.setEstadoItem(i, EstadoItem.FREE);
            // Podemos lanzar un warning a un log para saver que se ha duplicado un Item ya existente

        }
    }

    public EstadoItem getEstadoItem(Item i)
    {
        return this.getEstadoItem(i.getClave());
    }
    public EstadoItem getEstadoItem(String clave)
    {
        return this.estado.getEstadoItem(clave);
    }

     public void setEstadoItem(String clave, EstadoItem ei)
    {
        this.estado.setEstadoItem(clave, ei);
    }
    public void setEstadoItem(Item i, EstadoItem ei)
    {
        this.setEstadoItem(i.getClave(), ei);
    }

    /**
     * Consulta si una Actividad a, se puede lanzar en un estado actual de los item
     * @param a
     * @return
     */
    public boolean lanzable( Actividad a)
    {
        boolean b = true;
        EstadoActividad estado =  this.estadoActividad(a);
        // si ya se esta ejecutando no la lazamos
        if( (estado == EstadoActividad.Waiting) || (estado == EstadoActividad.Finalized) )
            return false;

        // Si no tiene un padre podemos comprobar si se puede lanzar
        Actividad padre = a.getPadre();
        if (padre != null)
        {
            if(this.estadoActividad(padre) != EstadoActividad.Waiting)
                return false;
            // Si la activiada esta en estado idle entonces podemos lanzarla
        }

        List<Item> l =  a.getItemNecesarios();
        ArrayList li;

        for (Item i :l)
        {
            List<String> itemsCompartidos = a.getItemAccesibles();
            // Si no esta entre los items compartidos entocnes hayq ue ver que este disponible
            if(!itemsCompartidos.contains(i.getClave()))
            {
                // Si el estado no esta en estado free significa que al menos un item no esta accesible
                EstadoItem ei = this.estadoItem(i);
                if(ei != EstadoItem.FREE)
                {
                    b = false;
                    break;
                }
            }
        }

        return b;
    }

    /**
     * Funcion que pregunta para un PseudoEstado si todas sus subactividades estan finalizadas
     * @param act
     * @return
     */
    public boolean subActividadesFinalizadas(Actividad act)
    {
        boolean b = false;
        ActividadGenerator ref = ActividadGenerator.getReference();
        List<Actividad> l = act.getSubActividades();
        for (Actividad a : l)
        {
            EstadoActividad ea = this.estadoActividad(a);
            if(ea == EstadoActividad.Waiting)
            {
                // Si hay alguna subactiviada esperando eso significa que aun no han finalizado las subactiviadades
                return false;
            }
        }
        return b;
    }

    public void  launchActividad (String nombre)
    {
        Actividad a = ActividadGenerator.getReference().getActividad(nombre);
        this.launchActivdad(a);
    }

    @Override
    public Object clone()
    {
         Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(PseudoEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
        PseudoEstado e = (PseudoEstado) clone;

            e.finalizado = this.finalizado;
            e.estado = (EstadoTA) this.estado.clone();
 
        return e;
    }

    public void setItems(ListaEstadosItems items) {
        estado.setItems(items);
    }

    public void setActividades(ListaEstadosActividades actividades) {
        estado.setActividades(actividades);
    }

    public int sizeActividades() {
        return estado.sizeActividades();
    }

    public void inicializeEstado() {
        estado.inicializeEstado();
    }

    public EstadoTA getEstado() {
        return estado;
    }

    public void setEstado(EstadoTA estado) {
        this.estado = estado;
    }

    public EstadoTA descomponer()
    {
        EstadoTA e = this.estado;
        this.estado = null;
        return e;
    }



}
