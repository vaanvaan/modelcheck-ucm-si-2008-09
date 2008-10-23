/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Laberinto;

import ucm.si.Checker.Estado;



/**
 *
 * @author Pilar
 */
public class Posicion extends Estado 
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

    // Apaño para que que se vea en Consola los estados
    @Override
    public String toString() {
        return "Estado ---> PosX : "+this.posX+"  PosY : "+this.posY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Posicion other = (Posicion) obj;
        if (this.posX != other.posX) {
            return false;
        }
        if (this.posY != other.posY) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.posX;
        hash = 31 * hash + this.posY;
        return hash;
    }
    
    
    
    
    
}
