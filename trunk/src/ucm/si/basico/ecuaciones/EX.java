
package ucm.si.basico.ecuaciones;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.709D66FA-53B6-D1F5-0872-724E95CC588D]
// </editor-fold> 
public abstract class EX extends Formula {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7E18BB64-D50D-B17A-A226-04E73134B494]
    // </editor-fold> 
    private Formula mFormula;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D64E8F72-C308-C6D4-E5F5-766193D1161A]
    // </editor-fold> 
    public EX () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6C358184-1779-A050-CF84-E99902306E54]
    // </editor-fold> 
    public Formula getFormula () {
        return mFormula;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B26226F1-29BD-B905-CB78-BD2C700FD5D5]
    // </editor-fold> 
    public void setFormula (Formula val) {
        this.mFormula = val;
    }

}

