/**
 * 
 */
package ucm.si.TeoriaActividad2.actividad;

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
	 * Estoy pensando... Asi preguntaria una acividad (lo mas facil por ahora)
	 * 
	 *  1e definimos el estado de la actividad que queremos verificar en el cheker
	 *  
	 *  		Actividad A = new Actividad(...);
	 *  Esto da bastante juego, pues podemos definir las condiciones exactas de una
	 *  actividad, es decir, sus items y su estado. (Y mas cosas si añadimos)
	 *  
	 *  Podemos hacerlo de tal forma que si por ejemplo items va a null (en nuestra pregunta)
	 *  significa que no nos importa el estado de los items, con que se cumpla el resto,
	 *  nos vale.
	 *  
	 *  2º preguntamos al estado si tiene una actividad en el estado A, si la tiene,
	 *  entonces la proposicion se cumple >_<
	 *  	  esCierta(A)= true.
	 *  
	 *  Seria como un ecuals, jejeje
	 *  
	 *  Por si no queda claro el proceso:
	 * 
	 *  1e generamos la pregunta:
	 *  	Creamos un objeto Actividad. Le damos los atributos que nos interesa chekear.
	 *  	Ejemplo de lo que seria un Not:
	 *  				Actividad activity = new Actividad(..);
	 *  				ProposicionAct propAct = new  ProposicionAct();
	 *  				propAct.setActividad(activity); 
	 *  				Formula formula = new Not(propAct);
	 *  2º Lanzamos el cheker.
	 *  
	 *  
	 */
	@Override
	public boolean esCierta(Actividad s) {
		// TODO Auto-generated method stub
		return false;
	}

}
