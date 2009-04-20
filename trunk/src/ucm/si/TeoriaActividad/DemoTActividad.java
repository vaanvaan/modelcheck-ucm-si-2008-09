/**
 * 
 */
package ucm.si.TeoriaActividad;

import java.util.Iterator;
import java.util.List;

import ucm.si.TeoriaActividad2.GUI.DrawerActividad;
import ucm.si.TeoriaActividad2.Interprete.InterpreteTA;
import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.proposiciones.ProposicionActividad;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
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
		InterpreteTA interprete = new InterpreteTA();
		List<EstadoTA> listaIniciales=interprete.iniciales();
		Iterator it = listaIniciales.iterator();
		// mostrar por consola las actividades.
		while (it.hasNext()){
			System.out.println("Conj de actividades");
			EstadoTA stat = (EstadoTA) it.next();
			ListaEstadosActividades listaActiv = stat.getActividades();
			System.out.println(listaActiv.keySet());
		}
		// preparamos las proposiciones
		// la primera es que A1 finalice
		ProposicionActividad propA1= new ProposicionActividad("A1",EstadoActividad.Finalized);
		ProposicionActividad propA1ini= new ProposicionActividad("A1",EstadoActividad.Idle);
		ProposicionActividad propA2= new ProposicionActividad("A2",EstadoActividad.Finalized);
//		Formula formula = new And (propA2, new EU(propA1ini, propA1));
		Formula formula = new EU(propA1ini, propA1);
		Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(new Contexto() {}, interprete, formula);
		launcher.runCheck();
		Drawer drw = new DrawerActividad(interprete);
		launcher.launchGrafico(drw);
		System.out.println("fin");
	}

}
