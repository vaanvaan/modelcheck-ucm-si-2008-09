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
public class Primo extends Proposicion<Integer>{
    private static boolean[] criba;
    private static int calculado;
    
    public Primo(){
        calculado = 1;
    }
    
    @Override
    public boolean esCierta(Integer s) {
        int este = s.intValue();
        if (este<2)
            return false;
        else if (este>calculado)
            calcular(este);
        return criba[este-2];
    }

    private void calcular(int este) {
        criba = new boolean[este-1];
        for (int i = 0; i < este-1; i++) {
            criba[i] = true;
        }
        int i = 2;
        while (i <= (int)Math.sqrt(este)){
            while (i<criba.length && (criba[i-2]==false))
                i++;
            for (int j = 2; j*i-2 < criba.length; j++){
                criba[j*i-2] = false;
            }            
            i++;
        }
        calculado = este;
    }

    public static void main(String args[]){
        Primo p = new Primo();
        System.out.println(p.esCierta(500));        
        System.out.println(p.esCierta(13));        
        System.out.println(p.esCierta(15));        
        System.out.println(p.esCierta(17));                
        System.out.println(p.esCierta(323));
    }
    
}
