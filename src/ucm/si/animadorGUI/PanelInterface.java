/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import javax.swing.JPanel;

/**
 *Clase encargada de Contener un JPanel sobre el cual se aplicaran un Drawer para los estados.
 * Con esto se consigue que para un estado de tipo desconocido se le asigne un Drawer a medida
 * que ante un estado use los recursos de JPnael del que hereda para representar el estado
 * @author Admin
 */
public abstract class PanelInterface<S> extends JPanel
{
    protected Contexto contexto;
    protected Drawer<S> drawer;
    public abstract void pintaEstado(S s);
    public abstract void rePinta(S s);
    public abstract void setDrawer(Drawer<S> dw);
    public abstract void setContexto(Contexto cntxt);

}
