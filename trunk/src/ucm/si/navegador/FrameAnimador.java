package ucm.si.navegador;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;

public class FrameAnimador<S> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Navegador<S> nav;
	private JPanel lienzo;
	private S estadoactual;
	private Laberinto lab;
	private ArrayList<JButton> botones;

	public S getEstadoactual() {
		return estadoactual;
	}

	public void setEstadoactual(S estadoactual) {
		this.estadoactual = estadoactual;
	}

	public JPanel getLienzo() {
		return lienzo;
	}

	public void setLienzo(JPanel lienzo) {
		this.lienzo = lienzo;
	}

	public FrameAnimador(final AnimadorFrame control) {
		// Estado S;
		this.setTitle("Animador v1");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				FrameAnimador.this.dispose();
			}
		};
		ActionListener actionListenerAvanzar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				control.aplicaAvanza();
			}
		};
		ActionListener actionListenerGo_to = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				control.aplicaGoTo();
			}
		};
		ActionListener actionListenerRetroceder = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				control.aplicaRetrocede();
			}
		};
		lab = control.getLab();
		estadoactual = (S) control.getEstadoactual();
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout());
		JButton boton = new JButton("Cerrar");
		JButton boton1 = new JButton("Avanzar");
		JButton boton2 = new JButton("Go To");
		JButton boton3 = new JButton("Retroceder");
		lienzo = pintaEstado();
		lienzo.setSize(200, 200);
		boton1.setHorizontalTextPosition(SwingConstants.CENTER);
		boton2.setHorizontalTextPosition(SwingConstants.CENTER);
		boton3.setHorizontalTextPosition(SwingConstants.CENTER);
		boton1.addActionListener(actionListenerAvanzar);
		boton2.addActionListener(actionListenerGo_to);
		boton3.addActionListener(actionListenerRetroceder);
		boton.addActionListener(actionListener);
		pane.add(lienzo);
		pane.add(boton1);
		pane.add(boton2);
		pane.add(boton3);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(boton, BorderLayout.NORTH);
		c.add(lienzo, BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(pane), BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setVisible(true);

	}

	public Navegador<S> getNav() {
		return nav;
	}

	public void setNav(Navegador<S> nav) {
		this.nav = nav;
	}

	public JPanel pintaEstado() {
		/*
		 * Pintamos el laberinto.
		 */
		ImageIcon aguaIcon = new ImageIcon("etc/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("etc/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("etc/caballero.jpg");
		botones = new ArrayList<JButton>((lab.getDim()^2)); //al menos va a tener dim x dim
		Posicion p = (Posicion) estadoactual;

		JPanel estado = new JPanel();
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
		return estado;
	}

	public void rePinta() {
		ImageIcon aguaIcon = new ImageIcon("etc/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("etc/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("etc/caballero.jpg");

		Posicion p = (Posicion) estadoactual;

		lienzo = new JPanel();
		GridLayout cuadrados = new GridLayout(lab.getDim(), lab.getDim());
		cuadrados.setColumns(lab.getDim());
		cuadrados.setHgap(lab.getDim());

		lienzo.setLayout(cuadrados);

		for (int i = 0; i < lab.getDim(); i++) {
			for (int j = 0; j < lab.getDim(); j++) {
				if (p.getPosX() == i && p.getPosY() == j) {
					JButton jb = new JButton();
					jb.setName("b" + i + "," + j);
					jb.setIcon(caballeroIcon);
					lienzo.add(jb);
				} else {
					if (lab.checkPos(new Posicion(i, j))) {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(hierbaIcon);
						lienzo.add(jb);
					} else {
						JButton jb = new JButton();
						jb.setName("b" + i + "," + j);
						jb.setIcon(aguaIcon);
						lienzo.add(jb);
					}
				}
			}
		}
		lienzo.repaint();
	}

	public void rePinta2() {
		ImageIcon aguaIcon = new ImageIcon("etc/agua.jpg");
		ImageIcon hierbaIcon = new ImageIcon("etc/hierba.jpg");
		ImageIcon caballeroIcon = new ImageIcon("etc/caballero.jpg");
		if (botones!=null){
			Posicion p = (Posicion) estadoactual;
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
