/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
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
 * @author nico
 */
public class ActivityDrawer extends JPanel{

    private String texto;
    private EstadoTA ei;
    private TreeMap<String, Color> mapeadoColores;
    private JList jlitemsObjetos;
    private SistemaActividades p;
    private JList jlitemsGenerados;

    ActivityDrawer(TreeMap<String, Color> mapeadoColores, SistemaActividades p) {
        this.mapeadoColores = mapeadoColores;
        this.p = p;
    }

    void setInfo(String texto, EstadoTA ei) {
        this.texto = texto;
        this.ei = ei;
        if (texto!=null){
        this.removeAll();
        this.setLayout(new FlowLayout());
        boolean activa = ei.getEstadoActividad(this.texto).equals(EstadoActividad.Executing);
        boolean finalizada = ei.getEstadoActividad(this.texto).equals(EstadoActividad.Finalized);
        if (!finalizada) {
            Item[] itemsNecesarios = p.activGen.getItem(this.texto).getItemNecesarios();
            String[] itemsObjetos = new String[itemsNecesarios.length];
            for (int i = 0; i < itemsObjetos.length; i++) {
                itemsObjetos[i] = itemsNecesarios[i].getClave();
            }
            DefaultListModel dlmItems = new DefaultListModel();
            if (itemsObjetos != null) {
                java.util.Arrays.sort(itemsObjetos);
                for (String s : itemsObjetos) {
                    if (activa) {
                        dlmItems.addElement(new Object[]{s, EstadoItem.FREE});
                    } else {
                        dlmItems.addElement(new Object[]{s, ei.getEstadoItem(s)});
                    }
                }
            }
            this.jlitemsObjetos = new JList(dlmItems);
            this.jlitemsObjetos.setCellRenderer(new ItemDrawer(mapeadoColores, activa,1F));
            if (!activa) {
                this.jlitemsObjetos.setEnabled(false);
            }
            this.add(this.jlitemsObjetos);
        }
        JLabel jlabel = new JLabel(this.texto);
        if (ei.getEstadoActividad(this.texto).equals(EstadoActividad.Finalized)) {
            jlabel.setEnabled(false);
        } else if (!activa) {
            ImageIcon espera = new ImageIcon("images/Waiting32.png");
            jlabel.setIcon(espera);
            jlabel.setForeground(Color.RED);
        } else {
            ImageIcon espera = new ImageIcon("images/Executing32.png");
            jlabel.setIcon(espera);
        }
        this.add(jlabel);
        if (activa) {
            Item[] itemsToGenerate = p.activGen.getItem(this.texto).getItemToGenerate();
            String[] itemsGenerados = new String[itemsToGenerate.length];
            for (int i = 0; i < itemsGenerados.length; i++) {
                itemsGenerados[i] = itemsToGenerate[i].getClave();
            }
            DefaultListModel dlmItemsGenerados = new DefaultListModel();
            if ((itemsGenerados != null)&&(itemsGenerados.length!=0)) {
                java.util.Arrays.sort(itemsGenerados);
                for (String s : itemsGenerados) {
                    if (activa) {
                        dlmItemsGenerados.addElement(new Object[]{s, EstadoItem.FREE});
                    } else {
                        dlmItemsGenerados.addElement(new Object[]{s, ei.getEstadoItem(s)});
                    }
                }
            }
            this.jlitemsGenerados = new JList(dlmItemsGenerados);
            if (!activa) {
                this.jlitemsGenerados.setEnabled(false);
            }
            this.jlitemsGenerados.setCellRenderer(new ItemDrawer(mapeadoColores, activa,1F));
            this.add(this.jlitemsGenerados);
        }
        }
    }
   
    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        //arg0.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight()-1,6,6);
    }
}