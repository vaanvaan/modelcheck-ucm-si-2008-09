/**
 * 
 */
package ucm.si.TeoriaActividad2.proposiciones;

import ucm.si.TeoriaActividad2.actividad.ProposicionTA;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.item.EstadoItem;
import ucm.si.TeoriaActividad2.item.ListaEstadosItems;

/**
 * @author Ivan
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
		ListaEstadosItems listaitems = estado.getItems();
		if(listaitems.containsItem(nomItem)){
			if(listaitems.getEstado(nomItem).equals(estadoItem)){
				resultado = true;
			}
		}else
			System.out.println("Atencion: Item no encontrado!");
		return resultado;
	}

}
