package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.709D66FA-53B6-D1F5-0872-724E95CC588D]
// </editor-fold> 
public class EX extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4D3D4048-E192-F9C9-6086-09A6628DB370]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.75B4568D-9DEC-897F-BBC3-DD66406B2892]
    // </editor-fold> 
    public void accept (ModelChecker mc) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.91F972C4-833C-D70C-CCA0-596814AD779B]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.4621A952-30FD-D4A1-523F-0B13F981374D]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

