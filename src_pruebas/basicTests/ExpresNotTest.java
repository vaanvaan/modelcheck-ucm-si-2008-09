package basicTests;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
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
import ucm.si.adhoc.Multiplo;
import ucm.si.adhoc.Primo;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.EU;

public class ExpresNotTest extends TestCase {

    private final static Proposicion pfalsa = new Proposicion<Posicion>() {

        @Override
        public boolean esCierta(Posicion s) {
            return false;
        }
    };
    private final static Proposicion pcierta = new Proposicion<Posicion>() {

        @Override
        public boolean esCierta(Posicion s) {
            return true;
        }
    };
    private final static Proposicion pmaybefalsa = new Proposicion<Posicion>() {

        @Override
        public boolean esCierta(Posicion s) {
            return false;
        }

        //@Override
        public String getValor() {
            return Resultado.COD_MAYBEF;
        }    
    };

    public void test2Not() throws Exception {
        VisitanteConector<Posicion> visitante = new VisitanteConector<Posicion>(
                new Posicion(0, 0),
                new Laberinto(50));
        Formula ctlexp = new Not(new Not(pfalsa));
        ctlexp.accept(visitante);
        System.out.println(visitante.getResParcial().getResultado());
        assertEquals(Resultado.COD_FALSE, visitante.getResParcial().getResultado());
    }

    public void testAnd() throws Exception {
        VisitanteConector<Posicion> visitante = new VisitanteConector<Posicion>(
                new Posicion(0, 0),
                new Laberinto(50));
        Formula ctlexp = new And(new Not(pfalsa), pcierta);
        ctlexp.accept(visitante);
        System.out.println(visitante.getResParcial().getResultado());
        assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
    }

    public void testOr() throws Exception {
        VisitanteConector<Posicion> visitante = new VisitanteConector<Posicion>(
                new Posicion(0, 0),
                new Laberinto(50));
        Formula ctlexp = new Or(pmaybefalsa, new Not(pfalsa));
        ctlexp.accept(visitante);
        System.out.println(visitante.getResParcial().getResultado());
        assertEquals(Resultado.COD_TRUE, visitante.getResParcial().getResultado());
    }

    public void testAU0() throws Exception {
        AdHoc a = new AdHoc("0 1", "0 1 4;1 1 4 6;4 4;6 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        /* estados iniciales: el 0 y el 1
         * transiciones: 0 1 4 significa que del estado 0 transita al
         *                                              1 y al 4                 
         *                1 1 4 6 significa que el 1 transita al
         *                                         1 al 4 y al 6
         * */

        Primo p = new Primo();
        String r = m.chequear(a, new AU(new Not(p), p)).getResultado();
        System.out.println("El resultado de AU0 es: " + r);
        assertEquals(Resultado.COD_FALSE, r);
    }

    public void testAU1() throws Exception {
        AdHoc a = new AdHoc("0 1", "0 1 4;1 3 4;4 5;3 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        String r = m.chequear(a,new AU(new Not(p), p)).getResultado();
        System.out.println("El resultado de AU1 es: " + r);
        assertEquals(Resultado.COD_TRUE, r);
    }

    public void testEU0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 1 4 6;4 1 4;6 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        String r = m.chequear(a,new EU(new Not(p), p)).getResultado();
        System.out.println("El resultado de EU0 es: " + r);
        assertEquals(Resultado.COD_TRUE, r);
    }

    public void testEU1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 1 4;4 1 4 8;6 4 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        Resultado res = m.chequear(a,new EU(new Not(p), p));
        String r = res.getResultado();
        System.out.println("El resultado de EU1 es: " + r);        
        assertEquals(Resultado.COD_FALSE, r);
    }
    
    public void testComplicada1() throws Exception {
        AdHoc a = new AdHoc("100", "100 10 5;5 10 6;10 10;6 35 12;12 5;35 105;105 35 11");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Multiplo p = new Multiplo(2);
        Multiplo q = new Multiplo(3);
        Multiplo r = new Multiplo(5);
        Multiplo w = new Multiplo(7);
        Multiplo s = new Multiplo(11);
        Formula f = new EU(new AU(new And(p,q),r), new AX(new Or(w,s)));
        //InterpreteWrapper<Integer> wr = new InterpreteWrapper<Integer>(a);
        String res = m.chequear(a,f).getResultado();
        System.out.println("El resultado de f es: " + res);
        assertEquals(Resultado.COD_TRUE, res);
    }
    
}
