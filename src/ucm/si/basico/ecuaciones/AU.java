
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.D5E40A8C-8E1F-EAE5-5813-94533D620864]
// </editor-fold> 
public abstract class AU extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DB37CE66-5611-3E70-5395-97DAAFF623AF]
    // </editor-fold> 
    private Formula[] mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F14C3477-D524-4577-BFE1-2CE5C6893C18]
    // </editor-fold> 
    public AU () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.478C32E3-7565-3633-5101-73FC52B311F5]
    // </editor-fold> 
    public Formula[] getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.731105DE-D1A6-54DF-3009-E2C2FDBE9F11]
    // </editor-fold> 
    public void setFormula (Formula[] val) {
        this.mFormula = val;
    }

}

