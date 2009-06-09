/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import ucm.si.TeoriaActividad.Interprete.SistemaActividades;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.PanelInterface;

/**
 *
 * @author nico
 */
public class DrawerActividad extends Drawer<EstadoTA> implements TreeSelectionListener {

    private JList jlItems;
    private DefaultListModel dlmItems;
    private SistemaActividades p;
    private LinkedList<DefaultMutableTreeNode> dmtNodes;
    private LinkedList<JTree> listaArboles;
    private ActivityDrawer jtActividadDetalle;
    private Object[] e;
    private JTree ultimoArbol;

    public DrawerActividad(SistemaActividades p) {
        this.p = p;
    }

    @Override
    public void pintaEstado(EstadoTA s, PanelInterface<EstadoTA> pane) {
        dlmItems = new DefaultListModel();
        String[] items = ItemGenerator.getReference().getItems();
        java.util.Arrays.sort(items);
        TreeMap<String, Color> mapeadoColores = new TreeMap<String, Color>();
        int base = (int) Math.ceil(Math.pow((double) (items.length + 1), (1 / (double) 3)));
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e, s.getEstadoItem(e)});
            int ni = (i * (base * base * base - 1)) / (items.length);
            int r = ni / (base * base);
            int g = (ni - r * base * base) / base;
            int b = ni % base;
            if (r == base - 1) {
                ni = ((i + 1) * (base * base * base - 1)) / (items.length);
                g = (ni - r * base * base) / base;
                b = (ni) % base;
            }
            Color c = new Color(r * 255 / (base - 1), g * 255 / (base - 1), b * 255 / (base - 1));
            mapeadoColores.put(e, c);
        }
        jlItems = new JList(dlmItems) {

            @Override
            protected void paintComponent(Graphics arg0) {
                arg0.translate(0, 28);
                super.paintComponent(arg0);
                arg0.translate(0, -28);
                arg0.clearRect(0, 0, this.getWidth(), 28);
                arg0.setColor(Color.black);
                arg0.setFont(arg0.getFont().deriveFont(2.0F * arg0.getFont().getSize()));
                arg0.drawString("Items", 16, 26);
                arg0.drawLine(0, 27, this.getWidth(), 27);
                arg0.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 30, 30);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = new Dimension(super.getPreferredSize());
                d.height = d.height + 28;
                d.width = Math.max(16 * 6, d.width);
                return d;
            }
        };
        jlItems.setCellRenderer(new ItemDrawer(mapeadoColores, true,1F));
        jlItems.setAlignmentY(Component.TOP_ALIGNMENT);
        JPanel envoltorio = new JPanel();
        //envoltorio.setLayout(new BoxLayout(envoltorio, BoxLayout.Y_AXIS));
        envoltorio.setLayout(new BoxLayout(envoltorio,BoxLayout.X_AXIS));
        envoltorio.add(jlItems);
        LinkedList<String> actividades = p.getActividadesOrdenadas();
        dmtNodes = new LinkedList<DefaultMutableTreeNode>();
        LinkedList<DefaultMutableTreeNode> listaNodosRaiz = new LinkedList<DefaultMutableTreeNode>();
        int j = 0;
        for (int i = 0; i < actividades.size(); i++) {
            String a = actividades.get(i);
            DefaultMutableTreeNode dmtnAct = new DefaultMutableTreeNode(new Object[]{a, s});
            Actividad padre = p.activGen.getItem(a).getPadre();
            if (padre != null) {
                while (!actividades.get(j).equals(padre.getNombre())) {
                    j++;
                }
                dmtNodes.get(j).add(dmtnAct);
            } else {
                listaNodosRaiz.add(dmtnAct);
            }
            dmtNodes.add(dmtnAct);
        }
        JPanel panelActividades2 = new JPanel();
        JPanel panelActividades = new JPanel() {

            @Override
            protected void paintComponent(Graphics arg0) {
                arg0.translate(0, 28);
                super.paintComponent(arg0);
                arg0.translate(0, -28);
                arg0.clearRect(0, 0, this.getWidth(), 28);
                arg0.setColor(Color.black);
                arg0.setFont(arg0.getFont().deriveFont(2.0F * arg0.getFont().getSize()));
                arg0.drawString("Actividades", 16, 26);
                arg0.drawRoundRect(0, 0, 16*12 + 1, 28 - 1, 28, 28);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = new Dimension(super.getPreferredSize());
                d.height = d.height + 28;
                d.width = 16 * 12 +2;
                return d;
            }
        };
        panelActividades.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelActividades2.setLayout(new BoxLayout(panelActividades2, BoxLayout.Y_AXIS));
        panelActividades2.add(panelActividades);
        listaArboles = new LinkedList<JTree>();
        for (int i = 0; i < listaNodosRaiz.size(); i++) {
            DefaultMutableTreeNode a = listaNodosRaiz.get(i);
            JTree jtActividades = new JTree(a);
            jtActividades.setCellRenderer(new ActivityDrawerSimple(mapeadoColores, p));
            jtActividades.setAlignmentX(Component.LEFT_ALIGNMENT);
            jtActividades.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            jtActividades.addTreeSelectionListener(this);
            listaArboles.add(jtActividades);
            panelActividades2.add(jtActividades);
        }
        panelActividades2.setAlignmentY(Component.TOP_ALIGNMENT);
        envoltorio.add(panelActividades2);
        jtActividadDetalle = new ActivityDrawer(mapeadoColores, p);
        //jtActividadDetalle.setAlignmentY(Component.CENTER_ALIGNMENT);
        //jtActividadDetalle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel envoltorio2 = new JPanel(){

            @Override
            protected void paintComponent(Graphics arg0) {
                super.paintComponent(arg0);
                arg0.clearRect(0, 0, this.getWidth(), 28);
                arg0.setColor(Color.black);
                arg0.setFont(arg0.getFont().deriveFont(2.0F * arg0.getFont().getSize()));
                arg0.drawString("Detalle Actividad", 16, 26);
                arg0.drawLine(0, 27, this.getWidth(), 27);
                arg0.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 30, 30);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = new Dimension(super.getPreferredSize());
                d.height = d.height + 28;
                d.width = Math.max(16 * 17, d.width);
                return d;
            }
        };
        envoltorio2.setLayout(new BorderLayout());
        envoltorio2.add(Box.createRigidArea(new Dimension(16*17,28)),
                BorderLayout.NORTH);
        envoltorio2.add(Box.createRigidArea(new Dimension(16*17,15)),
                BorderLayout.SOUTH);
        envoltorio2.add(Box.createRigidArea(new Dimension(1,1)),
                BorderLayout.EAST);
        envoltorio2.add(Box.createRigidArea(new Dimension(1,1)),
                BorderLayout.WEST);
        envoltorio2.add(jtActividadDetalle,BorderLayout.CENTER);
        envoltorio2.setAlignmentY(Component.TOP_ALIGNMENT);
        envoltorio.add(envoltorio2);
        pane.setLayout(new BorderLayout());
        envoltorio.setAlignmentY(Component.LEFT_ALIGNMENT);
        pane.add(envoltorio,BorderLayout.WEST);        
    }

    @Override
    public void rePinta(EstadoTA s, PanelInterface<EstadoTA> pane) {
        dlmItems.removeAllElements();
        String[] items = ItemGenerator.getReference().getItems();
        java.util.Arrays.sort(items);
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e, s.getEstadoItem(e)});
        }
        for (int i = 0; i < dmtNodes.size(); i++) {
            ((Object[]) dmtNodes.get(i).getUserObject())[1] = s;
        }
        for (JTree jTree : listaArboles) {
            jTree.updateUI();
        }
        if (ultimoArbol != null) {
            ultimoArbol.clearSelection();
        }
        if (e != null) {
            this.jtActividadDetalle.setInfo((String) e[0], (EstadoTA) e[1]);
            this.jtActividadDetalle.updateUI();
        }
    }

    public void valueChanged(TreeSelectionEvent evnt) {
        if (ultimoArbol != null) {
            ultimoArbol.clearSelection();
        }
        ultimoArbol = ((JTree) evnt.getSource());
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) ultimoArbol.getLastSelectedPathComponent();

        if (dmtn != null) {
            e = (Object[]) dmtn.getUserObject();
            String texto = (String) e[0];
            EstadoTA ei = (EstadoTA) e[1];
            this.jtActividadDetalle.setInfo(texto, ei);
            this.jtActividadDetalle.updateUI();
        }
    }
}