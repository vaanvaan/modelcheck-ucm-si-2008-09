package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.14B913A8-ED73-E3E0-9667-7203B3DB2505]
// </editor-fold> 
public class Or extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C3F27E39-5F97-31DE-1912-42F9325C9FBA]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E286E01C-C0F7-2EF9-5424-205D7080718D]
    // </editor-fold> 
    public void accept (ModelChecker mc) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7F36B771-DE63-D884-A7B8-2A0D20D432B6]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C407560B-353E-5C7F-6E21-5FAAAF142CD3]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

