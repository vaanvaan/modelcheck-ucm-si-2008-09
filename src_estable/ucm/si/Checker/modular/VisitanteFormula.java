package ucm.si.Checker.modular;

import ucm.si.Checker.Resultado;
import ucm.si.basico.ecuaciones.Formula;

public interface VisitanteFormula<S,F extends Formula> {

    public void visita(F f);
    
    public Resultado getResParcial();

    public void setResParcial(Resultado resParcial);
}
