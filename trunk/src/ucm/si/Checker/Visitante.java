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
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import ucm.si.Checker.tabulacion.TabulacionMemSistema;
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
    private S estado;
    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;

    public Visitante(S estado, Interprete<S> interprete) {
        super();
        this.estado = estado;
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
    //this.tabFormulas = new TabulacionFormulas<S>();
    }

    public Resultado getResParcial() {
        return resParcial;
    }

    public void setResParcial(Resultado resParcial) {
        this.resParcial = resParcial;
    }

    public void visita(Proposicion<S> p) {
        if (tabFormulas.tieneEtiqueta(estado, p)) {
            Resultado r = tabFormulas.getResultado(estado, p);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            if (p.esCierta(estado)) {
                resParcial.setResultado(Resultado.COD_TRUE);
                GrafoCaminos<S> gce = new GrafoUnico<S>(estado);
                resParcial.setEjemplo(gce);
            } else {
                resParcial.setResultado(Resultado.COD_FALSE);
                GrafoCaminos<S> gcce = new GrafoUnico<S>(estado);
                resParcial.setContraejemplo(gcce);
            }
            this.tabFormulas.aniadirEtiqueta(estado, p, resParcial);
        }

    }

    public void visita(Not n) {
        if (tabFormulas.tieneEtiqueta(estado, n)) {
            Resultado r = tabFormulas.getResultado(estado, n);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
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
            this.tabFormulas.aniadirEtiqueta(estado, n, resParcial);
        }
    }

    public void visita(Or or) {
        if (tabFormulas.tieneEtiqueta(estado, or)) {
            Resultado r = tabFormulas.getResultado(estado, or);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
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
                            resIzq.getContraejemplo(), resDer.getContraejemplo()));
                } else if (part1 && !part2) {
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
            tabFormulas.aniadirEtiqueta(estado, or, resParcial);
        }
    }

    public void visita(And and) {
        if (tabFormulas.tieneEtiqueta(estado, and)) {
            Resultado r = tabFormulas.getResultado(estado, and);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
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
            tabFormulas.aniadirEtiqueta(estado, and, resParcial);
        }
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
                GrafoCaminos<S> gaux = resParcial.getContraejemplo();
                gaux.setArista(epadre, estado);
                gaux.setInicio(epadre);
                //GrafoCaminos<S> gaux2 = GrafoCaminos.CreateGrafo(gaux, resParcial.getContraejemplo());
                //gaux2.setInicio(epadre);                
                resParcial.setContraejemplo(gaux);
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
        GrafoCaminos<S> ej = new GrafoUnico<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>();
        int i = 0;
        while (!this.auProfIter(au, ej, cej, new NodoVisitados<S>(), i)) {
            i++;
        }
    }

    private boolean auProfIter(AU au, GrafoCaminos<S> ej, GrafoCaminos<S> cej,
            NodoVisitados<S> visitados, int i) {
        boolean definido = true;  // sera false si falta profundizar mas
        S eraiz = estado;
        GrafoCaminos<S> cejauxf2, ejauxf1;
        boolean visitado, cumplef2, cumplef1;
        visitado = visitados.contains(estado);
        visitados.add(estado);
        this.comprobarFormula(estado, au.getOperando(1)); // comprobar f2
        cumplef2 = resParcial.equals(Resultado.COD_TRUE);
        if (cumplef2) { // AU entonces es cierta
            ej.union(resParcial.getEjemplo());
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
            return true;
        } else if (visitado) { // camino infinito: AU es falsa
            Resultado<S> r = tabFormulas.getResultado(estado, au.getOperando(0));
            if (r.equals(Resultado.COD_TRUE)) {
                cej.union(r.getEjemplo());
            } else {
                cej.union(r.getContraejemplo());
            }
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(cej);
            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
            return true;
        } else {  // no cumple f2 y no visitado
            cejauxf2 = resParcial.getContraejemplo();
            ej.union(cejauxf2);
            comprobarFormula(estado, au.getOperando(0)); //comprobamos f1
            cumplef1 = resParcial.equals(Resultado.COD_TRUE);
            if (!cumplef1) { //no cumple ni f1 ni f2
                cej.union(resParcial.getContraejemplo());
                cej.union(cejauxf2);
                resParcial.setContraejemplo(cej);
                tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                return true;
            } else { //cumple f1 y no f2 --> miramos hijos
                ejauxf1 = resParcial.getEjemplo();
                ej.union(ejauxf1);
                if (i == 0) { // por esta rama no se puede profundizar mas
                    ej.setInicio(eraiz);
                    return false;
                } else {
                    List<S> listaHijos = interprete.transitar(estado);
                    Iterator<S> it = listaHijos.iterator();
                    boolean hijodefinido;
                    while (it.hasNext()) {
                        estado = it.next();
                        if (tabFormulas.tieneEtiqueta(estado, au)){
                            Resultado<S> r = tabFormulas.getResultado(estado, au);
                            resParcial.setResultado(r.getResultado());
                            resParcial.setEjemplo(r.getEjemplo());
                            resParcial.setContraejemplo(r.getContraejemplo());
                            hijodefinido = true;
                        } else {
                            hijodefinido = auProfIter(au, ej, cej, new NodoVisitados<S>(visitados), i - 1);
                        }
                        if (hijodefinido) {
                            if (resParcial.equals(Resultado.COD_FALSE)) {
                                cej.union(cejauxf2);
                                cej.union(ejauxf1);
                                cej.setArista(eraiz, estado);
                                cej.setInicio(eraiz);
                                resParcial.setContraejemplo(cej);
                                estado = eraiz;
                                tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                                return true;
                            } else {
                                ej.setArista(eraiz, estado);
                            }
                        } else {
                            definido = false;
                        }
                    }
                    estado = eraiz;
                    ej.setInicio(eraiz);
                    if (definido) tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                    return definido;
                }
            }
        }
    }

    public void visita(EU eu) {
        S eraiz = estado;
        LinkedBlockingQueue2<S> colaEanterior =
                new LinkedBlockingQueue2<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>(estado);
        LinkedBlockingQueue2<GrafoCaminos<S>> colaej =
                new LinkedBlockingQueue2<GrafoCaminos<S>>();
        LinkedBlockingQueue2<S> colaEstados =
                new LinkedBlockingQueue2<S>(interprete.transitar(estado));
        for (int i = colaEstados.size(); i > 0; i--) {
            colaEanterior.offer(eraiz);
            colaej.offer(new GrafoUnico<S>(eraiz));
        }
        boolean encontrado = false;
        S eanterior;
        GrafoCaminos<S> ej = null, cejauxf2;
        HashSet<S> visitados = new HashSet<S>();
        boolean visitado, cumplef2, cumplef1;
        while (!encontrado && !colaEstados.isEmpty()) {
            estado = colaEstados.poll();
            eanterior = colaEanterior.poll();
            ej = colaej.poll();
            visitado = visitados.contains(estado);
            visitados.add(estado);
            if (!tabFormulas.tieneEtiqueta(estado, eu.getOperando(1))) {
                eu.getOperando(1).accept(this);
            } else {
                Resultado r = tabFormulas.getResultado(estado, eu.getOperando(1));
                resParcial.setResultado(r.getResultado());
                resParcial.setContraejemplo(r.getContraejemplo());
                resParcial.setEjemplo(r.getEjemplo());
            }
            cumplef2 = resParcial.equals(Resultado.COD_TRUE);
            if (cumplef2) {
                encontrado = true;
                ej.setArista(eanterior, estado);
                if (!visitado) {
                    ej.union(resParcial.getEjemplo());
                }
            } else {
                if (visitado) {
                    cej.setArista(eanterior, estado);
                } else {
                    cejauxf2 = resParcial.getContraejemplo();
                    ej.setArista(eanterior, estado);
                    cej.setArista(eanterior, estado);
                    ej.union(cejauxf2);
                    cej.union(cejauxf2);
                    if (!tabFormulas.tieneEtiqueta(estado, eu.getOperando(0))) {
                        eu.getOperando(0).accept(this);
                    } else {
                        Resultado r = tabFormulas.getResultado(estado, eu.getOperando(0));
                        resParcial.setResultado(r.getResultado());
                        resParcial.setContraejemplo(r.getContraejemplo());
                        resParcial.setEjemplo(r.getEjemplo());
                    }
                    cumplef1 = resParcial.equals(Resultado.COD_TRUE);
                    if (cumplef1) {
                        ej.union(resParcial.getEjemplo());
                        cej.union(resParcial.getEjemplo());
                        List<S> listaHijos = interprete.transitar(estado);
                        for (Iterator<S> it = listaHijos.iterator(); it.hasNext();) {
                            S s = it.next();
                            if (!visitados.contains(s)) {
                                colaEstados.offer(s);
                                colaEanterior.offer(estado);
                                colaej.offer(GrafoCaminos.CreateGrafo(ej));
                            } else {
                                cej.setArista(estado, s);
                            }
                        }
                    } else if (resParcial.equals(Resultado.COD_FALSE)) {
                        cej.setArista(eanterior, estado);
                        cej.union(resParcial.getContraejemplo());
                    }
                }
            }
        }
        if (encontrado) {
            ej.setInicio(eraiz);
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
        } else {
            cej.setInicio(eraiz);
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(cej);
        }
        estado = eraiz;
    }

    private void comprobarFormula(S s, Formula f) {
        if (!tabFormulas.tieneEtiqueta(s, f)) {
            S s2 = estado;
            estado = s;
            f.accept(this);
            estado = s2;
        } else {
            Resultado<S> r = tabFormulas.getResultado(s, f);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        }
    }
}
