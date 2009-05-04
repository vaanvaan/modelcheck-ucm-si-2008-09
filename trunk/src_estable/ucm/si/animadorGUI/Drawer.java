/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import javax.swing.JPanel;

/**
 *
 * @author Pilar
 */
public abstract class Drawer<S>
{
    protected S estado;
    protected Contexto contexto;

    public Contexto getContexto() {
        return contexto;
    }

    public void setContexto(Contexto contexto) {
        this.contexto = contexto;
    }

    public S getEstado() {
        return estado;
    }

    public void setEstado(S estado) {
        this.estado = estado;
    }
    
    public abstract void pintaEstado(S s, PanelInterface<S> pane);
    public abstract void rePinta(S s, PanelInterface<S> pane);

}