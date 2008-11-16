package ucm.si.animadorGUI.laberinto;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;

public class PanelLaberinto<S> extends Panel<S>{
	

	private Laberinto lab;
	private ArrayList<JButton> botones;
	
	public PanelLaberinto(Laberinto l) {
		lab = l;
	}
	@Override
	public void pintaEstado(S s) {
		ImageIcon aguaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/caballero.jpg");
		botones = new ArrayList<JButton>((lab.getDim()^2)); //al menos va a tener dim x dim
		Posicion p = (Posicion) s;

		JPanel estado = this;
		GridLayout cuadrados = new GridLayout(lab.getDim(), lab.getDim());
		// JButton boton = new JButton();
		cuadrados.setColumns(lab.getDim());
		cuadrados.setHgap(lab.getDim());
		// Image imagenFuente =
		// Toolkit.getDefaultToolkit().getImage("agua.jpg");

		estado.setLayout(cuadrados);

		for (int i = 0; i < lab.getDim(); i++) {
			for (int j = 0; j < lab.getDim(); j++) {
				if (p.getPosX() == i && p.getPosY() == j) {
					JButton jb = new JButton();
					jb.setName("b" + i + "," + j);
					jb.setIcon(caballeroIcon);
					botones.add(jb);
					estado.add(jb);
				} else {
					if (lab.checkPos(new Posicion(i, j))) {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(hierbaIcon);
						botones.add(jb);
						estado.add(jb);
					} else {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(aguaIcon);
						botones.add(jb);
						estado.add(jb);
					}
				}
			}
		}
	
	}

	@Override
	public void rePinta(S s) {

		ImageIcon aguaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("src/ucm/si/animadorGUI/laberinto/caballero.jpg");
		if (botones!=null){
			Posicion p = (Posicion) s;
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
	
		
	}

}
