package ucm.si.Checker;

import java.util.Stack;

import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;


// a�adir constructora para usar logs globales, si es necesario.
public  class Visitante<S> {
	
	private Resultado resParcial = null;
	private Stack<Formula> pilaFormulas = new Stack<Formula>();
	private boolean inicio = false;
	
	public Resultado visita(Formula expresion){
		expresion.accept(this);
		/*while (!pilaFormulas.isEmpty())
		{ 
			Formula e = pilaFormulas.pop();
			e.accept(this);
		}
		*/
		//actualizo el resultado parcial
		return resParcial;
	}

	public Resultado getResParcial() {
		return resParcial;
	}

	public void setResParcial(Resultado resParcial) {
		this.resParcial = resParcial;
	}
	public void visita(Not n){
		n.getFormula().accept(this);
		if (resParcial.equals(Resultado.COD_TRUE)){
			resParcial.setResultado(Resultado.COD_FALSE);
		} else if (resParcial.equals(Resultado.COD_FALSE)){
			resParcial.setResultado(Resultado.COD_TRUE);
		}else if (resParcial.equals(Resultado.COD_MAYBEF))
			resParcial.setResultado(Resultado.COD_MAYBET);
		else resParcial.setResultado(Resultado.COD_MAYBEF);		
	}
	public void visita(Or or){
		or.getExpIzq().accept(this);
		Resultado resIzq = resParcial;
		or.getExpDer().accept(this);
		Resultado resDer = resParcial;
		Resultado resAND;
		try{
			boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
			boolean part2 = Boolean.parseBoolean(resDer.getResultado());
			part1 = part1 || part2;
			resAND = new Resultado(String.valueOf(part1));
		}catch(Exception e){
			if((resIzq.getResultado().equals(Resultado.COD_TRUE)) ||
					(resDer.getResultado().equals(Resultado.COD_TRUE)) ){
				resAND = new Resultado(Resultado.COD_TRUE);
			}else{
				resAND = new Resultado(Resultado.COD_MAYBET);
			}
		}
		resParcial=resAND;
	}
	public void visita(And and){

		// TODO Auto-generated method stub
		and.getExpIzq().accept(this);
		Resultado resIzq = resParcial;
		and.getExpDer().accept(this);
		Resultado resDer = resParcial;
		Resultado resAND;
		try{
			boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
			boolean part2 = Boolean.parseBoolean(resDer.getResultado());
			part1 = part1 && part2;
			resAND = new Resultado(String.valueOf(part1));
		}catch(Exception e){
			if((resIzq.getResultado().equals(Resultado.COD_FALSE)) ||
					(resDer.getResultado().equals(Resultado.COD_FALSE)) ){
				resAND = new Resultado(Resultado.COD_FALSE);
			}else{
				resAND = new Resultado(Resultado.COD_MAYBEF);
			}
		}
		resParcial=resAND;
	
	}
}
