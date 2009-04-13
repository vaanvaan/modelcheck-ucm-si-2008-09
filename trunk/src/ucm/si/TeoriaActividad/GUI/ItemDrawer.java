/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.JPanel;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;

/**
 *
 * @author nico
 */
public class ItemDrawer extends JPanel{
    public EstadoTA estado;
    private TreeMap<String, Color> dibujos;


    public ItemDrawer(EstadoTA estado) {
        this.estado = estado;
        this.dibujos = new TreeMap<String,Color>();
        Random r = new Random(System.currentTimeMillis());
        for (String e : estado.items.keySet()) {
            this.dibujos.put(e, new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
        }
        this.setPreferredSize(new Dimension(30, 120));
    }


    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        int w = this.getWidth();
        int h = this.getHeight();
        int i = 0;
        for (String e: dibujos.keySet()){
            if (estado.getEstadoItem(e).equals(EstadoItem.FREE)){
                arg0.setColor(dibujos.get(e));
            } else {
                arg0.setColor(dibujos.get(e).darker());
            }
            arg0.fillOval(0, i*h/dibujos.size(), w, h/dibujos.size());
            i++;
        }
    }



}
