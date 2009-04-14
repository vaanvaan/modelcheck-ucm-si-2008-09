/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;

/**
 *
 * @author nico
 */
public class ActivityDrawer extends JPanel implements ListCellRenderer{
    private String texto;
    private TreeMap<String, Color> mapeadoColores;
    private JList jlitemsObjetos;
    
    ActivityDrawer(TreeMap<String, Color> mapeadoColores) {
        this.mapeadoColores = mapeadoColores;
    }

    public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
        this.removeAll();
        Object[] e = (Object[]) arg1;
        this.texto= (String)e[0];
        EstadoTA ei = (EstadoTA)e[1];
        Set<String> itemsObjetos = ei.propietarias.get(this.texto);
        DefaultListModel dlmItems = new DefaultListModel();
        for (String s : itemsObjetos) {
            dlmItems.addElement(new Object[]{s,EstadoItem.FREE});
        }
        this.jlitemsObjetos = new JList(dlmItems);
        this.jlitemsObjetos.setCellRenderer(new ItemDrawer(mapeadoColores));
        this.add(this.jlitemsObjetos);
        this.add(new JLabel(this.texto));
        return this;
    }

}