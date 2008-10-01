/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Laberinto;



/**
 *
 * @author Pilar
 */
public class Posicion 
{
    protected int posX;
    protected int posY;

    public Posicion(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
