/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.laberinto;

import java.awt.Dimension;
import ucm.si.animadorGUI.PanelInterface;
import ucm.si.animadorGUI.Drawer;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;

/**
 *
 * @author Admin
 */
public class DrawerLaberinto extends Drawer<Posicion> {

    private Laberinto lab;
    private ArrayList<JButton> botones;
    
    @Override
    public void pintaEstado(Posicion s, PanelInterface<Posicion> panel) {
                ContextoLaberinto context =  (ContextoLaberinto) super.contexto;
                this.lab = context.getLab();
                this.estado = s;
                ImageIcon aguaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/caballero.jpg");
		botones = new ArrayList<JButton>((lab.getDim()^2)); //al menos va a tener dim x dim
		
		GridLayout cuadrados = new GridLayout(lab.getDim(), lab.getDim());
		// JButton boton = new JButton();
		//cuadrados.setColumns(lab.getDim());
		//cuadrados.setHgap(lab.getDim());
		// Image imagenFuente =
		// Toolkit.getDefaultToolkit().getImage("agua.jpg");
		
		panel.setLayout(cuadrados);

		for (int j = 0; j < lab.getDim(); j++) {
			for (int i = 0; i < lab.getDim(); i++) {
				if (this.estado.getPosX() == i && this.estado.getPosY() == j) {
					JButton jb = new JButton();
					jb.setName("b" + i + "," + j);
					jb.setIcon(caballeroIcon);
					botones.add(jb);
					panel.add(jb);
				} else {
					if (lab.checkPos(new Posicion(i, j))) {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(hierbaIcon);
						botones.add(jb);
						panel.add(jb);
					} else {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(aguaIcon);
						botones.add(jb);
						panel.add(jb);
					}
				}
			}
		}
	

    }

    @Override
    public void rePinta(Posicion p, PanelInterface<Posicion> panel) {
                
                panel.repaint();
                ContextoLaberinto context =  (ContextoLaberinto) super.contexto;
                this.lab = context.getLab();
                ImageIcon aguaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/caballero.jpg");
		if (botones!=null){
			// generamos el nombre del boton
			String nomBoton = "b"+p.getPosX()+","+p.getPosY();
			Iterator<JButton> it = botones.iterator();
			while(it.hasNext()){
				JButton boton = it.next();
				if(boton.getIcon().toString().equals(caballeroIcon.toString())){
					String str = boton.getName();
					StringTokenizer tokenizer = new StringTokenizer(str,",");
					String str2 = tokenizer.nextToken();
					String str3 = tokenizer.nextToken();
					str2 = str2.substring(1, str2.length());
					Posicion pos = new Posicion(Integer.parseInt(str2),Integer.parseInt(str3));
					lab.checkPos(pos);
					if (lab.checkPos(pos)) {
						boton.setIcon(hierbaIcon);
					} else {
						boton.setIcon(aguaIcon);
					}
				}
				if(boton.getName().equals(nomBoton)){
					boton.setIcon(caballeroIcon);
				}
			}
		}
                panel.repaint();
	
    }


}
