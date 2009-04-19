/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.GUI;

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

    ItemDrawer(TreeMap<String, Color> mapeadoColores,boolean activado) {
        this.mapeadoColores = mapeadoColores;
        this.activado = activado;
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
        Color c = mapeadoColores.get(texto);
        Color cxor = Color.RED;
        if (!this.activado){
            c = escalaGrises(c);
            cxor = escalaGrises(cxor);
        }
        int lado = 2*(int)rect.getHeight() - 4;
        if (ei.equals(EstadoItem.FREE)){
            arg0.setColor(c);
            arg0.fillOval(0, 1, lado, lado);
            arg0.setColor(Color.black);
            arg0.drawOval(0, 1, lado, lado);
        } else if (ei.equals(EstadoItem.BUSY)){
            arg0.setColor(cxor);
            arg0.fillOval(0, 1, lado, lado);
            arg0.setColor(Color.black);
            arg0.drawOval(0, 1, lado, lado);
            arg0.setColor(c);
            arg0.fillOval(4, 5, lado - 8, lado-8);
            arg0.setColor(Color.black);
            arg0.drawOval(4, 5, lado - 8, lado-8);
        } else {
            arg0.setColor(c);
            arg0.fillOval(0, 1, lado, lado);
            arg0.setColor(Color.black);
            arg0.drawOval(0, 1, lado, lado);
            arg0.setColor(cxor);
            int ldp5 = lado/5;
            int ldp2 = lado/2;
            int[] px = new int[]{0,ldp5,ldp2,lado-ldp5,lado,ldp2+ldp5,lado,lado-ldp5,ldp2,ldp5,0,ldp2-ldp5};
            int[] py = new int[]{ldp5+1,0+1,ldp2-ldp5+1,0+1,ldp5+1,ldp2+1,lado-ldp5+1,lado+1,ldp2+ldp5+1,lado+1,lado-ldp5+1,ldp2+1};
            arg0.fillPolygon(px, py, px.length);
            arg0.setColor(Color.black);
            arg0.drawPolygon(px, py, px.length);
        }
        arg0.setColor(Color.BLACK);
        arg0.setFont(arg0.getFont().deriveFont(2.0F*arg0.getFont().getSize()));
        arg0.drawString(texto, lado+1, lado-1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2*(14 + 8*texto.length()), 2*14);
    }

    private Color escalaGrises(Color c) {
        int media = (c.getRed() + c.getGreen() + c.getBlue())/3;
        return new Color(media,media,media);
    }





}