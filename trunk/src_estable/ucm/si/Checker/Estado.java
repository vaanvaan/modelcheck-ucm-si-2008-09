package ucm.si.Checker;

import java.io.Serializable;
import java.util.ArrayList;
import ucm.si.basico.ecuaciones.Proposicion; 

/**
 * Clase que fucniona como interfaz si se desea para definir un estado
 * @author Niko, Jose Antonio, Ivan Antonio
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.F6D8E1F3-7B0D-2D99-1F1B-2A15776B6CB5]
// </editor-fold> 
public abstract class Estado implements Serializable{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1EDE41B8-3E51-98EC-7E1B-45C4D632879B]
    // </editor-fold> 
    //private ArrayList<Proposicion> listaProposiciones = null;

    /**
     * Constructor por defecto
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E0CEE85F-3143-90CA-15C4-148F8E1E61DA]
    // </editor-fold> 
    public Estado () {
    }

    /**
     * Forzamos que se implemente equals porque es necesario dentro del modelchekcer
     * @param obj
     * @return
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Forzamos que se tenga que implemntar la funcion hascode, ya que es necesaria
     * en las coleciiones que se usan internamente dentro del modelchecker
     * @return
     */
    @Override
    public abstract int hashCode();



    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C598D086-DF52-2A21-CDCB-8BBF928F788C]
    // </editor-fold> 
    /*public ArrayList<Proposicion> getListaProposiciones () {
        return null;
    }*/

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A3D8ADF6-9F40-A1EB-8B2F-4CDF85FA5195]
    // </editor-fold> 
    /*public void setListaProposiciones (ArrayList<Proposicion> val) {
    }*/
    
    /*@Override
    public abstract String toString();*/
    
    

}

 	