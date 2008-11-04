package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.232CA67B-758C-D54D-26EC-244346BE81DA]
// </editor-fold> 
public class EU extends OperacionBinaria {
    
    public EU(Formula f0, Formula f1) {
        super(f0, f1);
    }

    @Override
    public void accept(Visitante v) {
        v.visita(this);
    }

}

