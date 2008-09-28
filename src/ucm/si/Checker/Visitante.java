package ucm.si.Checker;

import ucm.si.basico.ecuaciones.Formula;


// añadir constructora para usar logs globales, si es necesario.
public  class Visitante {
	
	private Resultado resParcial = null;
	
	public Resultado visita(Formula expresion){
		Formula exp = expresion;
		exp.accept(this);
		//actualizo el resultado parcial
		return resParcial;
	}

	public Resultado getResParcial() {
		return resParcial;
	}

	public void setResParcial(Resultado resParcial) {
		this.resParcial = resParcial;
	}
}
