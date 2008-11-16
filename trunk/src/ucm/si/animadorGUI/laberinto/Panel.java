/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.laberinto;

import javax.swing.JPanel;

/**
 *
 * @author Pilar
 */
public abstract class Panel<S> extends JPanel
{
    public abstract void pintaEstado(S s);
    public abstract void rePinta(S s);

}
