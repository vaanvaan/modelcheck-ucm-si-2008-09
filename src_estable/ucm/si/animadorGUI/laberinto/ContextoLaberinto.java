/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.laberinto;

import ucm.si.util.Contexto;
import ucm.si.Laberinto.Laberinto;

/**
 *
 * @author Pilar
 */
public class ContextoLaberinto extends Contexto 
{
    private Laberinto lab;

    public Laberinto getLab() {
        return lab;
    }

    public void setLab(Laberinto lab) {
        this.lab = lab;
    }

}
