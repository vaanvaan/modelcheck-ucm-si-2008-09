/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.PanelInterface;

/**
 *
 * @author nico
 */
public class DrawerActividad extends Drawer<EstadoTA>{
    private JList jlItems;
    private DefaultListModel dlmItems;

    @Override
    public void pintaEstado(EstadoTA s, PanelInterface<EstadoTA> pane) {
        dlmItems = new DefaultListModel();
        String[] items = ItemGenerator.getReference().getItems();
        java.util.Arrays.sort(items);
        TreeMap<String,Color> mapeadoColores = new TreeMap<String,Color>();
        int base = (int)Math.ceil(Math.pow((double)(items.length+1), (1/(double)3)));
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e,s.getEstadoItem(e)});
            int ni = (i*(base*base*base-2))/(items.length);
            int r = ni/(base*base);
            int g = (ni - r*base*base)/base;
            int b = ni%base;
            if (r==base-1){
                ni = ((i+1)*(base*base*base-1))/(items.length);
                g = (ni - r*base*base)/base;
                b = (ni)%base;
            }
            Color c = new Color(r*255/(base-1),b*255/(base-1),g*255/(base-1));
            mapeadoColores.put(e, c);
        }
        jlItems = new JList(dlmItems);
        jlItems.setCellRenderer(new ItemDrawer2(mapeadoColores));
        pane.add(jlItems);
    }

    @Override
    public void rePinta(EstadoTA s, PanelInterface<EstadoTA> pane) {
        dlmItems.removeAllElements();
        String[] items = ItemGenerator.getReference().getItems();
        java.util.Arrays.sort(items);
        for (int i = 0; i < items.length; i++) {
            String e = items[i];
            dlmItems.addElement(new Object[]{e,s.getEstadoItem(e)});
        }
    }
    
    
}
