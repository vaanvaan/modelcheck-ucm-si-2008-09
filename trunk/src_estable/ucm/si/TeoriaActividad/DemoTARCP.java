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
import ucm.si.TeoriaActividad.actividad.Conditions;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;
import ucm.si.TeoriaActividad.proposiciones.ProposicionActividad;
import ucm.si.TeoriaActividad.proposiciones.ProposicionItem;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.AU;
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
public class DemoTARCP {

    /**
     * @param args
     */
    private static void crearActividades() {
        try {
            Item reanimador = new Item("Reanimador");
            Item victima = new Item("Victima");
            Item seguridad = new Item("Seguridad");
            Actividad garantizarSeguridad = new Actividad(
                    "Garantizar Seguridad", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{seguridad}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{seguridad}, //itemsTodispose, itemsToGenerate
                    new Conditions[0]); // condiciones
            Item salvarVictima = new Item("Salvar Victima");
            Conditions haySeguridad = new Conditions() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Seguridad").equals(EstadoItem.FREE);
                }
            };
            Actividad RCP = new Actividad(
                    "RCP", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[]{salvarVictima}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{salvarVictima}, //itemsTodispose, itemsToGenerate
                    new Conditions[]{haySeguridad}); // condiciones
            Item analisisConciencia = new Item("Analisis Conciencia");
            Actividad comprobarConciencia = new Actividad(
                    "Comprobar Conciencia", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{analisisConciencia}, // herramientas, productos
                    new Item[0], new Item[]{analisisConciencia}, //itemsTodispose, itemsToGenerate
                    new Conditions[]{haySeguridad}); // condiciones
            Item victimaRespondeI = new Item("Victima Responde");
            Item victimaNoRespondeI = new Item("Victima No Responde");
            Actividad victimaResponde = new Actividad(
                    "Victima Responde", new Item[]{victima}, //nombre, sujetos
                    new Item[]{analisisConciencia}, new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{victimaRespondeI}, // herramientas, productos
                    new Item[]{analisisConciencia}, new Item[]{victimaRespondeI,salvarVictima}, //itemsTodispose, itemsToGenerate
                    new Conditions[0]); // condiciones
            Actividad victimaNoResponde = new Actividad(
                    "Victima No Responde", new Item[]{victima}, //nombre, sujetos
                    new Item[]{analisisConciencia}, new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{victimaNoRespondeI}, // herramientas, productos
                    new Item[]{analisisConciencia}, new Item[]{victimaNoRespondeI}, //itemsTodispose, itemsToGenerate
                    new Conditions[0]); // condiciones
            Conditions noResponde = new Conditions() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima No Responde").equals(EstadoItem.FREE);
                }
            };
            Item analisisRespiracion = new Item("Analisis Respiracion");
            Actividad comprobarRespiracion = new Actividad(
                    "Comprobar Respiracion", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{analisisRespiracion}, // herramientas, productos
                    new Item[0], new Item[]{analisisRespiracion}, //itemsTodispose, itemsToGenerate
                    new Conditions[]{haySeguridad,noResponde}); // condiciones
            Item victimaRespira = new Item("Victima Respira");
            Item victimaNoRespira = new Item("Victima No Respira");
            Actividad respira =  new Actividad(
                    "Victima Respira", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{analisisRespiracion}, new Item[]{victimaRespira}, //itemsTodispose, itemsToGenerate
                    new Conditions[0]); // condiciones
             Actividad noRespira =  new Actividad(
                    "Victima No Respira", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{analisisRespiracion}, new Item[]{victimaNoRespira}, //itemsTodispose, itemsToGenerate
                    new Conditions[0]); // condiciones
             Conditions respiraC = new Conditions() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima Respira").equals(EstadoItem.FREE);
                }
             };
             Actividad colocarPosicionSeguridad  =  new Actividad(
                    "Colocar en Posicion de Seguridad", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{salvarVictima}, // objetos, objetivos
                    new Item[0], new Item[]{salvarVictima}, // herramientas, productos
                    new Item[0], new Item[]{salvarVictima}, //itemsTodispose, itemsToGenerate
                    new Conditions[]{haySeguridad,respiraC}); // condiciones
            // Aqui construir el arbol de actividades
            RCP.addActividadHija(comprobarConciencia);
            RCP.addActividadHija(comprobarRespiracion);
            RCP.addActividadHija(colocarPosicionSeguridad);
            ActividadGenerator activGen = ActividadGenerator.getReference();
            ItemGenerator itemGen = ItemGenerator.getReference();
            itemGen.addItem(reanimador);
            itemGen.addItem(victima);
            itemGen.addItem(seguridad);
            itemGen.addItem(salvarVictima);
            itemGen.addItem(analisisConciencia);
            itemGen.addItem(victimaRespondeI);
            itemGen.addItem(victimaNoRespondeI);
            itemGen.addItem(analisisRespiracion);
            itemGen.addItem(victimaRespira);
            itemGen.addItem(victimaNoRespira);
            activGen.addActividad(garantizarSeguridad);
            activGen.addActividad(RCP);
            activGen.addActividad(comprobarConciencia);
            activGen.addActividad(victimaResponde);
            activGen.addActividad(victimaNoResponde);
            activGen.addActividad(comprobarRespiracion);
            activGen.addActividad(respira);
            activGen.addActividad(noRespira);
            activGen.addActividad(colocarPosicionSeguridad);
        } catch (Exception ex) {
            Logger.getLogger(DemoTARCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DemoTARCP.crearActividades();
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
            if (items[i].equalsIgnoreCase("reanimador") ||
                    items[i].equalsIgnoreCase("victima")) {
                lEstItems.addEstado(items[i], EstadoItem.FREE);
            } else {
                lEstItems.addEstado(items[i], EstadoItem.DISPOSED);
            }
        }
        EstadoTA estadoIni = new EstadoTA(lEstItems, lEstAct, new TreeMap<String, Set<String>>());
        List<EstadoTA> laux = new ArrayList<EstadoTA>();
        laux.add(estadoIni);
        SistemaActividades interprete = new SistemaActividades(laux);
        // preparamos las proposiciones
        Formula propA1 = new ProposicionItem("Salvar Victima", EstadoItem.FREE);
        Formula propA1ini = new Not(propA1);
        Formula formula = new AU(propA1ini,propA1); // siempre se salva la victima?
        Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(
                new Contexto() {
                }, interprete, formula);
        launcher.runCheck();
        Drawer drw = new DrawerActividad(interprete);
        launcher.launchGrafico(drw);
    }
}
