package ucm.si.basico.ecuaciones;

import ucm.si.Checker.VisitanteConector;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.0DF9B8A5-3C1E-E7D4-7697-07930880CE80]
// </editor-fold> 
public class And extends OperacionBinaria {

    public And(Formula f0, Formula f1) {
        super(f0, f1);
    }

    @Override
    public void accept(VisitanteConector v) {
        v.visita(this);
    }

}