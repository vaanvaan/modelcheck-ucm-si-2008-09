package ucm.si.basico.ecuaciones;

import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.232CA67B-758C-D54D-26EC-244346BE81DA]
// </editor-fold> 
public class EU extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C0FEF54A-FC5C-8312-A682-A522F92AFEBD]
    // </editor-fold> 
    private Formula mFormula;
        private Formula exprIzq;
    private Formula exprDer;

    public Formula getExprDer() {
        return exprDer;
    }

    public Formula getExprIzq() {
        return exprIzq;
    }
    
    public EU(Formula exprIzq, Formula exprDer) {
        this.exprIzq = exprIzq;
        this.exprDer = exprDer;
    }



    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BF866D23-4A26-293E-A5AC-C4FB5401E57A]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BFEF54EF-22BF-2F06-9594-1F8399EF0850]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }
	@Override
	public void accept(Visitante v) {
		v.visita(this);		
	}

}

