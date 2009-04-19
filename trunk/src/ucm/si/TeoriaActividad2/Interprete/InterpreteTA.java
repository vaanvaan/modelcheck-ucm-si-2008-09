/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.Interprete;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.util.StateLabeledList;
import ucm.si.TeoriaActividad2.actividad.Actividad;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
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

    ArrayList<String> listaTransiciones = null;

    public List<EstadoTA> iniciales()
    {
        // Generamos los Items
        Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        Item item4 = new Item("4");
        Item item5 = new Item("5");
        Item item6 = new Item("6");
        ItemGenerator itemGen = ItemGenerator.getReference();
        // Generamos las actividades, hayq ue ponerlas en orden primero las actividades padre y luego las hijas
        /*Item[] listaItem1 = {item1, item2};
        Item[] listaItem2 = {item2, item3};
        Item[] listaItem3 = {item2};*/
        Item[] listaItem1 = {item1, item2, item3};
        Item[] listaItem2 = {item1, item2, item4};
        Item[] listaItem3 = {item3, item4, item5};
        Item[] listaItem4 = {item1, item6, item3};
        Actividad[] subhijas = {};
        /*Actividad actividad1 = new Actividad("A1", listaItem1, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);
        Actividad actividad2 = new Actividad("A2", listaItem2, new Item[0], new Item[0], new Item[0], new Item[0], new Item[]{item4}, new Item[0], new Conditions[0]);
        Actividad actividad3 = new Actividad("A3", listaItem3, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);
        Actividad actividad4 = new Actividad("A4", listaItem4, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);*/
        Actividad actividad1 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem1)), null, null, null, null );
  //              ("A1", listaItem1, null, null, null);
        Actividad actividad2 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem2)), null, null, null, null );
  //              ("A2", listaItem2, null, null, null);
        Actividad actividad3 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem3)), null, null, null, null );
        Actividad actividad4 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem4)), null, null, null, null );
        // añadimos los item y Actividades a los generadores.

        ActividadGenerator activGen = ActividadGenerator.getReference();
        itemGen.addItem(item1);
        itemGen.addItem(item2);
        itemGen.addItem(item3);
        itemGen.addItem(item4);
        activGen.addActividadSimple(actividad1, "A1", "");
        activGen.addActividadSimple(actividad2, "A2", "");
        activGen.addActividadSimple(actividad3, "A3", "A2");
        activGen.addActividadSimple(actividad4, "A4", "A2");

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
        List<EstadoTA> laux = this.transitar(state);
        ArrayList<String> laux2 = new ArrayList<String>();
        //for (Iterator<EstadoTA> it = laux.iterator(); it.hasNext();) {
        for(EstadoTA e : laux){
            //EstadoTA e = it.next();
            laux2.add(nombreTransicion(state, e));
        }
        return new StateLabeledList<EstadoTA>(laux, laux2);
    }

    public List<String> dameTransiciones() {
        if (listaTransiciones == null) {
            Queue<EstadoTA[]> q = new LinkedBlockingQueue<EstadoTA[]>();
            TreeSet<EstadoTA[]> ts = new TreeSet<EstadoTA[]>(new Comparator() {

                public int compare(Object arg0, Object arg1) {
                    EstadoTA[] c1 = (EstadoTA[]) arg0;
                    EstadoTA[] c2 = (EstadoTA[]) arg1;
                    int a = c1[0].compareTo(c2[0]);
                    //int a = 0;
                    if (a != 0) {
                        return a;
                    } else {
                        return c1[1].compareTo(c2[1]);
                        //return 1;
                    }
                }
            });
            for (EstadoTA e : this.iniciales()) {
                q.add(new EstadoTA[]{null, e});
            }
            listaTransiciones = new ArrayList<String>();
            while (!q.isEmpty()) {
                EstadoTA[] arraye = q.poll();
                EstadoTA epadre = arraye[0];
                EstadoTA ehijo = arraye[1];
                if (epadre == null) {
                    //ts.add(ehijo);
                    for (EstadoTA eaux : transitar(ehijo)) {
                        q.add(new EstadoTA[]{ehijo, eaux});
                    }
                } else {
                    EstadoTA[] trans = new EstadoTA[]{epadre, ehijo};
                    if (!ts.contains(trans)) {
                        String s = nombreTransicion(epadre, ehijo);
                        if (!listaTransiciones.contains(s)) {
                            listaTransiciones.add(s);
                        }
                        ts.add(trans);
                        for (EstadoTA eaux : transitar(ehijo)) {
                            q.add(new EstadoTA[]{ehijo, eaux});
                        }
                    }
                }
            }
        }
        return listaTransiciones;
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

    private String nombreTransicion(EstadoTA eini, EstadoTA efin) {
        StringBuffer strbuf = new StringBuffer();
        StringBuffer strbuf2 = new StringBuffer();
        ActividadGenerator ref = ActividadGenerator.getReference();
        for ( String s : ref.getConjunto().keySet())
        {
            EstadoActividad estadoEIni = eini.getEstadoActividad(s);
            EstadoActividad estadoEFin = efin.getEstadoActividad(s);

            if (!eini.getEstadoActividad(s).equals(efin.getEstadoActividad(s))) {
                strbuf.append(s + "->" + efin.getEstadoActividad(s).toString() + ", ");
            }
            Actividad a = ref.getActividad(s);
            List<Item> l = a.getItemNecesarios();
            if( estadoEFin == EstadoActividad.Waiting)
            {
                strbuf2.append(s + " posee ");
                for(Item i : l)
                {
                    strbuf2.append(i.getClave() + ", ");
                }

            }
            else if( estadoEFin == EstadoActividad.Finalized)
            {
                strbuf2.append(s + " suelta ");
                        for (Item i : l) {
                            strbuf2.append(i.getClave() + ", ");
                        }
            }
            /*if ((eini.getItemsPoseidos(s) == null) ||
                    (efin.getItemsPoseidos(s) == null) ||
                    (!Arrays.equals(eini.getItemsPoseidos(s), efin.getItemsPoseidos(s)))) {
                String[] itemsp = efin.getItemsPoseidos(s);
                if (itemsp != null) {
                    java.util.Arrays.sort(itemsp);
                    strbuf2.append(s + " posee ");
                    for (int i = 0; i < itemsp.length; i++) {
                        strbuf2.append(itemsp[i] + ", ");
                    }
                } else {
                    itemsp = eini.getItemsPoseidos(s);
                    if (itemsp != null) {
                        java.util.Arrays.sort(itemsp);
                        strbuf2.append(s + " suelta ");
                        for (int i = 0; i < itemsp.length; i++) {
                            strbuf2.append(itemsp[i] + ", ");
                        }
                    }

                }
            }*/
        }
        if ((strbuf.length() == 0) && (strbuf2.length() == 0)) {
            return efin.toString();
        } else {
            return strbuf.append(strbuf2).toString();
        }
    }





}
