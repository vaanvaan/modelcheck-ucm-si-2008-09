/**
 * 
 */
package ucm.si.TeoriaActividad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.TeoriaActividad.GUI.DrawerActividad;
import ucm.si.TeoriaActividad.Interprete.SistemaActividades;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad.actividad.Condition;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;
import ucm.si.TeoriaActividad.proposiciones.ProposicionActividad;
import ucm.si.animadorGUI.AnimadorGrafico;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Proposicion;
import ucm.si.util.Contexto;

/**
 * @author Ivan
 *
 */
public class DemoTActividad {

    /**
     * @param args
     */
    private static void crearActividades() {
        try {
            Item item1 = new Item("1");
            Item item2 = new Item("2");
            Item item3 = new Item("3");
            Item item4 = new Item("4");
            Item item5 = new Item("5");
            Item item6 = new Item("6");
            ItemGenerator itemGen = ItemGenerator.getReference();
            Item[] listaItem1 = {item1, item2};
            Item[] listaItem2 = {item2, item4};
            Item[] listaItem3 = {item3, item5};
            Item[] listaItem4 = {item3, item4};
            Actividad actividad1 = new Actividad("A1", listaItem1, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[]{item4}, new Condition[0]);
            Actividad actividad2 = new Actividad("A2", listaItem2, new Item[0], new Item[0], new Item[0], new Item[0], new Item[]{item4}, new Item[0], new Condition[0]);
            Actividad actividad3 = new Actividad("A3", listaItem3, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Condition[0]);
            Actividad actividad4 = new Actividad("A4", listaItem4, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Condition[0]);
            // Aqui construir el arbol de actividades
            actividad1.addActividadHija(actividad2);
            actividad1.addActividadHija(actividad4);
            ActividadGenerator activGen = ActividadGenerator.getReference();
            itemGen.addItem(item1);
            itemGen.addItem(item2);
            itemGen.addItem(item3);
            itemGen.addItem(item4);
            itemGen.addItem(item5);
            itemGen.addItem(item6);
            activGen.addActividad(actividad1);
            activGen.addActividad(actividad2);
            activGen.addActividad(actividad3);
            activGen.addActividad(actividad4);
        } catch (Exception ex) {
            Logger.getLogger(DemoTActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DemoTActividad.crearActividades();
        ActividadGenerator activGen = ActividadGenerator.getReference();
        ItemGenerator itemGen = ItemGenerator.getReference();
        String[] actividades = activGen.getConjunto().keySet().toArray(new String[0]);
        String[] items = itemGen.getItems();
        ListaEstadosActividades lEstAct = new ListaEstadosActividades();
        for (int i = 0; i < actividades.length; i++) {
            lEstAct.addEstado(actividades[i], EstadoActividad.Waiting);
        }
        ListaEstadosItems lEstItems = new ListaEstadosItems();
        for (int i = 0; i < items.length; i++) {
            lEstItems.addEstado(items[i], EstadoItem.FREE);
        }
        EstadoTA estadoIni = new EstadoTA(lEstItems, lEstAct, new TreeMap<String, Set<String>>());
        List<EstadoTA> laux = new ArrayList<EstadoTA>();
        laux.add(estadoIni);
        SistemaActividades interprete = new SistemaActividades(laux);
        // preparamos las proposiciones
        // la primera es que A1 finalice
        Formula propA1 = new ProposicionActividad("A1", EstadoActividad.Finalized);
        Formula propA1ini = new Not(propA1);
        Formula propA2 = new ProposicionActividad("A2", EstadoActividad.Finalized);
        Formula propA2ini = new Not(propA2);
        Formula propA3 = new ProposicionActividad("A3", EstadoActividad.Finalized);
        Formula propA3ini = new Not(propA3);
        Formula propA4 = new ProposicionActividad("A4", EstadoActividad.Finalized);
        Formula propA4ini = new Not(propA4);
        Formula temp12 = new And(propA1ini, propA2ini);
        Formula temp3 = new And(propA3ini, temp12);
        Formula temp4 = new And(propA4ini, temp3);
        Formula fin = new And(propA2, propA3);
        //Formula formula = new EU(temp4, fin); // finalizan a la vez A2 y A3?
        Formula formula = new EU(new Proposicion() {

            @Override
            public boolean esCierta(Object s) {
                return true;
            }
        }, new Proposicion() {

            @Override
            public boolean esCierta(Object s) {
                return false;
            }
        }); // finalizan a la vez A2 y A3?
        Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(
                new Contexto() {
                }, interprete, formula);
        launcher.runCheck();
        Drawer drw = new DrawerActividad(interprete);
        launcher.launchGrafico(drw,AnimadorGrafico.BOTONERA_COMBO);
    }
}
