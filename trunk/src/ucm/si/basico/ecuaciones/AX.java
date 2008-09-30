package ucm.si.basico.ecuaciones;

import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.2AD68957-6A72-B3B8-D7C3-5328088B8926]
// </editor-fold> 
public class AX extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C8648BD8-AF1F-D594-E20F-7D487E66523C]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DEFB382B-8390-7765-B764-831D2F599BFA]
    // </editor-fold> 
    public void accept (Visitante v) {
    	v.visita(this);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C39E1C8E-2B45-86C1-F509-DD1006292922]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.34CDD849-C84B-C518-FB4B-35C133E6383D]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

