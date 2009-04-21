package ucm.si.Checker.modular.defecto;

import java.util.Iterator;
import java.util.List;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.NodoVisitados;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.util.GrafoCaminos;
import ucm.si.util.GrafoUnico;

public class VisitantePorDefectoAU<S> extends ModuloVisitante implements VisitanteFormula<S, AU> {

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;

    public VisitantePorDefectoAU(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
    }

    public void visita(AU au) {
        S estado = this.conector.getEstado();
        GrafoCaminos<S> ej = new GrafoUnico<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>();
        int i = 0;
        while (!this.auProfIter(au, ej, cej, new NodoVisitados<S>(), i)) {
            i++;
        }
        this.conector.setResParcial(this.resParcial);
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
        estado = this.conector.getEstado();
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
                    if (listaHijos.isEmpty()) { // Aqui hay un contraejemplo
                        cej.union(cejauxf2);
                        cej.union(ejauxf1);
                        cej.setInicio(eraiz);
                        resParcial.setResultado(Resultado.COD_FALSE);
                        resParcial.setContraejemplo(cej);
                        tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                        return true;
                    } else {
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
                        if (definido) {
                            tabFormulas.aniadirEtiqueta(estado, au, resParcial);
                        }
                        return definido;
                    }
                }
            }
        }
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
