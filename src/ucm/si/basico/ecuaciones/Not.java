
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7AE3FC8A-1924-11A1-E384-B388D3300E65]
// </editor-fold> 
public abstract class Not extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8CA553CF-A245-0A79-F768-B642CF7BF1A6]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3776FB35-0A0D-C8D8-AB1B-CA05C5C5B533]
    // </editor-fold> 
    public Not () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.266D22AF-EEA7-E558-B383-96E1588B9758]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1AC755D8-9106-B28F-9FF4-16FAFFE75EAC]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

