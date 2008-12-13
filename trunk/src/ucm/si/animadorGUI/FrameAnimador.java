package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
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

    public FrameAnimador(final AnimadorGrafico<S> control, Drawer dw, Contexto cntxt) {
        
        ActionListener actionListenerAvanzar = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                StateAndLabel<S> s =
                        pulsadaAccion((JButton) actionEvent.getSource());//obtenemos el estado siguiente seleccionado
                //System.out.println("Avanzo estado : \n "+s.getState().toString() + "  .\n" );
                if (s != null) {
                    control.aplicaAvanza(s.getState());//avanzamos a ese estado
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
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(1, 2,5,5));
        this.creaBotonesAccion(actionListenerAvanzar);
        this.actualizaBotonera();
        JPanel botoneraAcciones = new JPanel();
        int lado = Math.round((float)Math.sqrt(nav.dameTransiciones().size()));
        botoneraAcciones.setLayout(new GridLayout(lado,lado,5,5));
        for (int i = 0; i < botonesAcciones.size(); i++) {
            botoneraAcciones.add(botonesAcciones.get(i));
        }
        JButton boton3 = new JButton("Retroceder");
        boton3.setHorizontalTextPosition(SwingConstants.CENTER);
        boton3.addActionListener(actionListenerRetroceder);



        pane.add(botoneraAcciones);
        pane.add(boton3);
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
        JScrollPane panelAbajo = new JScrollPane(pane);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                panelArriba,panelAbajo);
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

    private StateAndLabel<S> pulsadaAccion(Object o) {
        int numAccion = this.botonesAcciones.indexOf(o);
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
     * Actualiza el ComboBoxExtend con los objetos que devuelve DamePosibles
     */
    private void actualizaBotonera() {
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
                    "Error al gnerar ComboBox, Excepcion lanzada desde damePosibles", ex);
            System.out.println("Error al generar ComboBox");
        }

    }
}
