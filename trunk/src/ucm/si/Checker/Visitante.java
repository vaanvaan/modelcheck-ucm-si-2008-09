package ucm.si.Checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
// a�adir constructora para usar logs globales, si es necesario.
public class Visitante<S> {

    private Resultado resParcial = new Resultado(Resultado.COD_TRUE);
    private Stack<Formula> pilaFormulas = new Stack<Formula>();
    private boolean inicio = false;
    private S estado;
    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;

    public Visitante(S estado, Interprete<S> interprete) {
        super();
        this.estado = estado;
        this.interprete = interprete;
    //this.tabFormulas = new TabulacionFormulas<S>();
    }

    public Resultado getResParcial() {
        return resParcial;
    }

    public void setResParcial(Resultado resParcial) {
        this.resParcial = resParcial;
    }

    public void visita(Proposicion<S> p) {
        if (p.esCierta(estado)) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(new Arbol(estado));
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(new Arbol(estado));
        }
    }

    public void visita(Not n) {
        n.getOperando(0).accept(this);
        if (resParcial.equals(Resultado.COD_TRUE)) {
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(resParcial.getEjemplo());
        } else if (resParcial.equals(Resultado.COD_FALSE)) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(resParcial.getContraejemplo());
        } else if (resParcial.equals(Resultado.COD_MAYBEF)) {
            resParcial.setResultado(Resultado.COD_MAYBET);
        } else {
            resParcial.setResultado(Resultado.COD_MAYBEF);
        }
    }

    public void visita(Or or) {
        or.getOperando(0).accept(this);
        Resultado resIzq = new Resultado(resParcial.getResultado());
        if (resIzq.equals(Resultado.COD_TRUE)) {
            resIzq.setEjemplo(resParcial.getEjemplo());
        } else {
            resIzq.setContraejemplo(resParcial.getContraejemplo());
        }
        or.getOperando(1).accept(this);
        Resultado resDer = new Resultado(resParcial.getResultado());
        if (resDer.equals(Resultado.COD_TRUE)) {
            resDer.setEjemplo(resParcial.getEjemplo());
        } else {
            resDer.setContraejemplo(resParcial.getContraejemplo());
        }
        Resultado resAND;
        try {
            boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
            boolean part2 = Boolean.parseBoolean(resDer.getResultado());
            boolean and = part1 || part2;
            resAND = new Resultado(String.valueOf(and));
            if (!and) {
                resAND.setContraejemplo(resIzq.getContraejemplo());
            } else if (part1) {
                resAND.setEjemplo(resIzq.getEjemplo());
            } else {
                resAND.setEjemplo(resDer.getEjemplo());
            }
        } catch (Exception e) {
            if ((resIzq.getResultado().equals(Resultado.COD_TRUE)) ||
                    (resDer.getResultado().equals(Resultado.COD_TRUE))) {
                resAND = new Resultado(Resultado.COD_TRUE);
            } else {
                resAND = new Resultado(Resultado.COD_MAYBET);
            }
        }
        resParcial = resAND;
    }

    public void visita(And and) {

        // TODO Auto-generated method stub
        and.getOperando(0).accept(this);
        Resultado resIzq = new Resultado(resParcial.getResultado());
        if (resIzq.equals(Resultado.COD_TRUE)) {
            resIzq.setEjemplo(resParcial.getEjemplo());
        } else {
            resIzq.setContraejemplo(resParcial.getContraejemplo());
        }
        and.getOperando(1).accept(this);
        Resultado resDer = new Resultado(resParcial.getResultado());
        if (resDer.equals(Resultado.COD_TRUE)) {
            resDer.setEjemplo(resParcial.getEjemplo());
        } else {
            resDer.setContraejemplo(resParcial.getContraejemplo());
        }
        Resultado resAND;
        try {
            boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
            boolean part2 = Boolean.parseBoolean(resDer.getResultado());
            part1 = part1 && part2;
            resAND = new Resultado(String.valueOf(part1));
            if (part1) {
                resAND.setEjemplo(resIzq.getEjemplo());
            } else if (part2) {
                resAND.setContraejemplo(resIzq.getContraejemplo());
            } else {
                resAND.setContraejemplo(resDer.getContraejemplo());
            }
        } catch (Exception e) {
            if ((resIzq.getResultado().equals(Resultado.COD_FALSE)) ||
                    (resDer.getResultado().equals(Resultado.COD_FALSE))) {
                resAND = new Resultado(Resultado.COD_FALSE);
            } else {
                resAND = new Resultado(Resultado.COD_MAYBEF);
            }
        }
        resParcial = resAND;

    }

    public void visita(AX allnext) {
        ArrayList<S> listaEstados;
        S epadre = estado;
        listaEstados = (ArrayList<S>) interprete.transitar(estado);
        Iterator it = listaEstados.iterator();
        boolean seguir = true;
        while (it.hasNext() && seguir) {
//			Visitante vhijo = new Visitante<S>();
//			vhijo.estado= it.next();
            estado = (S) it.next();
            allnext.getOperando(0).accept(this);
            // supongo que el get formula nos devolver� la f�rmula interna
            // del all next
            if (!resParcial.equals(Resultado.COD_TRUE)) {
                seguir = false;
                //resParcial se queda con false, asi que no lo tocamos.
                Arbol<S> arbaux = new Arbol(epadre);
                arbaux.aniadirHijo(resParcial.getContraejemplo());
                resParcial.setContraejemplo(arbaux);
            }
        }
        estado = epadre;
    }

    public void visita(EX eventx) {
        List<S> listaEstados;
        S epadre = estado;
        listaEstados = interprete.transitar(estado);
        Iterator it = listaEstados.iterator();
        boolean seguir = true;
        Arbol arbaux = new Arbol(estado);
        while (it.hasNext() && seguir) {
            estado = (S) it.next();
            eventx.getOperando(0).accept(this);
            if (resParcial.equals(Resultado.COD_TRUE)) {
                //hemos encontrado uno que nos vale
                seguir = false;
                arbaux.getHijos().clear();
                arbaux.aniadirHijo(resParcial.getEjemplo());
                resParcial.setEjemplo(arbaux);
            } else {
                arbaux.aniadirHijo(resParcial.getContraejemplo());
            }
        }
        if (seguir) {
            // entonces, es que no hemos encontrado ninguno
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(arbaux);
        }
        estado = epadre;
    }

    public void visita(AU au) {
        S eanterior = estado;
        LinkedBlockingQueue<S> cola =
                new LinkedBlockingQueue<S>(interprete.transitar(estado));
        LinkedBlockingQueue<Arbol<S>> colaArbolesEj =
                new LinkedBlockingQueue<Arbol<S>>();
        LinkedBlockingQueue<Arbol<S>> colaArbolesContraEj =
                new LinkedBlockingQueue<Arbol<S>>();
        LinkedBlockingQueue<HashSet<S>> colaVisitados =
                new LinkedBlockingQueue<HashSet<S>>();
        Arbol<S> arbauxEjemplo = new Arbol(estado);
        Arbol<S> arbauxContraEjemplo = new Arbol(estado);
        for (Iterator<S> it = cola.iterator(); it.hasNext();) {
            S s = it.next();
            colaVisitados.add(new HashSet<S>());
            Arbol<S> arbej = new Arbol<S>(s);
            colaArbolesEj.add(arbej);
            arbauxEjemplo.aniadirHijo(arbej);
            Arbol<S> arbcej = new Arbol<S>(s);
            colaArbolesContraEj.add(arbcej);
            arbauxContraEjemplo.aniadirHijo(arbcej);
        }
        Arbol<S> arbauxEj = new Arbol<S>(null);
        Arbol<S> arbauxCEj = new Arbol<S>(null);
        HashSet<S> visitados = new HashSet<S>();
        boolean seguir = true;
        while (seguir && (!cola.isEmpty())) {
            estado = cola.poll();
            visitados = colaVisitados.poll();
            arbauxEj = colaArbolesEj.poll();
            arbauxCEj = colaArbolesContraEj.poll();
            au.getOperando(1).accept(this);
            if (resParcial.equals(Resultado.COD_TRUE)) {
                //arbauxEj.aniadirHijo(resParcial.getEjemplo());
            } else {
                au.getOperando(0).accept(this);
                if (resParcial.equals(Resultado.COD_TRUE)) {
                    // visitados solo debe tener estados que cumplen f0
                    visitados.add(estado);
                    // este ejemplo esta a otro nivel
                    //arbauxEj.aniadirHijo(resParcial.getEjemplo());
                    List<S> listaux = interprete.transitar(estado);
                    if (listaux.isEmpty() ||
                            this.algunEstadoComun(visitados, listaux)) {
                        // por este camino o no hay mas hijos
                        // o hay un hijo q ya hemos visitado:
                        //nunca se cumple f2 en ese camino ciclico
                        // => falso
                        seguir = false;
                    // este contraejemplo esta a otro nivel
                    //arbauxCEj.aniadirHijo(resParcial.getContraejemplo());                            
                    } else {
                        cola.addAll(listaux);
                        for (Iterator<S> it = listaux.iterator(); it.hasNext();) {
                            S s = it.next();                            
                            colaVisitados.add((HashSet<S>)visitados.clone());
                            Arbol<S> arbej = new Arbol<S>(s);
                            arbauxEj.aniadirHijo(arbej);
                            colaArbolesEj.add(arbej);
                            Arbol<S> arbcej = new Arbol<S>(s);
                            colaArbolesContraEj.add(arbcej);
                            arbauxCEj.aniadirHijo(arbcej);
                        }
                    }
                } else {
                    seguir = false;
                }
            }
        }
        if (seguir) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(arbauxEjemplo);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            Arbol<S> arb;
            ArrayList<Arbol<S>> listarbaux;
            while (arbauxCEj.getPadre() != arbauxContraEjemplo) {
                arb = arbauxCEj.getPadre();
                listarbaux = new ArrayList<Arbol<S>>();
                listarbaux.add(arbauxCEj);
                arb.getHijos().retainAll(listarbaux);
                arbauxCEj = arb;
            }
            listarbaux = new ArrayList<Arbol<S>>();
            listarbaux.add(arbauxCEj);
            arbauxContraEjemplo.getHijos().retainAll(listarbaux);
            resParcial.setContraejemplo(arbauxContraEjemplo);
        }
        estado = eanterior;
    }

    public void visita(EU eu) {
        S eanterior = estado;
        LinkedBlockingQueue<S> cola =
                new LinkedBlockingQueue<S>(interprete.transitar(estado));
        LinkedBlockingQueue<HashSet<S>> colaVisitados =
                new LinkedBlockingQueue<HashSet<S>>();
        for (Iterator<S> it = cola.iterator(); it.hasNext();) {
            S s = it.next();
            colaVisitados.add(new HashSet<S>());
        }
        HashSet<S> visitados = new HashSet<S>();
        boolean seguir = true;
        boolean encontrado = false;
        while (seguir && (!cola.isEmpty())) {            
            estado = cola.poll();
            visitados = colaVisitados.poll();
            eu.getOperando(1).accept(this);
            if (resParcial.equals(Resultado.COD_TRUE)) {
                encontrado = true;
                seguir = false;                
            } else {
                eu.getOperando(0).accept(this);
                if (resParcial.equals(Resultado.COD_TRUE)) {
                    visitados.add(estado);
                    List<S> listaux = interprete.transitar(estado);
                    // quitamos los q ya hemos visitado
                    listaux.removeAll(visitados);
                    // aniadimos los q todavia no hemos visitado
                    cola.addAll(listaux);
                    for (Iterator<S> it = listaux.iterator(); it.hasNext();) {
                        S s = it.next();
                        colaVisitados.add((HashSet<S>)visitados.clone());
                    }                    
                } else {
                    seguir = false;
                }
            }
        }
        if (encontrado) {
            resParcial.setResultado(Resultado.COD_TRUE);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
        }
        estado = eanterior;
    }

    private boolean algunEstadoComun(Set<S> visitados, List<S> lista) {
        LinkedBlockingQueue<S> l = new LinkedBlockingQueue<S>(lista);
        boolean encontrado = false;
        while (!encontrado && !l.isEmpty()) {
            S s = l.poll();
            encontrado = visitados.contains(s);
        }
        return encontrado;
    }
}
