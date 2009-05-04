package ucm.si.Checker;

import java.util.EnumMap;
import ucm.si.Checker.tabulacion.TabulacionFormulas;

import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.basico.ecuaciones.*;
// a�adir constructora para usar logs globales, si es necesario.
public class VisitanteConector<S> extends Visitante<S> {

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    public enum TFormula {PROP,AND,OR,NOT,AX,EX,AU,EU};
    private EnumMap<TFormula,Visitante<S>> visitantes = new EnumMap<TFormula, Visitante<S>>(TFormula.class);
    private S estado;
    

    public S getEstado() {
        return estado;
    }

    public void setEstado(S estado) {
        this.estado = estado;
    }

    public VisitanteConector(S estado, Interprete<S> interprete) {
        super();
        this.estado = estado;
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        Visitante<S> visitantePorDefecto = new VisitantePorDefecto<S>(interprete, this);
        for (TFormula tf : TFormula.values()) {
            this.visitantes.put(tf, visitantePorDefecto);
        }
    }

    public void setVisitante(TFormula t, Visitante<S> v) {
        this.visitantes.put(t,v);
    }

    public void visita(Proposicion<S> p) {
        if (tabFormulas.tieneEtiqueta(estado, p)) {
            Resultado r = tabFormulas.getResultado(estado, p);
            resParcial.setResultado(r.getResultado());
            resParcial.setContraejemplo(r.getContraejemplo());
            resParcial.setEjemplo(r.getEjemplo());
        } else {
            visitantes.get(TFormula.PROP).visita(p);
            this.resParcial = visitantes.get(TFormula.PROP).getResParcial();
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
            visitantes.get(TFormula.NOT).visita(n);
            this.resParcial = visitantes.get(TFormula.NOT).getResParcial();
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
            visitantes.get(TFormula.OR).visita(or);
            this.resParcial = visitantes.get(TFormula.OR).getResParcial();
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
            visitantes.get(TFormula.AND).visita(and);
            this.resParcial = visitantes.get(TFormula.AND).getResParcial();
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
            visitantes.get(TFormula.AX).visita(allnext);
            this.resParcial = visitantes.get(TFormula.AX).getResParcial();
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
            visitantes.get(TFormula.EX).visita(eventx);
            this.resParcial = visitantes.get(TFormula.EX).getResParcial();
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
            visitantes.get(TFormula.AU).visita(au);
            this.resParcial = visitantes.get(TFormula.AU).getResParcial();
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
            visitantes.get(TFormula.EU).visita(eu);
            this.resParcial = visitantes.get(TFormula.EU).getResParcial();
            this.tabFormulas.aniadirEtiqueta(estado, eu, resParcial);
        }
    }
}
