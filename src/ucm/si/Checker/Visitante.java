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
        if (p.esCierta(estado)) {
            resParcial.setResultado(Resultado.COD_TRUE);
            GrafoCaminos gce = GrafoCaminos.CreateGrafo();
            gce.setS(estado, new ArrayList<S>());
            gce.setInicio(estado);
            resParcial.setEjemplo(gce);
            this.tabFormulas.aniadirEtiqueta(estado, p);
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
            this.tabFormulas.aniadirEtiqueta(estado, n);
            
        } else if (resParcial.equals(Resultado.COD_MAYBEF)) {
            resParcial.setResultado(Resultado.COD_MAYBET);
            this.tabFormulas.aniadirEtiqueta(estado, n);
        } else {
            resParcial.setResultado(Resultado.COD_MAYBEF);
        }
    }

    public void visita(Or or) {
        or.getOperando(0).accept(this);
        Resultado resIzq = new Resultado(resParcial.getResultado());
        if (resIzq.equals(Resultado.COD_TRUE)) {
            resIzq.setEjemplo(resParcial.getEjemplo());
            this.tabFormulas.aniadirEtiqueta(estado, or);
        } else {
            resIzq.setContraejemplo(resParcial.getContraejemplo());
        }
        or.getOperando(1).accept(this);
        Resultado resDer = new Resultado(resParcial.getResultado());
        if (resDer.equals(Resultado.COD_TRUE)) {
            resDer.setEjemplo(resParcial.getEjemplo());
            this.tabFormulas.aniadirEtiqueta(estado, or);
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
        HashSet<S> visitados = new HashSet<S>();
        au2(au,visitados);
    }
    
    public void au2(AU au,Set<S> visitados){
        S eanterior = estado;
        GrafoCaminos<S> ej = new GrafoUnico<S>(estado);
        GrafoCaminos<S> cej = new GrafoUnico<S>(estado);
        GrafoCaminos<S> cejauxf2;
        List<S> hijos = interprete.transitar(estado);
        boolean encontrado = false;
        Iterator<S> it = hijos.iterator();
        while (!encontrado&&it.hasNext()){
            estado = it.next();
            boolean visitado = visitados.contains(estado);
            visitados.add(estado);
            au.getOperando(1).accept(this);
            boolean cumplef2 = resParcial.equals(Resultado.COD_TRUE);
            if (cumplef2){
                ej.setArista(eanterior, estado);
                if (!visitado){
                    ej = GrafoCaminos.CreateGrafo(ej, resParcial.getEjemplo());
                }
            } else if (visitado){
                    encontrado=true;
                    cej.setArista(eanterior, estado);                    
                } else {
                    cejauxf2 = resParcial.getContraejemplo();
                    au.getOperando(0).accept(this);
                    boolean cumplef1 = resParcial.equals(Resultado.COD_TRUE);
                    if (cumplef1) {
                        ej.setArista(eanterior, estado);
                        ej = GrafoCaminos.CreateGrafo(ej, resParcial.getEjemplo());
                        au2(au,new HashSet<S>(visitados));
                        boolean cumpleau = resParcial.equals(Resultado.COD_TRUE);
                        if (cumpleau) {
                            ej = GrafoCaminos.CreateGrafo(ej, resParcial.getEjemplo());
                            ej = GrafoCaminos.CreateGrafo(ej, cejauxf2);
                            ej.setInicio(eanterior);
                        } else {
                            encontrado = true;
                            cej.setArista(eanterior, estado);
                            cej = GrafoCaminos.CreateGrafo(cej, resParcial.getContraejemplo());
                            cej = GrafoCaminos.CreateGrafo(cej, cejauxf2);
                            cej.setInicio(eanterior);
                        }
                    } else {
                        encontrado = true;
                        cej.setArista(eanterior, estado);
                        cej = GrafoCaminos.CreateGrafo(cej, resParcial.getContraejemplo());
                        cej = GrafoCaminos.CreateGrafo(cej, cejauxf2);
                        cej.setInicio(eanterior);
                    }
                }
        }
        if (!encontrado) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(cej);
        }
        estado = eanterior;
    }

    public void visita(EU eu) {        
        HashSet<S> visitados = new HashSet<S>();
        eu2(eu,visitados);
    }
    
    public void eu2(EU eu,Set<S> visitados){
        S eanterior = estado;
        GrafoCaminos<S> ej = new GrafoUnico<S>(estado);
        GrafoCaminos<S> cej = new GrafoUnico<S>(estado);
        GrafoCaminos<S> cejauxf2;
        List<S> hijos = interprete.transitar(estado);
        boolean encontrado = false;
        Iterator<S> it = hijos.iterator();
        while (!encontrado&&it.hasNext()){
            estado = it.next();
            boolean visitado = visitados.contains(estado);
            visitados.add(estado);
            eu.getOperando(1).accept(this);
            boolean cumplef2 = resParcial.equals(Resultado.COD_TRUE);
            if (cumplef2){
                encontrado = true;
                ej.setArista(eanterior, estado);
                ej = GrafoCaminos.CreateGrafo(ej, resParcial.getEjemplo());
            } else if (visitado){
                    cej.setArista(eanterior, estado);                    
                } else {
                    cejauxf2 = resParcial.getContraejemplo();
                    eu.getOperando(0).accept(this);
                    boolean cumplef1 = resParcial.equals(Resultado.COD_TRUE);
                    if (cumplef1) {
                        GrafoCaminos<S> ejauxf1 = resParcial.getEjemplo();                        
                        eu2(eu,new HashSet<S>(visitados));
                        boolean cumpleeu = resParcial.equals(Resultado.COD_TRUE);
                        if (cumpleeu) {
                            encontrado = true;
                            ej.setArista(eanterior, estado);
                            ej = GrafoCaminos.CreateGrafo(ej, resParcial.getEjemplo());
                            ej = GrafoCaminos.CreateGrafo(ej, ejauxf1);
                            ej = GrafoCaminos.CreateGrafo(ej, cejauxf2);
                            ej.setInicio(eanterior);
                        } else {                            
                            cej.setArista(eanterior, estado);
                            cej = GrafoCaminos.CreateGrafo(cej, resParcial.getContraejemplo());
                            cej = GrafoCaminos.CreateGrafo(cej, ejauxf1);
                            cej = GrafoCaminos.CreateGrafo(cej, cejauxf2);
                            cej.setInicio(eanterior);
                        }
                    } else {
                        cej.setArista(eanterior, estado);
                        cej = GrafoCaminos.CreateGrafo(cej, resParcial.getContraejemplo());
                        cej = GrafoCaminos.CreateGrafo(cej, cejauxf2);
                        cej.setInicio(eanterior);
                    }
                }
        }
        if (encontrado) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
        } else {
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(cej);
        }
        estado = eanterior;
    }
}
