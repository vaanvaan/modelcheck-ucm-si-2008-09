/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.basico.ecuaciones;

import ucm.si.Checker.Visitante;

/**
 *
 * @author nico
 */
abstract class OperacionUnaria extends Operacion{
      
   public OperacionUnaria(Formula f){
        this.setOperandos(new Formula[]{f});
   }

}
