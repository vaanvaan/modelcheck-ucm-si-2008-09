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
 *
 * Interfaz que define las objetos y funciones minimos que debe poseer un Drawer.
 * Su mision es representar un estado del tipo <S> en un JPanel.
 * Es Objeto que se almacena en una clase de tipo PanelInterface,
 * con ello el drawer tiene a su disposicion un objeto del tipo JPanel para que
 * se use en la representacion de un estado.
 */
public abstract class Drawer<S>
{
    protected S estado;
    /**
     * Es la el Objeto qeu guarda informacion que puedee ser de utilidad par aun drawer
     */
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
     * Con esta funcion se intenta que el Drawer pinte un estado para un objeto en concreto sobre un objeto del tipo PanelInterface
     * @param s Estado a Pintar
     * @param pane PanelInterface que contien el JPanel sobre el cual el Drawer pintará su representacion.
     */
    public abstract void pintaEstado(S s, PanelInterface<S> pane);
    /**
     * Repite la accion ya indicada en pintaEstado.
     * @param s Estado a Pintar
     * @param pane PanelInterface que contien el JPanel sobre el cual el Drawer pintará su representacion.
     */
    public abstract void rePinta(S s, PanelInterface<S> pane);

}
