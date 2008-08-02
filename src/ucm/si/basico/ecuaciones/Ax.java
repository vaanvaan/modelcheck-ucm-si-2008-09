
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.2AD68957-6A72-B3B8-D7C3-5328088B8926]
// </editor-fold> 
public abstract class Ax extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EC7C5432-849A-EDDC-BB52-BAEA11820369]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.630CD900-8DCB-7326-337F-6A8CC4E164AC]
    // </editor-fold> 
    public Ax () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DCB0A7A2-B8DF-60B7-8A31-B2D7B0447C3F]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.18DC782C-B9B3-149B-AAE3-83F7ADA67C99]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

