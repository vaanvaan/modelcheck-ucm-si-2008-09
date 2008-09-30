package ucm.si.basico.ecuaciones;


import ucm.si.Checker.ModelChecker; 
import ucm.si.Checker.Resultado;
import ucm.si.Checker.Visitante;

/**
 *  @author nico
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.0FFB3057-BDFA-07A4-5C6D-3B50EE5B4DAC]
// </editor-fold> 
public abstract class Proposicion<S> extends Formula {
	
	private String valor=Resultado.COD_FALSE;
	
	public Proposicion (String val){
		valor = val;
	}
	public Proposicion (){
		valor = Resultado.COD_MAYBEF;                 
	}

	public abstract boolean esCierta(S s);

        public String getValor(){
            return valor;
        }
        
	public void setValor(String valor) {
		this.valor = valor;
	}
	@Override
	public void accept(Visitante v) {
		v.setResParcial(new Resultado(valor));
		
	}

}

