package basicTests;

import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
import junit.framework.TestCase;
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.ModelChecker;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;
import ucm.si.adhoc.AdHoc;
import ucm.si.adhoc.Multiplo;
import ucm.si.adhoc.Primo;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;

public class TestBasicos extends TestCase {

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

    public void testNot() throws Exception {
        VisitanteConector<Posicion> visitante = new VisitanteConector<Posicion>(
                new Posicion(0, 0),
                new Laberinto(50));
        Formula ctlexp = new Not(new Not(pfalsa));
        ctlexp.accept(visitante);
        System.out.println(visitante.getResParcial().getResultado());
        assertEquals(Resultado.COD_FALSE, visitante.getResParcial().getResultado());
        assertNotNull(visitante.getResParcial().getContraejemplo());
    }

    public void testProp1() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Resultado r = m.chequear(a, conj4);
        String s = r.getResultado();
        System.out.println("El resultado de Prop1 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testProp2() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Resultado r = m.chequear(a, conj4);
        String s = r.getResultado();
        System.out.println("El resultado de Prop2 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testNot0() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Resultado r = m.chequear(a, new Not(conj4));
        String s = r.getResultado();
        System.out.println("El resultado de Not0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testNot1() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Resultado r = m.chequear(a, new Not(conj4));
        String s = r.getResultado();
        System.out.println("El resultado de Not1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testNotdoble() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Resultado r = m.chequear(a, new Not(new Not(conj4)));
        String s = r.getResultado();
        System.out.println("El resultado de Notdoble es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testOr00() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new Or(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de Or00 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testOr01() throws Exception {
        AdHoc a = new AdHoc("3", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new Or(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de Or01 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testOr10() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new Or(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de Or10 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testOr11() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };

        Resultado r = m.chequear(a, new Or(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de Or11 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testAnd00() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new And(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de And00 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testAnd01() throws Exception {
        AdHoc a = new AdHoc("3", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new And(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de And01 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());

    }

    public void testAnd10() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new And(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de And10 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());

    }

    public void testAnd11() throws Exception {
        AdHoc a = new AdHoc("4", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };

        Resultado r = m.chequear(a, new And(conj4, conj3));
        String s = r.getResultado();
        System.out.println("El resultado de And11 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());

    }

    public void testAXvacio() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de AXvacio es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());

    }

    public void testAX0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de AX0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());

    }
    public void testAX1() throws Exception {
        AdHoc a = new AdHoc("0", "0 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de AX1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());

    }
    public void testAX10() throws Exception {
        AdHoc a = new AdHoc("0", "0 3 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de AX10 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());

    }
    public void testAX01() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de AX01 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());

    }
    public void testAX11() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new AX(new Or(conj3,conj4)));
        String s = r.getResultado();
        System.out.println("El resultado de AX11 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());

    }

    public void testEXvacio() throws Exception {
        AdHoc a = new AdHoc("0", "1 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de EXvacio es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testEX0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de EX0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }
    public void testEX1() throws Exception {
        AdHoc a = new AdHoc("0", "0 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de EX1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }
    public void testEX10() throws Exception {
        AdHoc a = new AdHoc("0", "0 3 4;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de EX10 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }
    public void testEX01() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(conj3));
        String s = r.getResultado();
        System.out.println("El resultado de EX01 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }
    public void testEX11() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 3;");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 3;
            }
        };

        Resultado r = m.chequear(a, new EX(new Or(conj3,conj4)));
        String s = r.getResultado();
        System.out.println("El resultado de EX11 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }


    public void testAUvacio() throws Exception {
        AdHoc a = new AdHoc("0", "1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AUvacio es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testAUciclico() throws Exception {
        AdHoc a = new AdHoc("0", "0 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AUciclico es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }
    public void testAU1() throws Exception {
        AdHoc a = new AdHoc("4", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }
     public void testAU2() throws Exception {
        AdHoc a = new AdHoc("5", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU2 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }
    public void testAU00cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU00cortados es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testAU01cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU01cortados es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testAU10cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU10cortados es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testAU11cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU11cortados es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testAU00ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2;1 0;2 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU00ciclicos0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testAU01ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 0;4 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU01ciclicos0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testAU10ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1;4 0;1 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU10ciclicos0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testAU11ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5;4 0;5 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU11ciclicos0 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

     public void testAU00ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2;1 2;2 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU00ciclicos1 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testAU01ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 4;4 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU01ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

        public void testAU10ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1;4 1;1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU10ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

        public void testAU11ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5;4 5;5 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new AU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de AU11ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testEUvacio() throws Exception {
        AdHoc a = new AdHoc("0", "1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EUvacio es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testEUciclico() throws Exception {
        AdHoc a = new AdHoc("0", "0 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EUciclico es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }
    public void testEU1() throws Exception {
        AdHoc a = new AdHoc("4", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }
     public void testEU2() throws Exception {
        AdHoc a = new AdHoc("5", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU2 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }
    public void testEU00cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU00cortados es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testEU01cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU01cortados es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testEU10cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU10cortados es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testEU11cortados() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU11cortados es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testEU00ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2;1 0;2 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU00ciclicos0 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testEU01ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 0;4 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU01ciclicos0 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testEU10ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1;4 0;1 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU10ciclicos0 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getContraejemplo());
    }

        public void testEU11ciclicos0() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5;4 0;5 0");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU11ciclicos0 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

     public void testEU00ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 2;1 2;2 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU00ciclicos1 es: " + s);
        assertEquals(Resultado.COD_FALSE, s);
        assertNotNull(r.getContraejemplo());
    }

    public void testEU01ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 4;4 1");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU01ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

        public void testEU10ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 1;4 1;1 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() == 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU10ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

        public void testEU11ciclicos1() throws Exception {
        AdHoc a = new AdHoc("0", "0 4 5;4 5;5 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Proposicion<Integer> conj4 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() >= 4;
            }
        };
        Proposicion<Integer> conj3 = new Proposicion<Integer>() {

            @Override
            public boolean esCierta(Integer s) {
                return s.intValue() < 4;
            }
        };

        Resultado r = m.chequear(a, new EU(conj3,conj4));
        String s = r.getResultado();
        System.out.println("El resultado de EU11ciclicos1 es: " + s);
        assertEquals(Resultado.COD_TRUE, s);
        assertNotNull(r.getEjemplo());
    }

    public void testAUotro0() throws Exception {
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

    public void testAUotro1() throws Exception {
        AdHoc a = new AdHoc("0 1", "0 1 4;1 3 4;4 5;3 4");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        String r = m.chequear(a, new AU(new Not(p), p)).getResultado();
        System.out.println("El resultado de AU1 es: " + r);
        assertEquals(Resultado.COD_TRUE, r);
    }

    public void testEUotro0() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 1 4 6;4 1 4;6 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        String r = m.chequear(a, new EU(new Not(p), p)).getResultado();
        System.out.println("El resultado de EU0 es: " + r);
        assertEquals(Resultado.COD_TRUE, r);
    }

    public void testEUotro1() throws Exception {
        AdHoc a = new AdHoc("0", "0 1 4;1 1 4;4 1 4 8;6 4 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>();
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        Resultado res = m.chequear(a, new EU(new Not(p), p));
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
        Formula f = new EU(new AU(new And(p, q), r), new AX(new Or(w, s)));
        //InterpreteWrapper<Integer> wr = new InterpreteWrapper<Integer>(a);
        String res = m.chequear(a, f).getResultado();
        System.out.println("El resultado de f es: " + res);
        assertEquals(Resultado.COD_TRUE, res);
    }
}
