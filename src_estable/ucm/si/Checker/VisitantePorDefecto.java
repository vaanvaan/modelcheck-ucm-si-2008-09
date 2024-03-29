package ucm.si.Checker;

import ucm.si.Checker.tabulacion.TabulacionFormulas;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.TreeMap;
import java.util.TreeSet;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.basico.ecuaciones.*;
// a�adir constructora para usar logs globales, si es necesario.
import ucm.si.util.*;

public class VisitantePorDefecto<S> extends Visitante<S> {

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConector<S> conector;

    public VisitantePorDefecto(Interprete<S> interprete, VisitanteConector<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
    }

    public void visita(Proposicion<S> p) {
        S estado = conector.getEstado();
        if (p.esCierta(estado)) {
            resParcial.setResultado(Resultado.COD_TRUE);
            GrafoCaminos<S> gce = new GrafoUnico<S>(estado);
            resParcial.setEjemplo(gce);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            GrafoCaminos<S> gcce = new GrafoUnico<S>(estado);
            resParcial.setContraejemplo(gcce);
        }

    }

    public void visita(Not n) {
        n.getOperando(0).accept(this.conector);
        this.resParcial = this.conector.getResParcial();
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
        or.getOperando(0).accept(this.conector);
        this.resParcial = this.conector.getResParcial();
        Resultado resIzq = new Resultado(resParcial.getResultado());
        if (resIzq.equals(Resultado.COD_TRUE)) {
            resIzq.setEjemplo(resParcial.getEjemplo());
        } else {
            resIzq.setContraejemplo(resParcial.getContraejemplo());
        }
        or.getOperando(1).accept(this.conector);
        this.resParcial = this.conector.getResParcial();
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
            } else if (!part1 && part2) {
                resOR.setEjemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(), resDer.getEjemplo()));
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
        and.getOperando(0).accept(this.conector);
        this.resParcial = conector.getResParcial();
        Resultado resIzq = new Resultado(resParcial.getResultado());
        if (resIzq.equals(Resultado.COD_TRUE)) {
            resIzq.setEjemplo(resParcial.getEjemplo());
        } else {
            resIzq.setContraejemplo(resParcial.getContraejemplo());
        }
        and.getOperando(1).accept(this.conector);
        this.resParcial = this.conector.getResParcial();
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
            boolean andTotal = part1 && part2;
            resAND = new Resultado(String.valueOf(andTotal));
            if (andTotal) {
                resAND.setEjemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getEjemplo(), resDer.getEjemplo()));
            } else if (part2) {
                resAND.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(), resDer.getEjemplo()));
            } else if (part1) {
                resAND.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getEjemplo(), resDer.getContraejemplo()));
            } else {
                resAND.setContraejemplo(GrafoCaminos.CreateGrafo(
                        resIzq.getContraejemplo(), resDer.getContraejemplo()));
            }
            this.resParcial = resAND;
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
        S estado = this.conector.getEstado();
        List<S> listaEstados;
        S epadre = estado;
        GrafoCaminos<S> ej = new GrafoUnico<S>(epadre);
        listaEstados = interprete.transitar(epadre);
        Iterator<S> it = listaEstados.iterator();
        boolean seguir = true;
        while (it.hasNext() && seguir) {
            estado = it.next();
            this.conector.setEstado(estado);
            allnext.getOperando(0).accept(this.conector);
            this.resParcial = this.conector.getResParcial();
            if (!resParcial.equals(Resultado.COD_TRUE)) {
                seguir = false;
                //resParcial se queda con false, asi que no lo tocamos.
                GrafoCaminos<S> gaux = resParcial.getContraejemplo();
                gaux.setArista(epadre, estado);
                gaux.setInicio(epadre);
                //GrafoCaminos<S> gaux2 = GrafoCaminos.CreateGrafo(gaux, resParcial.getContraejemplo());
                //gaux2.setInicio(epadre);                
                resParcial.setContraejemplo(gaux);
            } else {
                ej.setArista(epadre, estado);
            }
        }
        if (seguir) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
        }
        estado = epadre;
        this.conector.setEstado(estado);
        this.conector.setResParcial(this.resParcial);
    }

    public void visita(EX eventx) {
        S estado = this.conector.getEstado();
        List<S> listaEstados;
        S epadre = estado;
        listaEstados = interprete.transitar(estado);
        Iterator<S> it = listaEstados.iterator();
        boolean seguir = true;
        GrafoCaminos<S> grafoaux = new GrafoUnico<S>(estado);
        while (seguir && it.hasNext()) {
            estado = it.next();
            this.conector.setEstado(estado);
            eventx.getOperando(0).accept(this.conector);
            this.resParcial = this.conector.getResParcial();
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
        this.conector.setEstado(estado);
    }

    public void visita(AU au) {
        S estado = this.conector.getEstado();
        GrafoCaminos<S> ej = new GrafoUnico<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>();
        int i = 0;
        while (!this.auProfIter(au, ej, cej, new NodoVisitados<S>(), i)) {
            i++;
        }
        this.conector.setEstado(estado);
    }

    private boolean auProfIter(AU au, GrafoCaminos<S> ej, GrafoCaminos<S> cej,
            NodoVisitados<S> visitados, int i) {
        S estado = this.conector.getEstado();
        boolean definido = true;  // sera false si falta profundizar mas
        S eraiz = estado;
        GrafoCaminos<S> cejauxf2, ejauxf1;
        boolean visitado, cumplef2, cumplef1;
        visitado = visitados.contains(estado);
        visitados.add(estado);
        this.comprobarFormula(estado, au.getOperando(1)); // comprobar f2
        cumplef2 = this.resParcial.equals(Resultado.COD_TRUE);
        if (cumplef2) { // AU entonces es cierta
            ej.union(resParcial.getEjemplo());
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
            return true;
        } else if (visitado) { // camino infinito: AU es falsa
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(cej);
            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
            return true;
        } else {  // no cumple f2 y no visitado
            cejauxf2 = resParcial.getContraejemplo();
            ej.union(cejauxf2);
            comprobarFormula(estado, au.getOperando(0)); //comprobamos f1
            estado = this.conector.getEstado();
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
                    if (!listaHijos.isEmpty()) {
                        Iterator<S> it = listaHijos.iterator();
                        boolean hijodefinido;
                        while (it.hasNext()) {
                            estado = it.next();
                            this.conector.setEstado(estado);
                            if (tabFormulas.tieneEtiqueta(estado, au)) {
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
                                    this.conector.setEstado(estado);
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
                        this.conector.setEstado(estado);
                        ej.setInicio(eraiz);
                        resParcial.setEjemplo(ej);
                        if (definido) {
                            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                        }
                        return definido;
                    } else {
                        cej.union(cejauxf2);
                        cej.union(ejauxf1);
                        cej.setInicio(eraiz);
                        resParcial.setContraejemplo(cej);
                        resParcial.setResultado(Resultado.COD_FALSE);
                        this.conector.setEstado(eraiz);
                        tabFormulas.aniadirEtiqueta(eraiz, au, resParcial);
                        return true;
                    }
                }
            }
        }
    }

    public void visita(EU eu) {
        S estado = this.conector.getEstado();
        S eraiz = estado;
        Cola<S> colaEanterior =
                new Cola<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>();
        Cola<GrafoCaminos<S>> colaej =
                new Cola<GrafoCaminos<S>>();
        Cola<S> colaEstados =
                new Cola<S>();
        colaEstados.offer(estado);
        colaEanterior.offer(null);
        colaej.offer(new GrafoUnico<S>());
        boolean encontrado = false;
        S eanterior;
        GrafoCaminos<S> ej = null, cejauxf2;
        TreeSet<S> visitados = new TreeSet<S>();
        boolean visitado, cumplef2, cumplef1;
        while (!encontrado && !colaEstados.isEmpty()) {
            estado = colaEstados.poll();
            this.conector.setEstado(estado);
            eanterior = colaEanterior.poll();
            ej = colaej.poll();
            visitado = visitados.contains(estado);
            visitados.add(estado);
            if (!tabFormulas.tieneEtiqueta(estado, eu.getOperando(1))) {
                eu.getOperando(1).accept(this.conector);
                this.resParcial = this.conector.getResParcial();
            } else {
                Resultado r = tabFormulas.getResultado(estado, eu.getOperando(1));
                resParcial.setResultado(r.getResultado());
                resParcial.setContraejemplo(r.getContraejemplo());
                resParcial.setEjemplo(r.getEjemplo());
            }
            cumplef2 = resParcial.equals(Resultado.COD_TRUE);
            if (cumplef2) {
                encontrado = true;
                if (eanterior != null) {
                    ej.setArista(eanterior, estado);
                }
                if (!visitado) {
                    ej.union(resParcial.getEjemplo());
                }
            } else {
                if (visitado) {
                    cej.setArista(eanterior, estado);
                } else {
                    cejauxf2 = resParcial.getContraejemplo();
                    if (eanterior != null) {
                        ej.setArista(eanterior, estado);
                        cej.setArista(eanterior, estado);
                    }
                    ej.union(cejauxf2);
                    cej.union(cejauxf2);
                    if (!tabFormulas.tieneEtiqueta(estado, eu.getOperando(0))) {
                        eu.getOperando(0).accept(this.conector);
                        this.resParcial = this.conector.getResParcial();
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
                        if (eanterior != null) {
                            cej.setArista(eanterior, estado);
                        }
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
        this.conector.setEstado(estado);
    }

    private void comprobarFormula(S s, Formula f) {
        S estado = this.conector.getEstado();
        if (!tabFormulas.tieneEtiqueta(s, f)) {
            S s2 = estado;
            estado = s;
            this.conector.setEstado(estado);
            f.accept(this.conector);
            this.resParcial = this.conector.getResParcial();
            estado = s2;
            this.conector.setEstado(estado);
        } else {
            Resultado<S> r = tabFormulas.getResultado(s, f);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        }
    }
}
