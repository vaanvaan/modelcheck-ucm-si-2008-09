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
        
}
