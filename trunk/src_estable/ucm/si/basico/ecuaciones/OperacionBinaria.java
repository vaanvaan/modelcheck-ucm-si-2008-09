/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.basico.ecuaciones;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
abstract class OperacionBinaria extends Operacion{
    
   public OperacionBinaria(Formula f0, Formula f1){
        this.setOperandos(new Formula[]{f0, f1});
   }

}
