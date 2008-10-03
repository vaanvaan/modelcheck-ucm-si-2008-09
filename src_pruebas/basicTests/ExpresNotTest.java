package basicTests;

import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
import junit.framework.TestCase;
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.InterpreteWrapper;
import ucm.si.Checker.ModelChecker;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;
import ucm.si.adhoc.AdHoc;
import ucm.si.adhoc.Primo;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.EU;



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
		ctlexp.accept(visitante);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_FALSE, visitante.getResParcial().getResultado());
	}
	
	public void testAnd()throws Exception{
		Visitante<Posicion> visitante = new Visitante<Posicion>(
                    new Posicion(0,0),
                    new Laberinto());
		Formula ctlexp = new And(new Not(pfalsa),pcierta);
		ctlexp.accept(visitante);
		System.out.println(visitante.getResParcial().getResultado());
	    assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
	
	public void testOr() throws Exception{
		Visitante<Posicion> visitante = new Visitante<Posicion>(
                    new Posicion(0,0),
                    new Laberinto());
		Formula ctlexp = new Or(pmaybefalsa,new Not(pfalsa));
		ctlexp.accept(visitante);
		System.out.println(visitante.getResParcial().getResultado());
		assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
	}
        
        public void testAU0() throws Exception{
                ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
                /* estados iniciales: el 0 y el 1
                 * transiciones: 0 1 4 significa que del estado 0 transita al
                 *                                              1 y al 4                 
                 *                1 1 4 6 significa que el 1 transita al
                 *                                         1 al 4 y al 6
                 * */
                AdHoc a = new AdHoc("0 1", "0 1 4;1 1 4 6;4 4;6 2");                
                Primo p = new Primo();
                InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
                String r = m.chequear(w, new AU(new Not(p),p)).getResultado();       
                System.out.println("El resultado de AU0 es: "+r);
                assertEquals(Resultado.COD_FALSE, r);
        }
        
        public void testAU1() throws Exception{
                ModelChecker<Integer> m = new DefaultModelChecker<Integer>();                
                AdHoc a = new AdHoc("0 1", "0 1 4;1 3 4;4 5;3 4");                
                Primo p = new Primo();
                InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
                String r = m.chequear(w, new AU(new Not(p),p)).getResultado();       
                System.out.println("El resultado de AU1 es: "+r);
                assertEquals(Resultado.COD_TRUE, r);
        }
        
        public void testEU0() throws Exception{
                ModelChecker<Integer> m = new DefaultModelChecker<Integer>();        
                AdHoc a = new AdHoc("0", "0 1 4;1 1 4 6;4 1 4;6 2");
                Primo p = new Primo();
                InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
                String r = m.chequear(w, new EU(new Not(p),p)).getResultado();       
                System.out.println("El resultado de EU0 es: "+r);
                assertEquals(Resultado.COD_TRUE, r);
        }
        
        public void testEU1() throws Exception{
                ModelChecker<Integer> m = new DefaultModelChecker<Integer>();        
                AdHoc a = new AdHoc("0", "0 1 4;1 1 4;4 1 4 8;6 4 2");
                Primo p = new Primo();
                InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
                String r = m.chequear(w, new EU(new Not(p),p)).getResultado();       
                System.out.println("El resultado de EU1 es: "+r);
                assertEquals(Resultado.COD_FALSE, r);
        }
}
