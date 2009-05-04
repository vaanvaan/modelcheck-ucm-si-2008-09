package ucm.si.Checker;

import ucm.si.Checker.util.Roseta;
import ucm.si.basico.ecuaciones.Formula; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.21B8A663-FA11-A9B0-67A5-53B60CD8727B]
// </editor-fold> 
public interface ModelChecker<S> {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7976A13B-E5F7-6A8F-1AD3-4E19D7E8B67B]
    // </editor-fold> 
    public Resultado chequear ( Formula formula);

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4CABA78F-38BB-549A-D70B-BD44F2FE74CB]
    // </editor-fold> 
    public Resultado chequear (Interprete<S> interprete, Formula formula, S estado);
    
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4CABA78F-38BB-549A-D70B-BD44F2FE74CB]
    // </editor-fold> 
    public Resultado chequear (Interprete<S> interprete, Formula formula);
    
    public Roseta<S> getRoseta();
    
}

