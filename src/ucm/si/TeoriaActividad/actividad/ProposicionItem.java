/**
 * 
 */
package ucm.si.TeoriaActividad.actividad;

import ucm.si.TeoriaActividad.item.Item;
import ucm.si.basico.ecuaciones.Proposicion;

/**
 * @author Ivan
 *
 */
public class ProposicionItem extends Proposicion<Item>{
	
	/*
	 * (non-Javadoc)
	 * @see ucm.si.basico.ecuaciones.Proposicion#esCierta(java.lang.Object)
	 * 
	 * Simplemente ser�a comprobar el estado del item en el estado que est� desarrollando
	 * el cheker. Me imagino, que podemos hacerlo como lo de la Actividad.
	 * Pues si metemos roles, si rol = null, no nos importa el rol que haya tenido
	 * pero puede que necesitemos algo como, item = destroyed, owner = ActividadPepe,
	 * rol= tool;
	 * 
	 * XD espero que se entienda
	 * 
	 */
	@Override
	public boolean esCierta(Item s) {
		// TODO Auto-generated method stub
		return false;
	}

}
