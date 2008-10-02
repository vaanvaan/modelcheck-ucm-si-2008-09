package basicTests;

import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.InterpreteWrapper;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.LaberintoPropo;
import ucm.si.Laberinto.Posicion;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Proposicion;
import junit.framework.TestCase;

public class LaberintoTests extends TestCase {
	
	public void Laberinto_COD_1_test(){
		Interprete<Posicion> lab = new Laberinto();
		ModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();
		Interprete<Posicion> wrapper = new InterpreteWrapper<Posicion>(lab);
		Posicion pos = new Posicion(1,1);
		LaberintoPropo prop = new LaberintoPropo(pos);
		prop.setLab(lab);
		Formula formula = new Not(prop);
		Resultado res = m.chequear(lab, formula,pos);
		assertFalse(res.getResultado().equals(Resultado.COD_FALSE));
	}
}
