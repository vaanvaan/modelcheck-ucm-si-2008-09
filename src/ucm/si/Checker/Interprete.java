package ucm.si.Checker;

import java.util.List;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.AB379A1E-67C3-9733-6F60-5FA18BC31E6E]
// </editor-fold> 
public interface Interprete {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E3EA2941-4A75-6F2C-15E4-98944D2EB3C1]
    // </editor-fold> 
    public List<Estado> iniciales ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2088AE4E-6D7D-BD80-A3D7-21C2583346FF]
    // </editor-fold> 
    public List<Estado> transitar (Estado state);

}

