package basicTests;

import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
import junit.framework.TestCase;
import ucm.si.Checker.Interprete;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;



public class ExpresNotTest extends TestCase{

       private final static Proposicion pfalsa = new Proposicion<Posicion>(){
            @Override
            public boolean esCierta(Posicion s) {
                return false;
            }
            };
       
       private final static Proposicion pcierta = new Proposicion<Posicion>(){
            @Override
            public boolean esCierta(Posicion s) {
                return true;
            }
            };
       private final static Proposicion pmaybefalsa = new Proposicion<Posicion>(){
            @Override
            public boolean esCierta(Posicion s) {
                return false;
            }
            @Override
            public String getValor(){
                return Resultado.COD_MAYBEF;
            }
            };
       
            
	public void test2Not() throws Exception {
	    Visitante<Posicion> visitante = new Visitante<Posicion>(
                    new Posicion(0,0),
                    new Laberinto());            
	    Formula ctlexp = new Not(new Not(pfalsa));
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_FALSE, visitante.getResParcial().getResultado());
	}
	
	public void testAnd()throws Exception{
		Visitante<Posicion> visitante = new Visitante<Posicion>(
                    new Posicion(0,0),
                    new Laberinto());
		Formula ctlexp = new And(new Not(pfalsa),pcierta);
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
	
	public void testOr() throws Exception{
		Visitante<Posicion> visitante = new Visitante<Posicion>(
                    new Posicion(0,0),
                    new Laberinto());
		Formula ctlexp = new Or(pmaybefalsa,new Not(pfalsa));
		visitante.visita(ctlexp);
		System.out.println(visitante.getResParcial().getResultado());
		assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
}
