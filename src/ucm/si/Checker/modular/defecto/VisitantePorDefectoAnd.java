package ucm.si.Checker.modular.defecto;

import ucm.si.basico.ecuaciones.And;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.util.GrafoCaminos;


public class VisitantePorDefectoAnd<S> extends ModuloVisitante implements VisitanteFormula<S,And>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoAnd(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
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
        this.conector.setResParcial(this.resParcial);
    }

}
