package ucm.si.animadorGUI.laberinto;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ucm.si.Checker.util.StateAndLabel;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;
import ucm.si.navegador.Navegador;

public class FrameAnimador<S> extends JFrame {

	/**
	 * 
	 */
	/*
	 * Mï¿½todos que metan mano al lienzo --separarlos de los botones.
	 * Clase Abstracta que herede de Jpanel con esos mï¿½todos.
	 * otra clase que herede de a la abstracta
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Navegador<S> nav;
	private PanelInterface<S> lienzo;
	private S estadoactual;
	ComboBoxExtend<S> combo;

	public S getEstadoactual() {
		return estadoactual;
	}

	public void setEstadoactual(S estadoactual) {
		this.estadoactual = estadoactual;
	}

	public JPanel getLienzo() {
		return lienzo;
	}

	public void setLienzo(PanelInterface lienzo) {
		this.lienzo = lienzo;
	}

	public FrameAnimador(final AnimadorGrafico<S> control, Drawer dw, Contexto cntxt) {
		// Estado S;
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				FrameAnimador.this.dispose();
			}
		};
		ActionListener actionListenerAvanzar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				S s = combo.getSelectedItem();//obtenemos el estado siguiente seleccionado
				control.aplicaAvanza(s);//avanzamos a ese estado
			}
		};
		ActionListener actionListenerGo_to = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
//				control.aplicaGoTo();
			}
		};
		ActionListener actionListenerRetroceder = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				control.aplicaRetrocede();
			}
		};
		
		
		nav = control.getNavigator();
		estadoactual = (S) control.getEstadoactual();
		/*
		 * Sección de construcción de componentes:
		 * Botones inferiores y sus caracteristicas
		 */
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout());
		JButton boton1 = new JButton("Avanzar");
		combo = creaComboEstadoActual();
		JButton boton3 = new JButton("Retroceder");
		boton1.setHorizontalTextPosition(SwingConstants.CENTER);
		boton3.setHorizontalTextPosition(SwingConstants.CENTER);
		boton1.addActionListener(actionListenerAvanzar);
		boton3.addActionListener(actionListenerRetroceder);
		pane.add(boton1);
		pane.add(combo);
		pane.add(boton3);
		/*
		 * Sección de tratamiento del panel cuyo objetivo es ser el marco
		 * donde se va a pintar el estado. 
		 */
        lienzo = new PanelJPane<S>(cntxt);
        lienzo.setContexto(cntxt);
        lienzo.setDrawer(dw);
		lienzo.pintaEstado(estadoactual);
		lienzo.setSize(200, 200);
		pane.add(lienzo);
		
		/*
		 * Configuración del contenedor.(del propio frame)
		 */
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(lienzo, BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(pane), BorderLayout.SOUTH);
		this.setTitle("Animador gráfico");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setVisible(true);

	}

	/**
	 * Método que se encarga de generar el combo con los valores de los posibles movimientos
	 * de los que se dispone.
	 * @param control  
	 * @return
	 */
	private ComboBoxExtend<S> creaComboEstadoActual() {
		//Aqui voy a suponer que puedo tener un vector a partir de dameSiguientes() del navegador
		Vector<StateAndLabel<S>> siguientes = new Vector<StateAndLabel<S>>();
		ComboBoxExtend<S> combo = null;
		try {
			Iterator it = nav.damePosibles().iterator();
			while(it.hasNext()){
				siguientes.add((StateAndLabel<S>) it.next());
			}
			combo = new ComboBoxExtend<S>(siguientes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return combo;
	}
	private Vector generaComboVector(){
		Vector<StateAndLabel<S>> siguientes = new Vector<StateAndLabel<S>>();
		try {
			Iterator it = nav.damePosibles().iterator();
			while(it.hasNext()){
				siguientes.add((StateAndLabel<S>) it.next());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return siguientes;
	}

	public Navegador<S> getNav() {
		return nav;
	}

	public void setNav(Navegador<S> nav) {
		this.nav = nav;
	}


	public void rePinta() {
		lienzo.rePinta(estadoactual);
		combo.removeAllItems();
		Vector<StateAndLabel<S>> nuevas = generaComboVector();
		Iterator it = nuevas.iterator();
		while (it.hasNext()) {
			combo.addItem(it.next()); 
		}
		this.repaint();
	}
        
        public void setDrawer(Drawer<S> dw)
        {
            lienzo.setDrawer(dw);
        }

}
