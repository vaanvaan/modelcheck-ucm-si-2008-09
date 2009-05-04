package ucm.si.basico.ecuaciones;


import ucm.si.Checker.Visitante;

/**
 *  @author nico
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.0FFB3057-BDFA-07A4-5C6D-3B50EE5B4DAC]
// </editor-fold> 
public abstract class Proposicion<S> extends Formula {
	
	public abstract boolean esCierta(S s);
        
	@Override
	public void accept(Visitante v) {
               v.visita(this);		
	}

}

