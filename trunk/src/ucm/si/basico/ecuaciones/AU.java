package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.D5E40A8C-8E1F-EAE5-5813-94533D620864]
// </editor-fold> 
public class AU extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.69C3CA39-095E-B121-8D66-8FDA84593686]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.98EC09BD-E8C6-0EC9-E85F-C07B900048B5]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.82954675-AEF2-D494-409C-CB3B4B7CA123]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

	@Override
	public void accept(Visitante v) {
		// TODO Auto-generated method stub
		
	}

}

