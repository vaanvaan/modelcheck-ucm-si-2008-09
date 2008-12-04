/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.laberinto;

import javax.swing.JPanel;
import ucm.si.animadorGUI.Drawer;
import ucm.si.util.Contexto;

/**
 *
 * @author Admin
 */
public abstract class Panel<S> extends JPanel
{
    protected Contexto contexto;
    protected Drawer<S> drawer;
    public abstract void pintaEstado(S s);
    public abstract void rePinta(S s);
    public abstract void setDrawer(Drawer<S> dw);
    public abstract void setContexto(Contexto cntxt);

}
