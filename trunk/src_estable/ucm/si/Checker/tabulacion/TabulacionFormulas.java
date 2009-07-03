package ucm.si.Checker.tabulacion;

import java.util.Hashtable;
import java.util.Set;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.Checker.Resultado;

/**
 *  @author Niko, Jose Antonio, Ivan
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.2275D25C-FD9F-315D-5999-A3E0B8FEC6F3]
// </editor-fold> 
public interface TabulacionFormulas<S> {


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D1C67E55-7DF1-F2F8-5633-A7370FA19565]
    // </editor-fold> 
    public void aniadirEtiqueta (S estado, Formula formula, Resultado r);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5AC82920-3604-8216-3C35-F25EBB803942]
    // </editor-fold> 
    public boolean tieneEtiqueta (S estado, Formula formula);
    
    public Resultado getResultado(S estado, Formula formula);
    
    public boolean tieneEstado(S estado);

}

