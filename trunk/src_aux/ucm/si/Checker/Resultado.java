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
    private String resultado = null;

    public Resultado(String tipo){
		this.resultado= tipo;
	}
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1A8EBBF6-85AB-1659-453B-146B69450E7D]
    // </editor-fold> 
    private Arbol<S> contraejemplo;
    private Arbol<S> ejemplo;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    
    public Arbol<S> getContraejemplo() {
        return contraejemplo;
    }

    public void setContraejemplo(Arbol<S> contraejemplo) {
        this.contraejemplo = contraejemplo;
    }

    public Arbol<S> getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(Arbol<S> ejemplo) {
        this.ejemplo = ejemplo;
    }
    
    public boolean equals(String s) {
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

