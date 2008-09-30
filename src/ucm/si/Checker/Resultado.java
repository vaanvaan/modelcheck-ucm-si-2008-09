package ucm.si.Checker;

import java.util.List;

/**
 *  @author nico
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.04933AD7-03D5-10E9-DB99-5AB43CA44787]
// </editor-fold> 
public class Resultado<S> {

	public static final String COD_TRUE= "true";
	public static final String COD_FALSE= "false";
	public static final String COD_MAYBET= "maybet";
	public static final String COD_MAYBEF= "maybef";
	public static final String COD_ERROR= "error";

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1203B296-E7C4-00BB-828B-EEEB38B50A65]
    // </editor-fold> 
    private String resultado=null;

    public Resultado(String tipo){
		this.resultado= tipo;
	}
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1A8EBBF6-85AB-1659-453B-146B69450E7D]
    // </editor-fold> 
    private List<S> contraejemplo;

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0DF466D2-73FF-53F9-0248-7028D2277309]
    // </editor-fold> 
    public List<S> getContraejemplo () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9244876B-7A5C-52A9-F7D8-32DB685C24B9]
    // </editor-fold> 
    public void setContraejemplo (List<S> contraejemplo) {
    }


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A0BCDA9B-E166-AFE8-2706-24416EF66A02]
    // </editor-fold> 
    public void aniadirEstado (S e) {
    }
    
    public boolean equals(String s)
    {
    	return this.resultado.equals(s);    	
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resultado other = (Resultado) obj;
        if (this.resultado != other.resultado && (this.resultado == null || !this.resultado.equals(other.resultado))) {
            return false;
        }
        return true;
    }
    

}

