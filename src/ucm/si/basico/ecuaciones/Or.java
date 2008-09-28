package ucm.si.basico.ecuaciones;


import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.14B913A8-ED73-E3E0-9667-7203B3DB2505]
// </editor-fold> 
public class Or extends Operacion {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C3F27E39-5F97-31DE-1912-42F9325C9FBA]
    // </editor-fold> 
    private Formula mFormula;
    private Formula expIzq;
    private Formula expDer;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7F36B771-DE63-D884-A7B8-2A0D20D432B6]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C407560B-353E-5C7F-6E21-5FAAAF142CD3]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

	@Override
	public void accept(Visitante v) {
		expIzq.accept(v);
		Resultado resIzq = v.getResParcial();
		expDer.accept(v);
		Resultado resDer = v.getResParcial();
		Resultado resAND;
		try{
			boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
			boolean part2 = Boolean.parseBoolean(resDer.getResultado());
			part1 = part1 || part2;
			resAND = new Resultado(String.valueOf(part1));
		}catch(Exception e){
			if((resIzq.getResultado().equals(Resultado.COD_TRUE)) ||
					(resDer.getResultado().equals(Resultado.COD_TRUE)) ){
				resAND = new Resultado(Resultado.COD_TRUE);
			}else{
				resAND = new Resultado(Resultado.COD_MAYBE);
			}
		}
		v.setResParcial(resAND);
	}
	public Or(){
		expIzq = null;
		expDer = null;
	}
	public Or(Formula eIzq, Formula eDer){
		expIzq = eIzq;
		expDer = eDer;
	}
}

