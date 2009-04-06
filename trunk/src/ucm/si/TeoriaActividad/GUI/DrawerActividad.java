/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.GUI;

import javax.swing.JLabel;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.PanelInterface;

/**
 *
 * @author nico
 */
public class DrawerActividad extends Drawer<EstadoTA>{
    private JLabel e = new JLabel();
    @Override
    public void pintaEstado(EstadoTA s, PanelInterface<EstadoTA> pane) {
        e.setText(s.toString());
        pane.add(e);
    }

    @Override
    public void rePinta(EstadoTA s, PanelInterface<EstadoTA> pane) {
        e.setText(s.toString());
    }

}
