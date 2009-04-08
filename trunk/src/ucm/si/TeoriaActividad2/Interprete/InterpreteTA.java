/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.Interprete;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.util.StateLabeledList;
import ucm.si.TeoriaActividad2.actividad.Actividad;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.item.Item;
import ucm.si.TeoriaActividad2.item.ItemGenerator;
import ucm.si.TeoriaActividad2.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class InterpreteTA implements Interprete<EstadoTA>{

    public List<EstadoTA> iniciales()
    {
        // Generamos los Items
        Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        Item item4 = new Item("4");
        ItemGenerator itemGen = ItemGenerator.getReference();
        // Generamos las actividades, hayq ue ponerlas en orden primero las actividades padre y luego las hijas
        Item[] listaItem1 = {item1, item2};
        Item[] listaItem2 = {item2, item3};
        Item[] listaItem3 = {item4};
        Actividad[] subhijas = {};
        Actividad actividad1 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem1)), null, null, null, null );
  //              ("A1", listaItem1, null, null, null);
        Actividad actividad2 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem2)), null, null, null, null );
  //              ("A2", listaItem2, null, null, null);
        Actividad actividad3 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem3)), null, null, null, null );
        // añadimos los item y Actividades a los generadores.
        ActividadGenerator activGen = ActividadGenerator.getReference();
        itemGen.addItem(item1);
        itemGen.addItem(item2);
        itemGen.addItem(item3);
        itemGen.addItem(item4);
        activGen.addActividadSimple(actividad1, "A1", "");
        activGen.addActividadSimple(actividad2, "A2", "");
        activGen.addActividadSimple(actividad3, "A3", "A1");

        // Generaos el primer estado
        EstadoTA estadoTA = new EstadoTA();
        estadoTA.setActividades( new ListaEstadosActividades() );
        estadoTA.setItems( new ListaEstadosItems() );
        // Y inicializamos los estados de los item y Actividades
        estadoTA.inicializeEstado();
        //throw new UnsupportedOperationException("Not supported yet.");
        List<EstadoTA> l = new ArrayList<EstadoTA>();
        l.add(estadoTA);

        return l;
    }

    public List<EstadoTA> transitar(EstadoTA state) 
    {
        List<EstadoTA> l = new ArrayList<EstadoTA>();
        PseudoEstado estado = new PseudoEstado(state);
        // hacemos el trsnito con el pseudo estado generado
        Iterator<PseudoEstado> it = this.transitaPseudoEstado(estado);
        //Trnaormamos el Iterator qeu nos devuelve en un List de estadosTA
        while(it.hasNext())
        {
            PseudoEstado pe = it.next();
            l.add(pe.descomponer());
        }
        return l;
    }

    public StateLabeledList<EstadoTA> transitarConEtiqueta(EstadoTA state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> dameTransiciones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Funcionq ue se encarga de hacer todo el trnasito apartir de PseudoEstados
     * @param e
     * @return
     */
    private  Iterator<PseudoEstado> transitaPseudoEstado(PseudoEstado e)
    {
        /* suponemos que obtenemos el conjunto de Actividades que podriamos
         * lanzar en el siguiente estado
         */
        ActividadGenerator ref = ActividadGenerator.getReference();
        PseudoEstado estadoNuevo = new PseudoEstado();
        HashQueue<PseudoEstado> cola = new HashQueue<PseudoEstado>();
        PseudoEstado estadoAvance = e.avanza();
        cola.add(estadoAvance);
        int numActiv = ref.Elements();
        while (!cola.isEmpty())
        {
            PseudoEstado estado = cola.poll();

            boolean finalized = true;
                    //!estado.finalizado;
            for (Actividad a : ref.getConjunto().values()) {
                
                if( estado.lanzable(a))
                {
                    PseudoEstado eAux = (PseudoEstado) estado.clone();
                    eAux.launchActivdad(a);
                    cola.add(eAux);
                    finalized = false;
                }

            }
            if(finalized)
            {
                estado.finalizado = true;
                cola.addToFinal(estado);
            }
        }

        return cola.getFinals();
    }


}
