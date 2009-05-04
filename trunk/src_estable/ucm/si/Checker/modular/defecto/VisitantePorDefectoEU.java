package ucm.si.Checker.modular.defecto;

import java.util.HashSet;
import ucm.si.basico.ecuaciones.EU;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import ucm.si.Checker.Cola;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.util.GrafoCaminos;
import ucm.si.util.GrafoUnico;

public class VisitantePorDefectoEU<S> extends ModuloVisitante implements VisitanteFormula<S,EU>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoEU(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
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
