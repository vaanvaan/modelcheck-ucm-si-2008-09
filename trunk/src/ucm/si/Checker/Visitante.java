package ucm.si.Checker;

import ucm.si.Checker.tabulacion.TabulacionFormulas;
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
import ucm.si.util.*;
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
            GrafoCaminos gce = GrafoCaminos.CreateGrafo();
            gce.setS(estado, new ArrayList<S>());
            gce.setInicio(estado);
            resParcial.setEjemplo(gce);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            GrafoCaminos gcce = GrafoCaminos.CreateGrafo();
            gcce.setS(estado, new ArrayList<S>());
            gcce.setInicio(estado);
            resParcial.setContraejemplo(gcce);
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
        Resultado resOR;
        try {
            boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
            boolean part2 = Boolean.parseBoolean(resDer.getResultado());
            boolean or2 = part1 || part2;
            resOR = new Resultado(String.valueOf(or2));
            if (!or2) {
                resOR.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(),resDer.getContraejemplo()));
            } else if (part1&&!part2) {
                resOR.setEjemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getEjemplo(), resDer.getContraejemplo()));
            } else {
                resOR.setEjemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getEjemplo(), resDer.getEjemplo()));
            }
        } catch (Exception e) {
            if ((resIzq.getResultado().equals(Resultado.COD_TRUE)) ||
                    (resDer.getResultado().equals(Resultado.COD_TRUE))) {
                resOR = new Resultado(Resultado.COD_TRUE);
            } else {
                resOR = new Resultado(Resultado.COD_MAYBET);
            }
        }
        resParcial = resOR;
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
                resAND.setEjemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getEjemplo(), resDer.getEjemplo()));
            } else if (part2) {
                resAND.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(), resDer.getEjemplo()));
            } else {
                resAND.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(), resDer.getContraejemplo()));
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
        List<S> listaEstados;
        S epadre = estado;
        listaEstados = interprete.transitar(estado);
        Iterator<S> it = listaEstados.iterator();
        boolean seguir = true;
        while (it.hasNext() && seguir) {
            estado = it.next();
            allnext.getOperando(0).accept(this);            
            if (!resParcial.equals(Resultado.COD_TRUE)) {
                seguir = false;
                //resParcial se queda con false, asi que no lo tocamos.
                GrafoCaminos<S> gaux = new GrafoUnico<S>(epadre);
                gaux.setArista(epadre,estado);
                GrafoCaminos<S> gaux2 = GrafoCaminos.CreateGrafo(gaux, resParcial.getContraejemplo());
                gaux2.setInicio(epadre);                
                resParcial.setContraejemplo(gaux2);
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
        GrafoCaminos<S> grafoaux = new GrafoUnico<S>(estado);
        while (it.hasNext() && seguir) {
            estado = (S) it.next();
            eventx.getOperando(0).accept(this);
            if (resParcial.equals(Resultado.COD_TRUE)) {
                //hemos encontrado uno que nos vale
                seguir = false;
                grafoaux = new GrafoUnico(epadre);
                grafoaux.setArista(epadre, estado);
                resParcial.setEjemplo(GrafoCaminos.CreateGrafo(grafoaux, resParcial.getEjemplo()));
            } else {
                grafoaux = GrafoCaminos.CreateGrafo(grafoaux, resParcial.getContraejemplo());
                grafoaux.setArista(epadre, estado);
            }
        }
        if (seguir) {
            // entonces, es que no hemos encontrado ninguno
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(grafoaux);
        }
        estado = epadre;
    }

    public void visita(AU au) {
        S eanterior = estado;
        LinkedBlockingQueue<S> cola =
                new LinkedBlockingQueue<S>(interprete.transitar(estado));
        LinkedBlockingQueue<NodoGrafo<S>> colaArbolesEj =
                new LinkedBlockingQueue<NodoGrafo<S>>();
        LinkedBlockingQueue<NodoGrafo<S>> colaArbolesContraEj =
                new LinkedBlockingQueue<NodoGrafo<S>>();
        LinkedBlockingQueue<HashSet<S>> colaVisitados =
                new LinkedBlockingQueue<HashSet<S>>();
        NodoGrafo<S> arbauxEjemplo = new NodoGrafo(estado);
        NodoGrafo<S> arbauxContraEjemplo = new NodoGrafo(estado);
        for (Iterator<S> it = cola.iterator(); it.hasNext();) {
            S s = it.next();
            colaVisitados.add(new HashSet<S>());
            NodoGrafo<S> arbej = new NodoGrafo<S>(s);
            colaArbolesEj.add(arbej);
            arbauxEjemplo.aniadirAdyacente(arbej);
            NodoGrafo<S> arbcej = new NodoGrafo<S>(s);
            colaArbolesContraEj.add(arbcej);
            arbauxContraEjemplo.aniadirAdyacente(arbcej);
        }
        NodoGrafo<S> arbauxEj = new NodoGrafo<S>(null);
        NodoGrafo<S> arbauxCEj = new NodoGrafo<S>(null);
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
                            NodoGrafo<S> arbej = new NodoGrafo<S>(s);
                            arbauxEj.aniadirAdyacente(arbej);
                            colaArbolesEj.add(arbej);
                            NodoGrafo<S> arbcej = new NodoGrafo<S>(s);
                            colaArbolesContraEj.add(arbcej);
                            arbauxCEj.aniadirAdyacente(arbcej);
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
            NodoGrafo<S> arb;
            ArrayList<NodoGrafo<S>> listarbaux;
            while (arbauxCEj.getPadre() != arbauxContraEjemplo) {
                arb = arbauxCEj.getPadre();
                listarbaux = new ArrayList<NodoGrafo<S>>();
                listarbaux.add(arbauxCEj);
                arb.getHijos().retainAll(listarbaux);
                arbauxCEj = arb;
            }
            listarbaux = new ArrayList<NodoGrafo<S>>();
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
        LinkedBlockingQueue<NodoGrafo<S>> colaArbolesEj =
                new LinkedBlockingQueue<NodoGrafo<S>>();
        LinkedBlockingQueue<NodoGrafo<S>> colaArbolesContraEj =
                new LinkedBlockingQueue<NodoGrafo<S>>();
        LinkedBlockingQueue<HashSet<S>> colaVisitados =
                new LinkedBlockingQueue<HashSet<S>>();
        NodoGrafo<S> arbauxEjemplo = new NodoGrafo(estado);
        NodoGrafo<S> arbauxContraEjemplo = new NodoGrafo(estado);
        for (Iterator<S> it = cola.iterator(); it.hasNext();) {
            S s = it.next();
            colaVisitados.add(new HashSet<S>());
            NodoGrafo<S> arbej = new NodoGrafo<S>(s);
            colaArbolesEj.add(arbej);
            arbauxEjemplo.aniadirAdyacente(arbej);
            NodoGrafo<S> arbcej = new NodoGrafo<S>(s);
            colaArbolesContraEj.add(arbcej);
            arbauxContraEjemplo.aniadirAdyacente(arbcej);
        }
        NodoGrafo<S> arbauxEj = new NodoGrafo<S>(null);
        NodoGrafo<S> arbauxCEj = new NodoGrafo<S>(null);
        HashSet<S> visitados = new HashSet<S>();
        boolean seguir = true;
        boolean encontrado = false;
        while (seguir && (!cola.isEmpty())) {
            estado = cola.poll();
            visitados = colaVisitados.poll();
            arbauxEj = colaArbolesEj.poll();
            arbauxCEj = colaArbolesContraEj.poll();
            eu.getOperando(1).accept(this);
            if (resParcial.equals(Resultado.COD_TRUE)) {
                encontrado = true;
                seguir = false;
                //arbauxEj.aniadirHijo(resParcial.getEjemplo());
            } else {
                eu.getOperando(0).accept(this);
                if (resParcial.equals(Resultado.COD_TRUE)) {
                    // visitados solo debe tener estados que cumplen f0
                    visitados.add(estado);
                    // este ejemplo esta a otro nivel
                    //arbauxEj.aniadirHijo(resParcial.getEjemplo());
                    List<S> listaux = interprete.transitar(estado);
                    // quitamos los q ya hemos visitado
                    listaux.removeAll(visitados);
                    // aniadimos los q todavia no hemos visitado
                        cola.addAll(listaux);
                        for (Iterator<S> it = listaux.iterator(); it.hasNext();) {
                            S s = it.next();                            
                            colaVisitados.add((HashSet<S>)visitados.clone());
                            NodoGrafo<S> arbej = new NodoGrafo<S>(s);
                            arbauxEj.aniadirAdyacente(arbej);
                            colaArbolesEj.add(arbej);
                            NodoGrafo<S> arbcej = new NodoGrafo<S>(s);
                            colaArbolesContraEj.add(arbcej);
                            arbauxCEj.aniadirAdyacente(arbcej);
                        }
                }/* else {
                    seguir = false;
                }*/
            }
        }
        if (encontrado) {
            resParcial.setResultado(Resultado.COD_TRUE);
            NodoGrafo<S> arb;
            ArrayList<NodoGrafo<S>> listarbaux;
            while (arbauxEj.getPadre() != arbauxEjemplo) {
                arb = arbauxEj.getPadre();
                listarbaux = new ArrayList<NodoGrafo<S>>();
                listarbaux.add(arbauxEj);
                arb.getHijos().retainAll(listarbaux);
                arbauxEj = arb;
            }
            listarbaux = new ArrayList<NodoGrafo<S>>();
            listarbaux.add(arbauxEj);
            arbauxEjemplo.getHijos().retainAll(listarbaux);
            resParcial.setEjemplo(arbauxEjemplo);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);            
            resParcial.setContraejemplo(arbauxContraEjemplo);
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
