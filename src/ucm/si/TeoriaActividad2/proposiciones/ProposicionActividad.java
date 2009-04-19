/**
 * 
 */
package ucm.si.TeoriaActividad2.proposiciones;

import java.util.ArrayList;

import ucm.si.TeoriaActividad2.actividad.EstadoActividad;
import ucm.si.TeoriaActividad2.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad2.actividad.ProposicionTA;
import ucm.si.TeoriaActividad2.estado.EstadoTA;

/**
 * @author Ivan
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
		ListaEstadosActividades lista = estado.getActividades();
		EstadoActividad actState = null;
		 if (lista.isActivity(claveActiv)){
			 actState = lista.getEstado(claveActiv);
			 if(actState.equals(estado)){
				 resultado=true;
			 }
		 }else{
			 System.out.println("Actividad no encontrada.");
			 resultado= false;
		 }
		
		return resultado;
	}



}
