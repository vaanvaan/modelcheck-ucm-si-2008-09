package ucm.si.animadorGUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import ucm.si.util.Contexto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import ucm.si.Checker.util.StateAndLabel;
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
    Vector<JButton> botonesAcciones;
    JComboBox comboBoxAcciones;
    private int tipoBotonera;

    public S getEstadoactual() {
        return estadoactual;
    }

    public void setEstadoactual(S estadoactual) {
        this.estadoactual = estadoactual;
        this.actualizaBotonera();

    }

    public JPanel getLienzo() {
        return lienzo;
    }

    public void setLienzo(PanelInterface lienzo) {
        this.lienzo = lienzo;
    }

    public FrameAnimador(final AnimadorGrafico<S> control, Drawer dw, Contexto cntxt, int tipoBotonera) {

        ActionListener actionListenerAvanzar = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                int numAccion = botonesAcciones.indexOf((JButton) actionEvent.getSource());
                StateAndLabel<S> s =
                        pulsadaAccion(numAccion);//obtenemos el estado siguiente seleccionado
                //System.out.println("Avanzo estado : \n "+s.getState().toString() + "  .\n" );
                if (s != null) {
                    control.aplicaAvanza(s.getState());//avanzamos a ese estado
                }
            }
        };
        ActionListener actionListenerAvanzarCombo = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                int numAccion = comboBoxAcciones.getSelectedIndex();
                if (numAccion >= 0) {
                    try {
                        numAccion = nav.dameTransiciones().indexOf(
                                nav.damePosibles().get(numAccion).getLabel());
                        StateAndLabel<S> s =
                                pulsadaAccion(numAccion);//obtenemos el estado siguiente seleccionado
                        //System.out.println("Avanzo estado : \n "+s.getState().toString() + "  .\n" );
                        if (s != null) {
                            control.aplicaAvanza(s.getState());//avanzamos a ese estado
                        }
                    } catch (Exception e) {
                        System.out.println("Problema en listener de ComboBox");
                    }
                }
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
        this.tipoBotonera = tipoBotonera;
        JPanel botoneraAcciones = new JPanel();


        if (tipoBotonera == AnimadorGrafico.BOTONERA_COMBO) {
            this.creaComboBoxAcciones(actionListenerAvanzarCombo);
            JLabel transicion = new JLabel("Transición: ");
            transicion.setAlignmentY(Component.CENTER_ALIGNMENT);
            botoneraAcciones.setLayout(new BoxLayout(botoneraAcciones, BoxLayout.LINE_AXIS));
            botoneraAcciones.add(Box.createHorizontalGlue());
            botoneraAcciones.add(transicion);
            int espacio = 10;
            botoneraAcciones.add(Box.createRigidArea(new Dimension(espacio,0)));
            comboBoxAcciones.setAlignmentY(Component.CENTER_ALIGNMENT);
            comboBoxAcciones.setMaximumSize(new Dimension(
                    (int)comboBoxAcciones.getPreferredSize().getWidth(),
                    (int)comboBoxAcciones.getMinimumSize().getHeight()));
            botoneraAcciones.add(comboBoxAcciones);
            botoneraAcciones.add(Box.createRigidArea(new Dimension(espacio,0)));
        } else {
            this.creaBotonesAccion(actionListenerAvanzar);
            this.actualizaBotonera();
            botoneraAcciones.setLayout(new BoxLayout(botoneraAcciones, BoxLayout.Y_AXIS));

            for (int i = 0; i < botonesAcciones.size(); i++) {
                botonesAcciones.get(i).setAlignmentX(Component.LEFT_ALIGNMENT);
                botoneraAcciones.add(botonesAcciones.get(i));
            }
        }


        JButton boton3 = new JButton("Retroceder");
        boton3.setHorizontalTextPosition(SwingConstants.CENTER);
        boton3.addActionListener(actionListenerRetroceder);

        JPanel pane =new JPanel();
        Container panelAbajo;
        if (tipoBotonera == AnimadorGrafico.BOTONERA_COMBO) {
            botoneraAcciones.add(boton3);
            botoneraAcciones.add(Box.createHorizontalGlue());
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            pane.add(Box.createVerticalGlue());
            pane.add(botoneraAcciones);
            pane.add(Box.createVerticalGlue());
            panelAbajo = new JScrollPane(pane);
        } else {
            JScrollPane scrollBotonera = new JScrollPane(botoneraAcciones);
            pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
            pane.add(scrollBotonera);
            pane.add(boton3);
            panelAbajo = pane;
        }
        
        /*
         * Secci�n de tratamiento del panel cuyo objetivo es ser el marco
         * donde se va a pintar el estado. 
         */
        lienzo = new PanelJPane<S>(cntxt);
        lienzo.setContexto(cntxt);
        lienzo.setDrawer(dw);


        lienzo.pintaEstado(estadoactual);

        /*
         * Configuraci�n del contenedor.(del propio frame)
         */
        JScrollPane panelArriba = new JScrollPane(lienzo);        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                panelArriba, panelAbajo);
        splitPane.setResizeWeight(0.8);
        this.setContentPane(splitPane);
        this.setTitle("Animador grafico");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

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

    public void setDrawer(Drawer<S> dw) {
        this.lienzo.setDrawer(dw);
    }

    private StateAndLabel<S> pulsadaAccion(int numAccion) {
        List<String> transiciones = this.nav.dameTransiciones();
        String nombreAccion = transiciones.get(numAccion);
        List<StateAndLabel<S>> listaPosibles;
        int i = 0;
        StateAndLabel<S> salfinal = null;
        try {
            listaPosibles = this.nav.damePosibles();
            while ((salfinal == null) && (i < listaPosibles.size())) {
                if (listaPosibles.get(i).getLabel().equalsIgnoreCase(nombreAccion)) {
                    salfinal = listaPosibles.get(i);
                }
                i++;
            }
        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salfinal;
    }

    /**
     * Crea un ComboBoxExtend Nuevo a partir de los elemetos posibles.
     */
    private void creaBotonesAccion(ActionListener listenerAcciones) {
        try {


            List<StateAndLabel<S>> l = this.nav.damePosibles();
            List<String> ltodas = this.nav.dameTransiciones();

            this.botonesAcciones = new Vector<JButton>();
            for (Iterator<String> it = ltodas.iterator(); it.hasNext();) {
                String nombreAccion = it.next();
                JButton boton = new JButton(nombreAccion);
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                boton.addActionListener(listenerAcciones);
                this.botonesAcciones.add(boton);
            }

        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE,
                    "Error al generar la botonera, Excepcion lanzada desde damePosibles", ex);
        //System.out.println("Error al generar ComboBox");
        }

    }

    /**
     * Crea un ComboBoxExtend Nuevo a partir de los elemetos posibles.
     */
    private void creaComboBoxAcciones(ActionListener listenerAcciones) {
        try {


            List<StateAndLabel<S>> l = this.nav.damePosibles();

            this.comboBoxAcciones = new JComboBox();
            for (Iterator<StateAndLabel<S>> it = l.iterator(); it.hasNext();) {
                String nombreAccion = it.next().getLabel();
                this.comboBoxAcciones.addItem(nombreAccion);
            }
            this.comboBoxAcciones.addActionListener(listenerAcciones);


        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE,
                    "Error al generar la botonera, Excepcion lanzada desde damePosibles", ex);
        //System.out.println("Error al generar ComboBox");
        }

    }

    /**
     * Actualiza el ComboBoxExtend con los objetos que devuelve DamePosibles
     */
    private void actualizaBotonera() {
        if (this.tipoBotonera == AnimadorGrafico.BOTONERA_CLASICA) {
            try {
                for (int i = 0; i < botonesAcciones.size(); i++) {
                    botonesAcciones.get(i).setEnabled(false);
                }
                List<StateAndLabel<S>> l = this.nav.damePosibles();
                List<String> ltransiciones = this.nav.dameTransiciones();
                for (int i = 0; i < l.size(); i++) {
                    String nombreAccion = l.get(i).getLabel();
                    botonesAcciones.get(ltransiciones.indexOf(nombreAccion)).setEnabled(true);
                }

            } catch (Exception ex) {
                Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE,
                        "Error al generar Botonera, Excepcion lanzada desde damePosibles", ex);
                System.out.println("Error al generar Botonera" + ex.getMessage());
            }
        } else {
            this.actualizaCombo();
        }

    }

    /**
     * Actualiza el ComboBoxExtend con los objetos que devuelve DamePosibles
     */
    private void actualizaCombo() {
        try {
            ActionListener al = this.comboBoxAcciones.getActionListeners()[0];
            this.comboBoxAcciones.removeActionListener(al);

            this.comboBoxAcciones.removeAllItems();
            List<StateAndLabel<S>> l = this.nav.damePosibles();
            for (int i = 0; i < l.size(); i++) {
                String nombreAccion = l.get(i).getLabel();
                this.comboBoxAcciones.addItem(nombreAccion);
            }
            this.comboBoxAcciones.addActionListener(al);

        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE,
                    "Error al gnerar ComboBox, Excepcion lanzada desde damePosibles", ex);
            System.out.println("Error al generar ComboBox" + ex.getMessage());
        }

    }
}
