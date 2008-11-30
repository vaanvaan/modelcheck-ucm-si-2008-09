package ucm.si.animadorGUI;

import ucm.si.animadorGUI.util.ComboBoxExtend;
import ucm.si.animadorGUI.laberinto.*;
import ucm.si.util.Contexto;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
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
	 * M�todos que metan mano al lienzo --separarlos de los botones.
	 * Clase Abstracta que herede de Jpanel con esos m�todos.
	 * otra clase que herede de a la abstracta
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Navegador<S> nav;
	private PanelInterface<S> lienzo;
	private S estadoactual;
	ComboBoxExtend<StateAndLabel<S>> combo;

	public S getEstadoactual() {
		return estadoactual;
	}

	public void setEstadoactual(S estadoactual) {
            this.estadoactual = estadoactual;            
            this.actualizaComoboBox();
            
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
				StateAndLabel<S> s =  combo.getSelectedItem();//obtenemos el estado siguiente seleccionado
				//System.out.println("Avanzo estado : \n "+s.getState().toString() + "  .\n" );
                                if(s != null)
                                    control.aplicaAvanza(s.getState());//avanzamos a ese estado
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
		 * Secci�n de construcci�n de componentes:
		 * Botones inferiores y sus caracteristicas
		 */
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout());
		JButton boton1 = new JButton("Avanzar");
		//combo = creaComboEstadoActual();
		JButton boton3 = new JButton("Retroceder");
		boton1.setHorizontalTextPosition(SwingConstants.CENTER);
		boton3.setHorizontalTextPosition(SwingConstants.CENTER);
		boton1.addActionListener(actionListenerAvanzar);
		boton3.addActionListener(actionListenerRetroceder);
                
                
                this.creaNuevaComboBox();
            
                
		pane.add(boton1);
		pane.add(combo);
		pane.add(boton3);
		/*
		 * Secci�n de tratamiento del panel cuyo objetivo es ser el marco
		 * donde se va a pintar el estado. 
		 */
                lienzo = new PanelJPane<S>(cntxt);
                lienzo.setContexto(cntxt);
                 lienzo.setDrawer(dw);
        
        
		lienzo.pintaEstado(estadoactual);
		lienzo.setSize(200, 200);
		pane.add(lienzo);
		
		/*
		 * Configuraci�n del contenedor.(del propio frame)
		 */
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(lienzo, BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(pane), BorderLayout.SOUTH);
		this.setTitle("Animador grafico");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setVisible(true);

	}

	/**
	 * M�todo que se encarga de generar el combo con los valores de los posibles movimientos
	 * de los que se dispone.
	 * @param control  
	 * @return
	 */
	/*private ComboBoxExtend<S> creaComboEstadoActual() {
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
	}*/

	public Navegador<S> getNav() {
		return nav;
	}

	public void setNav(Navegador<S> nav) {
		this.nav = nav;
	}


	public void rePinta() {
            this.lienzo.rePinta(estadoactual);
            //this.combo.repaint();
            this.repaint();
            
                
		
                        //combo = creaComboEstadoActual();
	}
        
        public void setDrawer(Drawer<S> dw)
        {
            this.lienzo.setDrawer(dw);
        }
        
        /**
         * Actualiza el ComboBoxExtend Con los elemetos devueltos en el List
         * @param vect
         */
        private void actualizaComoboBox(List<StateAndLabel<S>> vect)
        {
            this.combo.removeAllItems();
            for(int i = 0; i< vect.size(); i++)
            {
                this.combo.addItem(vect.get(i));
            }
        }
        
       /**
        * Crea un ComboBoxExtend Nuevo a partir de los elemetos posibles.
        */ 
      private void creaNuevaComboBox()
      {
          try {
                
                List<StateAndLabel<S>> l = this.nav.damePosibles();
                Vector<StateAndLabel<S>> v  = new Vector<StateAndLabel<S>>(l); 
                this.combo = new ComboBoxExtend<StateAndLabel<S>>(v);
                if(l.size() == 0)
                {
                    this.combo.addItem("No Mas Posibles");
                   
                }
                
               
            } catch (Exception ex) {
                Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, 
                        "Error al generar ComboBox, Excepcion lanzada desde damePosibles", ex);
                //System.out.println("Error al generar ComboBox");
            }
          
          
      
                  
      }
        
        /**
         * Actualiza el ComboBoxExtend con los objetos que devuelve DamePosibles
         */
       private void actualizaComoboBox() {
            try {
                this.combo.removeAllItems();
                List<StateAndLabel<S>> l = this.nav.damePosibles();
                for (int i = 0; i < l.size(); i++) {
                    StateAndLabel<S> st = l.get(i);
                    this.combo.addItem(st);
                }
                
                if(l.size() == 0)
                {
                    this.combo.addItem("No Mas Posibles");
                    //System.out.print("Fuera");
                }

            } catch (Exception ex) {
                Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, 
                        "Error al gnerar ComboBox, Excepcion lanzada desde damePosibles", ex);
                System.out.println("Error al generar ComboBox");
            }
     
        }

}
