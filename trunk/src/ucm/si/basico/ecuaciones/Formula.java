package ucm.si.basico.ecuaciones;

import java.io.Serializable;
import ucm.si.Checker.VisitanteConector;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.930AEFE8-4DBB-FA60-B6DD-83E2A939254B]
// </editor-fold> 
public abstract class Formula implements Serializable {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5183DEAF-E5E6-A194-B3E9-81E667397DB7]
    // </editor-fold> 
    public abstract void accept (VisitanteConector v);

}

