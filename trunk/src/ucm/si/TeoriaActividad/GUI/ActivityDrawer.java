/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;

/**
 *
 * @author nico
 */
public class ActivityDrawer extends JPanel implements ListCellRenderer {

    private String texto;
    private TreeMap<String, Color> mapeadoColores;
    private JList jlitemsObjetos;

    ActivityDrawer(TreeMap<String, Color> mapeadoColores) {
        this.mapeadoColores = mapeadoColores;
    }

    public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
        this.removeAll();
        Object[] e = (Object[]) arg1;
        this.texto = (String) e[0];
        EstadoTA ei = (EstadoTA) e[1];
        boolean activa = ei.actividades.getEstado(this.texto).equals(EstadoActividad.Executing);
        Set<String> itemsObjetos = ei.propietarias.get(this.texto);
        DefaultListModel dlmItems = new DefaultListModel();
        if (itemsObjetos != null) {
            for (String s : itemsObjetos) {
                if (activa) {
                    dlmItems.addElement(new Object[]{s, EstadoItem.FREE});
                } else {
                    dlmItems.addElement(new Object[]{s, ei.items.getEstado(s)});
                }
            }
        }
        this.jlitemsObjetos = new JList(dlmItems);
        this.jlitemsObjetos.setCellRenderer(new ItemDrawer(mapeadoColores));
        this.add(this.jlitemsObjetos);
        JLabel jlabel = new JLabel(this.texto);
        if (!activa) {
            jlabel.setEnabled(false);
        }
        this.add(jlabel);
        return this;
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        arg0.drawLine(0, 0, this.getWidth() - 1, 0);
    }
}