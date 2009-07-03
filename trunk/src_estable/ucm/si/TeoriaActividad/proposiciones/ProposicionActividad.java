/**
 * 
 */
package ucm.si.TeoriaActividad.proposiciones;

import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.actividad.ProposicionTA;
import ucm.si.TeoriaActividad.estado.EstadoTA;

/**
 * @author Niko, Jose Antonio, Ivan
 *
 *OJO: Aqui hago si la clave por la que se pregunta no esta en la lista de actividades?
 * de momento es un sysout y return false.
 */
public class ProposicionActividad extends ProposicionTA{
	private String claveActiv;
	private EstadoActividad estado;
		
	public ProposicionActividad(String clave,EstadoActividad stat){
		claveActiv=clave;
		estado=stat;
	}
	public String getClaveActiv() {
		return claveActiv;
	}
	public void setClaveActiv(String claveActiv) {
		this.claveActiv = claveActiv;
	}
	public EstadoActividad getEstado() {
		return estado;
	}
	public void setEstado(EstadoActividad estado) {
		this.estado = estado;
	}
	@Override
	public boolean comparaActividad(EstadoTA estado) {
		boolean resultado = false;
		ListaEstadosActividades lista = estado.actividades;
		EstadoActividad actState = null;
			 actState = lista.getEstado(claveActiv);
			 if(actState.equals(this.estado)){
				 resultado=true;
			 }
		 
		
		return resultado;
	}



}
