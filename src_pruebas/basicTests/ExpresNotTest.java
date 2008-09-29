package basicTests;

import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
import junit.framework.TestCase;


public class ExpresNotTest extends TestCase{

	public void test2Not() throws Exception {
	    Visitante visitante = new Visitante();
	    Formula ctlexp = new Not(new Not(new Proposicion(Resultado.COD_FALSE)));
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_FALSE, visitante.getResParcial().getResultado());
	}
	
	public void testAnd()throws Exception{
		Visitante visitante = new Visitante();
		Formula ctlexp = new And(new Not(new Proposicion(Resultado.COD_FALSE)),new Proposicion(Resultado.COD_TRUE));
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
	
	public void testOr() throws Exception{
		Visitante visitante = new Visitante();
		Formula ctlexp = new Or(new Proposicion(Resultado.COD_MAYBEF),new Not(new Proposicion(Resultado.COD_FALSE)));
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
		assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
}
