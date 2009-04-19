/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.Interprete;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
//import sun.io.Converters;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.TeoriaActividad2.GUI.DrawerActividad;
import ucm.si.TeoriaActividad2.actividad.Actividad;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
//import ucm.si.TeoriaActividad2.actividad.Contexto;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.item.EstadoItem;
import ucm.si.TeoriaActividad2.item.Item;
import ucm.si.TeoriaActividad2.item.ItemGenerator;
import ucm.si.TeoriaActividad2.item.ListaEstadosItems;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Proposicion;
import ucm.si.util.Contexto;

/**
 *
 * @author José Antonio
 */
public class Pruebas2
{
    public static void main(String[] args) throws Exception {

        InterpreteTA ita = new InterpreteTA();
        List<EstadoTA> l = ita.iniciales();
        EstadoTA eta = l.get(0);
        List<EstadoTA> l2 = ita.transitar(eta);
        l2.isEmpty();
        System.out.print("Holaaaaa");


        /*Pruebas p = new Pruebas();
        List<EstadoTA> l = p.transitar(p.iniciales().iterator().next());
        for (Iterator<EstadoTA> it = l.iterator(); it.hasNext();) {
        EstadoTA e = it.next();
        System.out.println(e);
        }*/
        InterpreteTA interprete = new InterpreteTA();
        Proposicion<EstadoTA> nofin = new Proposicion<EstadoTA>() {

            @Override
            public boolean esCierta(EstadoTA s) {
                return true;//s.actividades.getEstado("A2").equals(EstadoActividad.Waiting);
            }
        };
        Proposicion<EstadoTA> fin = new Proposicion<EstadoTA>() {

            @Override
            public boolean esCierta(EstadoTA s) {
                return false;//s.actividades.getEstado("A2").equals(EstadoActividad.Finalized);
            }
        };

        Formula haycamino = new EU(nofin, fin);
        Formula formula = new Not(haycamino);

        Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(new Contexto() {}, interprete, formula);

        launcher.runCheck();

        Drawer dw = new ucm.si.TeoriaActividad2.GUI.DrawerActividad(interprete);
        launcher.launchGrafico(dw);

    }
        /*Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        ItemGenerator itemGen = ItemGenerator.getReference();
        Item[] listaItem1 = {item1, item2};
        Item[] listaItem2 = {item2, item3};
        Actividad actividad1 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem1)), null, null, null, null );
  //              ("A1", listaItem1, null, null, null);
        Actividad actividad2 = new Actividad(new ArrayList<Item>(Arrays.asList(listaItem2)), null, null, null, null );
  //              ("A2", listaItem2, null, null, null);
        ActividadGenerator activGen = ActividadGenerator.getReference();
        itemGen.addItem(item1);
        itemGen.addItem(item2);
        itemGen.addItem(item3);
        activGen.addActividadSimple(actividad1, "A1", "");
        activGen.addActividadSimple(actividad2, "A2", "");

        EstadoTA estadoTA = new EstadoTA();
        estadoTA.setActividades( new ListaEstadosActividades() );
        estadoTA.setItems( new ListaEstadosItems() );
        PseudoEstado estadoIni = new PseudoEstado(estadoTA);

        estadoIni.inicializeEstado();

        backtracking(estadoIni);*/






    

    /*
     * Ejemplo de uso de lanzalbe para saber cuales son
     * los item compartidos con las activiadades padre
     * se procede a usar la funcion de Actividades
     * public List<String> availableArtifacts()
     *
     * Actividad a; PseudoEstado e; List<String> itemsCompartdos;
     * itemsCompartdos = a.availableArtifacts();
     * this.lanzable(a,e,itemsCompartdos);
     */
/*
    public static boolean lanzable(PseudoEstado e, Actividad a)
    {
        boolean b = true;
        EstadoActividad estado =  e.estadoActividad(a);
        // si ya se esta ejecutando no la lazamos
        if( (estado == EstadoActividad.Waiting) || (estado == EstadoActividad.Finalized) )
            return false;

        // Si no tiene un padre podmeos comprobar si se puede lanzar
        Actividad padre = a.getPadre();
        if (padre != null)
        {
            if(e.estadoActividad(padre) != EstadoActividad.Waiting)
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
                EstadoItem ei = e.estadoItem(i);
                if(ei != EstadoItem.FREE)
                {
                    b = false;
                    break;
                }
            }
        }

        return b;
    }
*/
    /**
     * Hay que reisar esta funciona ya que hayq ue hacer una busqueda mas esaustiva en el arbol.
     * por ahora solo se cierra un nivel por transicion
     * @param act
     * @param e
     * @return
     */

    /*public static boolean subActividadesFinalizadas(Actividad act, PseudoEstado e)
    {
        boolean b = false;
        ActividadGenerator ref = ActividadGenerator.getReference();
        List<Actividad> l = act.getSubActividades();
        for (Actividad a : l)
        {
            EstadoActividad ea = e.estadoActividad(a);
            if(ea == EstadoActividad.Waiting)
            {
                // Si hay alguna subactiviada esperando eso significa que aun no han finalizado las subactiviadades
                return false;
            }
        }
        return b;
    }*/

   /* public static PseudoEstado avanza(PseudoEstado e)
    {
        PseudoEstado avance = null;
        avance = (PseudoEstado) e.clone();
        ActividadGenerator ref = ActividadGenerator.getReference();
        for (Actividad a : ref.getConjunto().values())
        {
            EstadoActividad ea = e.estadoActividad(a);
            if(ea == EstadoActividad.Finalized)
            {
                avance.setEstadoActividad(a, EstadoActividad.Idle);
            }

            if(ea == EstadoActividad.Waiting )
            {
                if (subActividadesFinalizadas(a, e))
                {
                    avance.setEstadoActividad(a, EstadoActividad.Finalized);
                }
            }
            
        }
        return avance;
    }*/

    /*public static void backtracking(PseudoEstado e)
    {
        /* suponemos que obtenemos el conjunto de Actividades que podriamos
         * lanzar en el siguiente estado
         */
    /*
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
                //Actividad a = ref.getActividad(st);
                if( estado.lanzable(a))
                {
                    PseudoEstado eAux = (PseudoEstado) estado.clone();
                    eAux.launchActivdad(a);
                    cola.add(eAux);
                    finalized = false;
                }
                

                /*List<EstadoActividad> lista = new ArrayList<EstadoActividad>();

                List<String> listaClaves = new ArrayList<String>();
                listaClaves.add(st);
                estadoNuevo.actividades = new ListaEstadosActividades(lista, listaClaves);*/
           /* }
            if(finalized)
            {
                estado.finalizado = true;
                cola.addToFinal(estado);
            }
        }

        System.out.println(cola);
    }
*/
}
