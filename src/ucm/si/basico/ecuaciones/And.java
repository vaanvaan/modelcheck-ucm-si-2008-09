package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.0DF9B8A5-3C1E-E7D4-7697-07930880CE80]
// </editor-fold> 
public class And extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5D0C91D1-10B8-73DD-6845-A33253E2E6C0]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D5A1FB0D-6E5E-D528-CAD6-ADCC3C6884CB]
    // </editor-fold> 
    public void accept (ModelChecker mc) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DAEAFFD7-C9E4-A051-7457-A7A84D26D6A1]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7C6E2AA6-3D9A-AE89-737D-EBF9EBC312FE]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

