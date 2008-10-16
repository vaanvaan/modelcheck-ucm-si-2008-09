package ucm.si.basico.ecuaciones;


import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.14B913A8-ED73-E3E0-9667-7203B3DB2505]
// </editor-fold> 
public class Or extends OperacionBinaria {

    public Or(Formula f0, Formula f1) {
        super(f0, f1);
    }

    @Override
    public void accept(Visitante v) {
        v.visita(this);
    }
    
}

