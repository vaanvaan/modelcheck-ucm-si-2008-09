package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.VisitanteConector;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.D5E40A8C-8E1F-EAE5-5813-94533D620864]
// </editor-fold> 
public class AU extends OperacionBinaria {
    
    public AU(Formula f0, Formula f1) {
        super(f0, f1);
    }

    @Override
    public void accept(VisitanteConector v) {
        v.visita(this);
    }

}

