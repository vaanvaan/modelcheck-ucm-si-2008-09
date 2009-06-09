/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import ucm.si.TeoriaActividad.item.EstadoItem;

/**
 *
 * @author nico
 */
public class ItemDrawer extends Component implements ListCellRenderer{
    private String texto;
    private TreeMap<String, Color> mapeadoColores;
    private EstadoItem ei;
    private boolean activado;
    private float tam;

    ItemDrawer(TreeMap<String, Color> mapeadoColores,boolean activado,float tam) {
        this.mapeadoColores = mapeadoColores;
        this.activado = activado;
        this.tam = tam;
    }

    public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
        Object[] e = (Object[]) arg1;
        this.texto = (String)e[0];
        ei = (EstadoItem)e[1];
        return this;
    }

    @Override
    public void paint(Graphics arg0) {
        super.paint(arg0);
        arg0.clearRect(0, 0, this.getWidth(), this.getHeight());
        Rectangle2D rect = arg0.getFont().getStringBounds(texto, arg0.getFontMetrics().getFontRenderContext());
        Color c = Color.GREEN;//mapeadoColores.get(texto);
        Color cxor = Color.RED;
        if (!this.activado){
            c = escalaGrises(c);
            cxor = escalaGrises(cxor);
        }
        double lado = (rect.getHeight()*(double)tam) - (double)2;
        if (ei.equals(EstadoItem.FREE)){
            arg0.setColor(c);
            arg0.fillOval(0, 0, (int)(lado-1), (int)(lado-1));
            arg0.setColor(Color.black);
            arg0.drawOval(0, 0, (int)(lado-1), (int)(lado-1));
        } else if (ei.equals(EstadoItem.BUSY)){
            /*arg0.setColor(cxor);
            arg0.fillOval(0, 0, (int)(lado-1), (int)(lado-1));
            arg0.setColor(Color.black);
            arg0.drawOval(0, 0, (int)(lado-1), (int)(lado-1));
            arg0.setColor(c);
            arg0.fillOval((int)(0.2*lado), (int)(0.2*lado), (int)(0.6*lado)-1, (int)(0.6*lado)-1);
            arg0.setColor(Color.black);
            arg0.drawOval((int)(0.2*lado), (int)(0.2*lado), (int)(0.6*lado)-1, (int)(0.6*lado)-1);*/
            arg0.setColor(cxor);
            arg0.fillOval(0, 0, (int)(lado-1), (int)(lado-1));
            arg0.setColor(Color.black);
            arg0.drawOval(0, 0, (int)(lado-1), (int)(lado-1));
        } else {
            /*arg0.setColor(c);
            arg0.fillOval(0, 0, (int)(lado-1), (int)(lado-1));
            arg0.setColor(Color.black);
            arg0.drawOval(0, 0, (int)(lado-1), (int)(lado-1));*/
            arg0.setColor(cxor);
            int ldp5 = (int)(lado/5.0);
            int ldp2 = (int)(lado/2.0);
            int[] px = new int[]{0,ldp5,ldp2,(int)lado-ldp5,(int)lado,ldp2+ldp5,(int)lado,(int)lado-ldp5,ldp2,ldp5,0,ldp2-ldp5};
            int[] py = new int[]{ldp5+1,0+1,ldp2-ldp5+1,0+1,ldp5+1,ldp2+1,(int)lado-ldp5+1,(int)lado+1,ldp2+ldp5+1,(int)lado+1,(int)lado-ldp5+1,ldp2+1};
            arg0.fillPolygon(px, py, px.length);
            arg0.setColor(Color.black);
            arg0.drawPolygon(px, py, px.length);
        }
        arg0.setColor(Color.BLACK);
        arg0.setFont(arg0.getFont().deriveFont(tam*arg0.getFont().getSize()));
        arg0.drawString(texto, (int)lado+1, (int)lado-1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)(tam*(14 + 8*texto.length())), (int)(tam*14));
    }

    private Color escalaGrises(Color c) {
        int media = (c.getRed() + c.getGreen() + c.getBlue())/3;
        return new Color(media,media,media);
    }





}