package ucm.si.Checker.modular.defecto;

import ucm.si.basico.ecuaciones.Or;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.util.GrafoCaminos;

public class VisitantePorDefectoOr<S> extends ModuloVisitante implements VisitanteFormula<S,Or>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoOr(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
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

}
