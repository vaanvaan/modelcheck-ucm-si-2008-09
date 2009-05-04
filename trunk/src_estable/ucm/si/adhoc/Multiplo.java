/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.adhoc;

import ucm.si.basico.ecuaciones.Proposicion;

/**
 *
 * @author nico
 */
public class Multiplo extends Proposicion<Integer> {
    int nbase;
    
    public Multiplo(int nbase){
        this.nbase = nbase;
    }
    
    @Override
    public boolean esCierta(Integer s) {
        return s.intValue() % nbase == 0;
    }

}
