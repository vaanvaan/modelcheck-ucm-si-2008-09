/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Laberinto;

import ucm.si.basico.ecuaciones.Proposicion;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class Final extends Proposicion<Posicion>{

    
    private int posX = 6;
    private int posY = 6;

    public Final(int posX, int posY) 
    {
        this.posX = posX;
        this.posY = posY;
    }
    
    
    
    @Override
    public boolean esCierta(Posicion s) {
        return (s.getPosX() == this.posX )&&(s.getPosY() == this.posY);
    }

}
