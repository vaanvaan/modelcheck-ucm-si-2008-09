/**
 * 
 */
package ucm.si.TeoriaActividad.proposiciones;

import ucm.si.TeoriaActividad.actividad.ProposicionTA;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;

/**
 * @author Niko, Jose Antonio, Ivan
 *
 */
public class ProposicionItem extends ProposicionTA{
	private String nomItem=null;
	private EstadoItem estadoItem;
	
	public ProposicionItem(String clave, EstadoItem stat) {
		nomItem=clave;
		estadoItem=stat;
	}
	
	@Override
	public boolean comparaActividad(EstadoTA estado) {
		boolean resultado = false;
		ListaEstadosItems listaitems = estado.items;
			if(listaitems.getEstado(nomItem).equals(estadoItem)){
				resultado = true;
			}
		return resultado;
	}

}
