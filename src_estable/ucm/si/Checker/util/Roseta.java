/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.util;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * 
 * @author Niko, Jose Antonio, Ivan
 */
public class Roseta<S> extends TreeMap<S, List<StateAndLabel<S>>> {
	public Roseta() {
		super();
	}


	public Roseta(int cap) {
		//super(cap);
	}

	public Roseta(int cap, float coef) {
		//super(cap, coef);
	}

	/**
	 * Devuelve la lista de StateAndLabel (estados y las etiquetas de sus
	 * transiciones) de los estados hijos al padre
	 * 
	 * @param s
	 *            Estado del cual queremos saber sus posibles hijos y sus
	 *            respectivas etiquetas
	 * @return
	 */
	public List<StateAndLabel<S>> getNextSAL(S s) {
		return super.get(s);
	}

	/**
	 * Para una arista compuesta por los estado padre e hijo (primer y ultimo
	 * estado de la arista) se devuelve el nombre de esa transicion.
	 * 
	 * @param padre
	 *            Estado primero de la arista
	 * @param hijo
	 *            Segundo y ultimo estado de la arista dirigida.
	 * @return String (etiqueta de la arsita o transicion)
	 * @throws java.lang.Exception
	 *             ("Arista no encontrada")
	 */
	public String getLabel(S padre, S hijo) throws Exception {
		List<StateAndLabel<S>> l = super.get(hijo);
		if (l.contains(l)) {
			int i = l.indexOf(l);
			StateAndLabel<S> sal = l.get(i);
			return sal.getLabel();

		} else
			throw (new Exception("Arista no encontrada"));
	}

	public StateAndLabel<S> getSAL(S padre, S hijo) throws Exception {
		List<StateAndLabel<S>> l = super.get(padre);//esto devuelve los movimientos del padre
//		if (l.contains(hijo)) {//comprobamos si hay transici�n al hijo
//			int i = l.indexOf(hijo);// en caso que la haya
//			StateAndLabel<S> sal = l.get(i);
//			return sal;
//		} else
//			throw (new Exception("Arista no encontrada"));
		StateAndLabel<S> sal = null;
		Iterator<StateAndLabel<S>> it = l.iterator();
		boolean encontrado = false;
		while(it.hasNext()&& !encontrado){
			sal = it.next();
			if (sal.getState().equals(hijo)){
				encontrado = true;
			}
		}
		if(!encontrado) throw (new Exception("Arista no encontrada" + padre.toString() + hijo.toString()));
		return sal;
	}


}
