package ucm.si.basico.ecuaciones;


import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7AE3FC8A-1924-11A1-E384-B388D3300E65]
// </editor-fold> 
public class Not extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3F1CBAC5-C434-3302-9721-0F710B643F92]
    // </editor-fold> 
    private Formula mFormula;

    public Not(Formula exp) {
    	mFormula = exp;
	}
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.506068F1-D9CD-05CC-F288-B51C99A9D5A7]
    // </editor-fold> 
    public void accept (Visitante v) {
    	//
    	mFormula.accept(v);
		Resultado resp = v.getResParcial();
		Resultado resul = new Resultado(Resultado.COD_ERROR);

		if (resp.getResultado().equals(Resultado.COD_FALSE)) {
			resul.setResultado(Resultado.COD_TRUE);
		} else if (resp.getResultado().equals(Resultado.COD_TRUE)) {
			resul.setResultado(Resultado.COD_FALSE);
		} else {
			resul = resp;
		}
		//
		v.setResParcial(resul);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BAC38878-1C1A-7263-A5B7-2595F7E1197D]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3F80ECB9-FDBE-13C8-81BF-FD384C417386]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

