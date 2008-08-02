
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.232CA67B-758C-D54D-26EC-244346BE81DA]
// </editor-fold> 
public abstract class EU extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FFD04807-4A0A-7962-FA5A-287AECA34596]
    // </editor-fold> 
    private Formula[] mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D847A02A-9AAE-044E-0FCE-4658352FF48D]
    // </editor-fold> 
    public EU () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D254D41E-D547-E56C-1058-6AAEE478409A]
    // </editor-fold> 
    public Formula[] getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.30FD2E42-15BD-4AFE-962A-7FD719EFFBC4]
    // </editor-fold> 
    public void setFormula (Formula[] val) {
        this.mFormula = val;
    }

}

