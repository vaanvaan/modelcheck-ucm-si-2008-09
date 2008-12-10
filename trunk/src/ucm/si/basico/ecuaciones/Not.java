package ucm.si.basico.ecuaciones;


import ucm.si.Checker.Visitante;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7AE3FC8A-1924-11A1-E384-B388D3300E65]
// </editor-fold> 
public class Not extends OperacionUnaria {

    public Not(Formula f) {
    	super(f);
    }    
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.506068F1-D9CD-05CC-F288-B51C99A9D5A7]
    // </editor-fold> 
    public void accept (Visitante v) {
    	v.visita(this);
    }

}

