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
    
    public abstract void pintaEstado(S s, Panel<S> panel);
    public abstract void rePinta(S s, Panel<S> panel);

}
