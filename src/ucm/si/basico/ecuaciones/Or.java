
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.14B913A8-ED73-E3E0-9667-7203B3DB2505]
// </editor-fold> 
public abstract class Or extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AFC796FA-0A5D-FE19-788B-39E914C2C13A]
    // </editor-fold> 
    private Formula[] mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1067E601-D9A1-42A8-13FE-09382292B452]
    // </editor-fold> 
    public Or () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.94651E69-E014-64BC-0596-F7DD9ABAE77B]
    // </editor-fold> 
    public Formula[] getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.64569533-0322-F577-CE26-F7D2DC1D30D9]
    // </editor-fold> 
    public void setFormula (Formula[] val) {
        this.mFormula = val;
    }

}

