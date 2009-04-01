/**
 * 
 */
package ucm.si.TeoriaActividad.actividad;

import ucm.si.basico.ecuaciones.Proposicion;

/**
 * @author Ivan
 *
 */
public class ProposicionAct extends Proposicion<Actividad>{
	
	/*
	 * (non-Javadoc)
	 * @see ucm.si.basico.ecuaciones.Proposicion#esCierta(java.lang.Object)
	 * 
	 * Estoy pensando... As� preguntar�a una acividad (lo m�s f�cil por ahora)
	 * 
	 *  1� definimos el estado de la actividad que queremos verificar en el cheker
	 *  
	 *  		Actividad A = new Actividad(...);
	 *  Esto da bastante juego, pues podemos definir las condiciones exactas de una
	 *  actividad, es decir, sus items y su estado. (Y m�s cosas si a�adimos)
	 *  
	 *  Podemos hacerlo de tal forma que si por ejemplo items va a null (en nuestra pregunta)
	 *  significa que no nos importa el estado de los items, con que se cumpla el resto,
	 *  nos vale.
	 *  
	 *  2� preguntamos al estado si tiene una actividad en el estado A, si la tiene,
	 *  entonces la proposici�n se cumple >_<
	 *  	  esCierta(A)= true.
	 *  
	 *  Ser�a como un ecuals, jejeje
	 *  
	 *  Por si no queda claro el proceso:
	 * 
	 *  1� generamos la pregunta:
	 *  	Creamos un objeto Actividad. Le damos los atributos que nos interesa chekear.
	 *  	Ejemplo de lo que ser�a un Not:
	 *  				Actividad activity = new Actividad(..);
	 *  				ProposicionAct propAct = new  ProposicionAct();
	 *  				propAct.setActividad(activity); 
	 *  				Formula formula = new Not(propAct);
	 *  2� Lanzamos el cheker.
	 *  
	 *  
	 */
	@Override
	public boolean esCierta(Actividad s) {
		// TODO Auto-generated method stub
		return false;
	}

}
