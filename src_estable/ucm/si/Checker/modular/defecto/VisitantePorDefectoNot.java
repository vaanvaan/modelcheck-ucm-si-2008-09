package ucm.si.Checker.modular.defecto;

import ucm.si.basico.ecuaciones.Not;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;

public class VisitantePorDefectoNot<S> extends ModuloVisitante implements VisitanteFormula<S,Not>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoNot(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
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

}
