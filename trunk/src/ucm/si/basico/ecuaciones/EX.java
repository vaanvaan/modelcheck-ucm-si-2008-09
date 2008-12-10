package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.VisitanteConector;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.709D66FA-53B6-D1F5-0872-724E95CC588D]
// </editor-fold> 
public class EX extends OperacionUnaria {

    public EX(Formula f) {
        super(f);
    }

    @Override
    public void accept(VisitanteConector v) {
        v.visita(this);
    }
    
}

