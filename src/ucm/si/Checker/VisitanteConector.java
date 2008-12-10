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

public class VisitanteConector<S> extends Visitante<S> {
    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    public static final int TPROP = 0;
    public static final int TAND = 1;
    public static final int TOR = 2;
    public static final int TNOT = 3;
    public static final int TAX = 4;
    public static final int TEX = 5;
    public static final int TAU = 6;
    public static final int TEU = 7;
    private Visitante[] visitantes= new Visitante[8];

    public VisitanteConector(S estado, Interprete<S> interprete) {
        super();
        this.estado = estado;
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        Visitante<S> visitantePorDefecto = new VisitantePorDefecto<S>(estado,interprete,this);
        for (int i=0;i<8;i++)
            this.visitantes[i] = visitantePorDefecto;
        
    //this.tabFormulas = new TabulacionFormulas<S>();
    }
    
    public void setVisitante(int TFormula, Visitante<S> v){
        this.visitantes[TFormula] = v;
    }

    public void visita(Proposicion<S> p) {
        if (tabFormulas.tieneEtiqueta(estado, p)) {
            Resultado r = tabFormulas.getResultado(estado, p);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes[TPROP].actualizar(estado,resParcial);
            visitantes[TPROP].visita(p);
            this.resParcial = visitantes[TPROP].getResParcial();            
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
            visitantes[TNOT].actualizar(estado,resParcial);
            visitantes[TNOT].visita(n);
            this.resParcial = visitantes[TNOT].getResParcial();
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
            visitantes[TOR].actualizar(estado,resParcial);
            visitantes[TOR].visita(or);
            this.resParcial = visitantes[TOR].getResParcial();
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
            visitantes[TAND].actualizar(estado,resParcial);
            visitantes[TAND].visita(and);
            this.resParcial = visitantes[TAND].getResParcial();            
            this.tabFormulas.aniadirEtiqueta(estado, and, resParcial);
        }
    }

    public void visita(AX allnext) {
        if (tabFormulas.tieneEtiqueta(estado, allnext)) {
            Resultado r = tabFormulas.getResultado(estado, allnext);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes[TAX].actualizar(estado,resParcial);
            visitantes[TAX].visita(allnext);
            this.resParcial = visitantes[TAX].getResParcial();            
            this.tabFormulas.aniadirEtiqueta(estado, allnext, resParcial);
        }
    }

    public void visita(EX eventx) {
        if (tabFormulas.tieneEtiqueta(estado, eventx)) {
            Resultado r = tabFormulas.getResultado(estado, eventx);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes[TEX].actualizar(estado,resParcial);
            visitantes[TEX].visita(eventx);
            this.resParcial = visitantes[TEX].getResParcial();            
            this.tabFormulas.aniadirEtiqueta(estado, eventx, resParcial);
        }
    }

    public void visita(AU au) {
        if (tabFormulas.tieneEtiqueta(estado, au)) {
            Resultado r = tabFormulas.getResultado(estado, au);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes[TAU].actualizar(estado,resParcial);
            visitantes[TAU].visita(au);
            this.resParcial = visitantes[TAU].getResParcial();            
            this.tabFormulas.aniadirEtiqueta(estado, au, resParcial);
        }
    }

    
    public void visita(EU eu) {
        if (tabFormulas.tieneEtiqueta(estado, eu)) {
            Resultado r = tabFormulas.getResultado(estado, eu);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes[TEU].actualizar(estado,resParcial);
            visitantes[TEU].visita(eu);
            this.resParcial = visitantes[TEU].getResParcial();            
            this.tabFormulas.aniadirEtiqueta(estado, eu, resParcial);
        }
    }
    
}
