package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.232CA67B-758C-D54D-26EC-244346BE81DA]
// </editor-fold> 
public class EU extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C0FEF54A-FC5C-8312-A682-A522F92AFEBD]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F2B516B0-EC21-C4F1-085D-E636B39F98C8]
    // </editor-fold> 
    public void accept (ModelChecker mc) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BF866D23-4A26-293E-A5AC-C4FB5401E57A]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BFEF54EF-22BF-2F06-9594-1F8399EF0850]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

