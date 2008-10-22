package ucm.si.Checker.tabulacion;

import java.util.Hashtable;
import java.util.Set;
import ucm.si.basico.ecuaciones.Formula; 

/**
 *  @author nico
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.2275D25C-FD9F-315D-5999-A3E0B8FEC6F3]
// </editor-fold> 
public interface TabulacionFormulas<S> {

      // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.49D8F442-55CC-6A13-133F-40FEADBA309E]
    // </editor-fold> 
    public Set<Formula> getEtiquetas (S estado);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.74489B72-AB3C-5CC3-0A6C-585042BBA1D2]
    // </editor-fold> 
    public void setEtiquetas (S estado, Set<Formula> etiquetas);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D1C67E55-7DF1-F2F8-5633-A7370FA19565]
    // </editor-fold> 
    public void aniadirEtiqueta (S estado, Formula formula);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5AC82920-3604-8216-3C35-F25EBB803942]
    // </editor-fold> 
    public boolean tieneEtiqueta (S estado, Formula formula);
    
    public void aniadirEstado(S estado, Set<Formula> etiquetas);
    
    public boolean tieneEstado(S estado);

}

