package ucm.si.basico.ecuaciones;

import ucm.si.Checker.VisitanteConector;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.2AD68957-6A72-B3B8-D7C3-5328088B8926]
// </editor-fold> 
public class AX extends OperacionUnaria {

    public AX(Formula f) {
        super(f);
    }

    @Override
    public void accept(VisitanteConector v) {
        v.visita(this);
    }

}

