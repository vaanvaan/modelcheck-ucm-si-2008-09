/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import ucm.si.TeoriaActividad2.Interprete.InterpreteTA;
import ucm.si.TeoriaActividad2.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad2.estado.EstadoTA;
import ucm.si.TeoriaActividad2.estado.IEstadoDrawable;
import ucm.si.TeoriaActividad2.item.ItemGenerator;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.PanelInterface;

/**
 *
 * @author nico
 */
public class DrawerActividad extends Drawer<IEstadoDrawable>{
    private JList jlItems;
    private DefaultListModel dlmItems;
    private JList jlActividades;
    private DefaultListModel dlmActividades;
    private InterpreteTA p;

    public DrawerActividad(InterpreteTA p){
        this.p = p;
    }

    @Override
    public void pintaEstado(IEstadoDrawable s, PanelInterface<IEstadoDrawable> pane) {
        dlmItems = new DefaultListModel();
        String[] items = ItemGenerator.getReference().getConjunto().keySet().toArray(new String[0]);
        java.util.Arrays.sort(items);
        TreeMap<String,Color> mapeadoColores = new TreeMap<String,Color>();
        int base = (int)Math.ceil(Math.pow((double)(items.length+1), (1/(double)3)));
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e,s.getEstadoItem(e)});
            int ni = (i*(base*base*base-1))/(items.length);
            int r = ni/(base*base);
            int g = (ni - r*base*base)/base;
            int b = ni%base;
            if (r==base-1){
                ni = ((i+1)*(base*base*base-1))/(items.length);
                g = (ni - r*base*base)/base;
                b = (ni)%base;
            }
            Color c = new Color(r*255/(base-1),g*255/(base-1),b*255/(base-1));
            mapeadoColores.put(e, c);
        }
        jlItems = new JList(dlmItems){

            @Override
            protected void paintComponent(Graphics arg0) {
                arg0.translate(0, 28);
                super.paintComponent(arg0);
                arg0.translate(0, -28);
                arg0.clearRect(0, 0, this.getWidth(), 28);
                arg0.setColor(Color.black);
                arg0.setFont(arg0.getFont().deriveFont(2.0F*arg0.getFont().getSize()));
                arg0.drawString("Items", 1, 26);
                arg0.drawLine(0, 27, this.getWidth(), 27);
                arg0.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1,30,30);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = new Dimension(super.getPreferredSize());
                d.height = d.height + 28;
                d.width = Math.max(16*5, d.width);
                return d;
            }
        };
        jlItems.setCellRenderer(new ItemDrawer(mapeadoColores,true));
        pane.add(jlItems);
        dlmActividades = new DefaultListModel();
        String[] actividades = ActividadGenerator.getReference().getConjunto().keySet()
                .toArray(new String[0]);
        java.util.Arrays.sort(actividades);
        base = (int)Math.ceil(Math.pow((double)(actividades.length+1), (1/(double)3)));
        for (int i = 0; i < actividades.length; i++) {
            String a = actividades[i];
            dlmActividades.addElement(new Object[]{a,s});
            int ni = (i*(base*base*base-2))/(actividades.length);
            int r = ni/(base*base);
            int g = (ni - r*base*base)/base;
            int b = ni%base;
            if (r==base-1){
                ni = ((i+1)*(base*base*base-1))/(actividades.length);
                g = (ni - r*base*base)/base;
                b = (ni)%base;
            }
            Color c = new Color(r*255/(base-1),g*255/(base-1),b*255/(base-1));
            mapeadoColores.put(a, c);
        }
        jlActividades = new JList(dlmActividades){

            @Override
            protected void paintComponent(Graphics arg0) {
                arg0.translate(0, 28);
                super.paintComponent(arg0);
                arg0.translate(0, -28);
                arg0.clearRect(0, 0, this.getWidth(), 28);
                arg0.setColor(Color.black);
                arg0.setFont(arg0.getFont().deriveFont(2.0F*arg0.getFont().getSize()));
                arg0.drawString("Actividades", 1, 27);
                arg0.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1,28,28);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = new Dimension(super.getPreferredSize());
                d.height = d.height + 28;
                d.width = Math.max(16*11, d.width);
                return d;
            }
        };
        jlActividades.setCellRenderer(new ActivityDrawer(mapeadoColores,p));
        pane.add(jlActividades);
    }

    @Override
    public void rePinta(IEstadoDrawable s, PanelInterface<IEstadoDrawable> pane) {
        dlmItems.removeAllElements();
        String[] items = ItemGenerator.getReference().getConjunto().keySet().toArray(new String[0]);
        java.util.Arrays.sort(items);
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e,s.getEstadoItem(e)});
        }
        dlmActividades.removeAllElements();
        String[] actividades = ActividadGenerator.getReference().getConjunto().keySet()
                .toArray(new String[0]);
        java.util.Arrays.sort(actividades);
        for (int i = 0; i < actividades.length; i++) {
            String a = actividades[i];
            dlmActividades.addElement(new Object[]{a,s});
        }
    }


}