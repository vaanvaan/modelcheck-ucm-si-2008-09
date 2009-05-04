package ucm.si.Checker.modular.defecto;

import ucm.si.basico.ecuaciones.Proposicion;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.util.GrafoCaminos;
import ucm.si.util.GrafoUnico;

public class VisitantePorDefectoProposicion<S> extends ModuloVisitante implements VisitanteFormula<S,Proposicion<S>>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoProposicion(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
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
}
