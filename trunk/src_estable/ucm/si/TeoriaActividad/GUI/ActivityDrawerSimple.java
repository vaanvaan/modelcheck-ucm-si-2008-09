/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import ucm.si.TeoriaActividad.Interprete.SistemaActividades;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class ActivityDrawerSimple extends JPanel implements TreeCellRenderer {

    private String texto;
    private TreeMap<String, Color> mapeadoColores;
    //private JList jlitemsObjetos;
    private SistemaActividades p;
    //private JList jlitemsGenerados;

    ActivityDrawerSimple(TreeMap<String, Color> mapeadoColores, SistemaActividades p) {
        this.mapeadoColores = mapeadoColores;
        this.p = p;
    }

    public Component getTreeCellRendererComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5, boolean arg6) {
        this.removeAll();
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)arg1;
        Object[] e = (Object[])dmtn.getUserObject();
        this.texto = (String) e[0];
        EstadoTA ei = (EstadoTA) e[1];
        boolean activa = ei.getEstadoActividad(this.texto).equals(EstadoActividad.Executing);
        //boolean finalizada = ei.getEstadoActividad(this.texto).equals(EstadoActividad.Finalized);
        
        JLabel jlabel = new JLabel(this.texto);
        if (ei.getEstadoActividad(this.texto).equals(EstadoActividad.Finalized)) {
            jlabel.setEnabled(false);
        } else if (!activa) {
            ImageIcon espera = new ImageIcon("images/Waiting16.png");
            jlabel.setIcon(espera);
            jlabel.setForeground(Color.RED);
        } else {
            ImageIcon espera = new ImageIcon("images/Executing16.png");
            jlabel.setIcon(espera);
        }
        this.add(jlabel);
        return this;
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        arg0.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight()-1,6,6);
    }
}