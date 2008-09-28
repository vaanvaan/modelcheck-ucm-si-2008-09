package ucm.si.Checker;

import java.util.List;


/**
 *  @author nico
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.04933AD7-03D5-10E9-DB99-5AB43CA44787]
// </editor-fold> 
public class Resultado {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.19A0C54A-5417-6654-88F9-BD1D26871F5C]
    // </editor-fold> 
    public static final int cierto = 0;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0493DD25-45B0-3444-F17A-F3F9B10A082D]
    // </editor-fold> 
    public static final int falso = 1;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C71DB725-CBD4-0D46-FD98-E7ABE22A07F2]
    // </editor-fold> 
    public static final int indeterminado = -1;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1203B296-E7C4-00BB-828B-EEEB38B50A65]
    // </editor-fold> 
    private int solucion;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1A8EBBF6-85AB-1659-453B-146B69450E7D]
    // </editor-fold> 
    private List<Estado> contraejemplo;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.790D13C2-FBAB-6557-5DD8-112DC177EA1C]
    // </editor-fold> 
    public int getSolucion () {
        return 0;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0DF466D2-73FF-53F9-0248-7028D2277309]
    // </editor-fold> 
    public List<Estado> getContraejemplo () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9244876B-7A5C-52A9-F7D8-32DB685C24B9]
    // </editor-fold> 
    public void setContraejemplo (List<Estado> contraejemplo) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3B740220-56D3-173F-C6A7-0A6D34DCB6A4]
    // </editor-fold> 
    public void setSolucion (int solucion) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A0BCDA9B-E166-AFE8-2706-24416EF66A02]
    // </editor-fold> 
    public void aniadirEstado (Estado e) {
    }

}

