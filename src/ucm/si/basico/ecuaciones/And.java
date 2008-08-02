
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.0DF9B8A5-3C1E-E7D4-7697-07930880CE80]
// </editor-fold> 
public abstract class And extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BC7F572A-D342-83D0-F58D-E263DA54D895]
    // </editor-fold> 
    private Formula[] mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C423FCCC-E040-CC71-B23F-26E2CE5415DA]
    // </editor-fold> 
    public And () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.ED65E2E5-3F2E-08CA-47F2-FA58011021CD]
    // </editor-fold> 
    public Formula[] getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.549B6261-288F-6CE9-EFD8-6067B79F59EC]
    // </editor-fold> 
    public void setFormula (Formula[] val) {
        this.mFormula = val;
    }

}

