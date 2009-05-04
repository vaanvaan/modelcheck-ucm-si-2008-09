/**
 * 
 */
package ucm.si.TeoriaActividad;

import java.util.Iterator;
import java.util.List;

import ucm.si.TeoriaActividad.GUI.DrawerActividad;
import ucm.si.TeoriaActividad.Interprete.SistemaActividades;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.proposiciones.ProposicionActividad;
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
	public static void main(String[] args) {
		SistemaActividades interprete = new SistemaActividades();
		
		// preparamos las proposiciones
		// la primera es que A1 finalice
		Formula propA1= new ProposicionActividad("A1",EstadoActividad.Finalized);
		Formula propA1ini= new Not(propA1);
		Formula propA2= new ProposicionActividad("A2",EstadoActividad.Finalized);
        Formula propA2ini= new Not(propA2);
		Formula propA3= new ProposicionActividad("A3",EstadoActividad.Finalized);
        Formula propA3ini= new Not(propA3);
        Formula propA4= new ProposicionActividad("A4",EstadoActividad.Finalized);
        Formula propA4ini= new Not(propA4);

        Formula temp12 = new And(propA1ini,propA2ini);
        Formula temp3 = new And(propA3ini, temp12);
        Formula temp4 = new And(propA4ini, temp3);
        Formula fin = new And (propA2, propA3);
		//Formula formula = new EU(temp4, fin); // finalizan a la vez A2 y A3?
        Formula formula = new EU(new Proposicion(){
            @Override
            public boolean esCierta(Object s) {
                return true;
            }
        }, new Proposicion(){
            @Override
            public boolean esCierta(Object s) {
                return false;
            }}); // finalizan a la vez A2 y A3?
		Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(new Contexto() {}, interprete, formula);
		launcher.runCheck();
		Drawer drw = new DrawerActividad(interprete);
		launcher.launchGrafico(drw);
		System.out.println("fin");
	}

}
