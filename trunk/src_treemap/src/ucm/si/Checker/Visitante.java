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

import javax.swing.JOptionPane;
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
    private Stack<Formula> pilaFormulas = new Stack<Formula>();
    private boolean inicio = false;
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
        S eraiz = estado;
        LinkedBlockingQueue<TreeSet<S>> colaVisitados =
                new LinkedBlockingQueue<TreeSet<S>>();
        LinkedBlockingQueue<S> colaEanterior =
                new LinkedBlockingQueue<S>();
        GrafoCaminos<S> ej = new GrafoUnico<S>(estado);
        LinkedBlockingQueue<GrafoCaminos<S>> colacej =
                new LinkedBlockingQueue<GrafoCaminos<S>>();
        LinkedBlockingQueue<S> colaEstados =
                new LinkedBlockingQueue<S>(interprete.transitar(estado));
        for (int i = colaEstados.size(); i > 0; i--) {
            colaVisitados.offer(new TreeSet<S>());
            colaEanterior.offer(eraiz);
            colacej.offer(new GrafoUnico<S>(eraiz));
        }
        boolean encontrado = false;
        S eanterior;
        GrafoCaminos<S> cej = null, cejauxf2;
        TreeSet<S> visitados;
        boolean visitado, cumplef2, cumplef1;
        while (!encontrado && !colaEstados.isEmpty()) {
            estado = colaEstados.poll();
            eanterior = colaEanterior.poll();
            cej = colacej.poll();
            visitados = colaVisitados.poll();
            visitado = visitados.contains(estado);
            visitados.add(estado);
            au.getOperando(1).accept(this);
            cumplef2 = resParcial.equals(Resultado.COD_TRUE);
            if (cumplef2) {
                ej.setArista(eanterior, estado);
                if (!visitado) {
                    ej.union(resParcial.getEjemplo());
                }
            } else if (visitado) {
                encontrado = true;
                cej.setArista(eanterior, estado);
            } else {
                cejauxf2 = resParcial.getContraejemplo();
                ej.setArista(eanterior, estado);
                cej.setArista(eanterior, estado);
                ej.union(cejauxf2);
                cej.union(cejauxf2);
                au.getOperando(0).accept(this);
                cumplef1 = resParcial.equals(Resultado.COD_TRUE);
                if (cumplef1) {
                    ej.union(resParcial.getEjemplo());
                    cej.union(resParcial.getEjemplo());
                    List<S> listaHijos = interprete.transitar(estado);
                    for (Iterator<S> it = listaHijos.iterator(); it.hasNext();) {
                        S s = it.next();
                        colaEstados.offer(s);
                        colaEanterior.offer(estado);
                        colaVisitados.offer(new TreeSet<S>(visitados));
                        colacej.offer(GrafoCaminos.CreateGrafo(cej));
                    }
                } else {
                    encontrado = true;
                    cej.setArista(eanterior, estado);
                    cej.union(resParcial.getContraejemplo());
                }
            }
        }
        if (!encontrado) {
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

    public void visita(EU eu) {
        S eraiz = estado;
        /*LinkedBlockingQueue2<NodoVisitados<S>> colaVisitados =
                new LinkedBlockingQueue2<NodoVisitados<S>>();*/
        LinkedBlockingQueue2<S> colaEanterior =
                new LinkedBlockingQueue2<S>();
        GrafoCaminos<S> cej = new GrafoUnico<S>(estado);
        LinkedBlockingQueue2<GrafoCaminos<S>> colaej =
                new LinkedBlockingQueue2<GrafoCaminos<S>>();
        LinkedBlockingQueue2<S> colaEstados =
                new LinkedBlockingQueue2<S>(interprete.transitar(estado));
        for (int i = colaEstados.size(); i > 0; i--) {
            //colaVisitados.offer(new NodoVisitados<S>());
            colaEanterior.offer(eraiz);
            colaej.offer(new GrafoUnico<S>(eraiz));
        }
        boolean encontrado = false;
        S eanterior;
        GrafoCaminos<S> ej = null, cejauxf2;
        //NodoVisitados<S> visitados = ;
        HashSet<S> visitados = new HashSet<S>();
        boolean visitado, cumplef2, cumplef1;
        while (!encontrado && !colaEstados.isEmpty()) {
            estado = colaEstados.poll();
            eanterior = colaEanterior.poll();
            ej = colaej.poll();
            //visitados = colaVisitados.poll();
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
                                //colaVisitados.offer(new NodoVisitados<S>(visitados));
                                colaej.offer(GrafoCaminos.CreateGrafo(ej));
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
}
