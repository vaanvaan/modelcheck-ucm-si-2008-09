/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.adhoc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.print.attribute.HashAttributeSet;
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.util.StateLabeledList;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;

/**
 *
 * @author nico
 */
public class AdHoc implements Interprete<Integer>{
    private ArrayList<Integer> iniciales;
    private Hashtable<Integer,ArrayList<Integer>> transita;
        
      /**
       *  @param ini Estados iniciales separados por espacios
       *  @param transiciones Lista de sucesores de estados de la forma
       *              Eini Ef1 Ef2 separadas por ;
       */    
   public AdHoc(String ini, String transiciones){       
        iniciales = new ArrayList<Integer>();
        String iniaux = new String(ini);
        rellenarDesdeCadena(iniciales,iniaux);
        transita = new Hashtable<Integer,ArrayList<Integer>>();
        String transaux = new String(transiciones);
        String[] splitaux = transaux.split(";");
        for (int i = 0; i < splitaux.length; i++) {
           String s = splitaux[i];
           String[] splitaux2 = s.trim().split(" ");
           Integer est = new Integer(Integer.parseInt(splitaux2[0]));
           ArrayList<Integer> trans = new ArrayList<Integer>();
           for (int j = 1; j < splitaux2.length; j++) {
             String s2 = splitaux2[j];
             trans.add(new Integer(Integer.parseInt(s2)));
           }
           transita.put(est, trans);
       }        
        
    }
    
    public List<Integer> iniciales() {
        return iniciales;
    }

    public List<Integer> transitar(Integer state) {
        List<Integer> l = transita.get(state);
        if (l==null) l = new ArrayList<Integer>();
        return l;            
    }
   
    private void rellenarDesdeCadena(ArrayList<Integer> iniciales, String iniaux) {
        String[] aux = iniaux.split(" ");
        for (int i = 0; i < aux.length; i++) {
            String s = aux[i];
            iniciales.add(new Integer(Integer.parseInt(s)));
        }
    }

    public StateLabeledList<Integer> transitarConEtiqueta(Integer state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> dameTransiciones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
