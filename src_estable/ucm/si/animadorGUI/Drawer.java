/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import javax.swing.JPanel;

/**
 * Clase abstracta que funciona como interfaz. El drawer es la calse que hay que implementar para que el animador sepa representar el contenido de un estado. Esta clase recive un estado y un JPanel. Con todo ello rellena el JPanel para que tenga una representacion del estado.
 * @author Niko, Jose Antonio, Ivan
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

    /**
     * Pinta un estado en un JPanel
     * @param s estado a represetnar
     * @param pane JPanel donde se va a "dibujar"
     */
    public abstract void pintaEstado(S s, PanelInterface<S> pane);
    /**
     * Repinta un estado en un JPanel
     * @param s esto a pintar
     * @param pane JPAnel donde se va a dibujar
     */
    public abstract void rePinta(S s, PanelInterface<S> pane);

}
