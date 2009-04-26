package ucm.si.animadorGUI;

import java.awt.Dialog.ModalityType;
import java.awt.event.ItemEvent;
import javax.swing.event.ListSelectionEvent;
import ucm.si.util.Contexto;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

import javax.swing.event.ListSelectionListener;
import ucm.si.Checker.util.StateAndLabel;
import ucm.si.animadorGUI.util.ComboBoxExtend;
import ucm.si.navegador.Navegador;

/**
 *
 * @author José Antonio
 * @param <S>
 */
public class FrameAnimador<S> extends JFrame {

    /**
     * Indica una accion a realizar, que podemos recibir o transmitir a el navegador
     */
    public static enum Accion {Avance, GoTo, Return};

    /*
     * Metodos que metan mano al lienzo --separarlos de los botones.
     * Clase Abstracta que herede de Jpanel con esos m�todos.
     * otra clase que herede de a la abstracta
     */
    private static final long serialVersionUID = 1L;
    private Navegador<S> nav;
    private PanelInterface<S> lienzo;
    private S estadoactual;
    //Vector<JButton> botonesAcciones;
    private JList posiblesTransiciones;
    /**
     * Guarda la pantalla que muestra un grafo de navagacion. 
     * La clase tabien engloba las operacion a realizar en el grafo
     */
    private GrafoNavegation<S> grafJUNG;
    private JMenuBar mbar;

    /**
     * Se encarga de Crear e inicializar los componentes del menu para el JFrame
     */
    private void InicializaMenus()
    {
        this.mbar = new JMenuBar();
        JMenu m = new JMenu("Navegacion");
        JMenuItem grafoJUNG = new JCheckBoxMenuItem("Usar Grafo de Navegacion", true);
        grafoJUNG.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                AbstractButton button = (AbstractButton) e.getItem();
                if(button.isSelected())
                {
                    launchGrafoNevegacion(true);
                }
                else
                {
                    launchGrafoNevegacion(false);
                }
            }
        });
        m.add(grafoJUNG);
        this.mbar.add(m);
        this.mbar.setVisible(true);
        this.setJMenuBar(mbar);
    }


    /**
     * Cambia el estado del item en el menu "grafo de navegacion"
     * que se encarga de Controlar la visibilidad del grafo navegacion.
     * @param check
     */
    public void setCheckGrafoNavegacion(boolean check)
    {
        JCheckBoxMenuItem ch = (JCheckBoxMenuItem) this.mbar.getMenu(0).getItem(0);
        ch.setState(false);
        
    }

    /**
     * Se encarga de poner como visible la ventana del grafo de navegacion
     * @param lanzar
     */
    private void launchGrafoNevegacion(boolean lanzar)
    {
        this.grafJUNG.setVisible(lanzar);
    }

    /**
     * Actualiza el grafo de anvegacion
     *
     */
    @Deprecated
    private void actualizaGrafoNavegacion()
    {
        try {
            List<S> l =this.nav.dameRecorrido();
            this.nav.damePosibles();
        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public S getEstadoactual() {
        return estadoactual;
    }

    public void setEstadoactual(S estadoactual) {
        this.estadoactual = estadoactual;
        this.actualizaBotonera();
        //this.actualizaGrafoNavegacion();

    }

    public JPanel getLienzo() {
        return lienzo;
    }

    public void setLienzo(PanelInterface lienzo) {
        this.lienzo = lienzo;
    }

    public FrameAnimador(final AnimadorGrafico<S> control, Drawer dw, Contexto cntxt) {
        

        /*ActionListener actionListenerAvanzar = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                StateAndLabel<S> s  =
                        pulsadaAccion((JButton) actionEvent.getSource());//obtenemos el estado siguiente seleccionado
                //System.out.println("Avanzo estado : \n "+s.getState().toString() + "  .\n" );
                if (s != null) {
                    control.aplicaAvanza(s.getState());//avanzamos a ese estado
                }
            }
        };*/

        // MouseListener encargado de lanzar la operacion de evanzar
        MouseListener mouseListenerAvanza = new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) // Se mira si es doble click
                {
                    int posicion = posiblesTransiciones.locationToIndex(e.getPoint());

                    // Obtenemos el objeto de tipo StateAndLabel para realizar el avance
                    ListModel model =posiblesTransiciones.getModel();
                    StateAndLabel<S> sat = (StateAndLabel<S>) model.getElementAt(posicion);
                    nav.Avanza(sat.getState());
                }
            }
        };

        // ActionListenr encargado de lanzar la operacion Go_To
        ActionListener actionListenerGo_to = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
//				control.aplicaGoTo();
            }
        };

        // ActionListenr encargado de lanzar la operacion de Retroceder
        ActionListener actionListenerRetroceder = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                nav.Retrocede();
            }
        };


        nav = control.getNavigator();
        estadoactual = (S) control.getEstadoactual();
        /*
         * Secci�n de construcci�n de componentes:
         * Botones inferiores y sus caracteristicas
         */
        
        
        this.InicializaMenus();
        //this.grafJUNG.;
        
        
        //this.creaBotonesAccion(actionListenerAvanzar);
        JPanel pane = this.creaPanelAcciones(mouseListenerAvanza, actionListenerGo_to, actionListenerRetroceder);
        this.actualizaBotonera();


        /*
         * Seccion de tratamiento del panel cuyo objetivo es ser el marco
         * donde se va a pintar el estado. 
         */
        lienzo = new PanelJPane<S>(cntxt);
        lienzo.setContexto(cntxt);
        lienzo.setDrawer(dw);
        //Image image = lienzo.createImage(lienzo.getSize().width, lienzo.getSize().height);
        //JImagePainter imagep;


        lienzo.pintaEstado(estadoactual);

        /*
         * Seccion donde se lanza y configura el Visulizador por Grafo
         */
        this.grafJUNG = new GrafoNavegation<S>(this,"Grafo Navegacion",ModalityType.MODELESS, true, this.getNav(), this);
        this.grafJUNG.inicializa();
        this.launchGrafoNevegacion(true);


        /*
         * Configuracion del contenedor.(del propio frame)
         */
        JScrollPane panelIzquierda = new JScrollPane(lienzo);
        JScrollPane panelDerecha = new JScrollPane(pane);
        //panelDerecha.setMaximumSize(new Dimension(50,50 ));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                panelIzquierda,panelDerecha);
        splitPane.setResizeWeight(0.8);
        double prop = (double) 0.20;
        //splitPane.setDividerLocation(0.90);
        splitPane.setDividerLocation((double)0.20);
        //splitPane.s
        this.setContentPane(splitPane);
        this.setTitle("Animador grafico");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

    }

    /**
     * Se encarga de notificar la accion a el JDialog
     * que se encarga de alvergar la funcionalidad del "Grafico de Navegacion"
     * @param accion
     *
     * @param estadoViejo
     *
     * @param estadoNuevo
     */
    public void realizaAccion(FrameAnimador.Accion accion, S estadoViejo , S estadoNuevo )
    {
        try {
            
            this.setEstadoactual(estadoNuevo);
            List<StateAndLabel<S>> l = this.nav.damePosibles();
            switch (accion) {
                case GoTo:
                    break;
                case Avance:
                    this.grafJUNG.transicionAvance( estadoViejo, estadoNuevo, l);
                    break;
                case Return:
                    this.grafJUNG.transicionRetrocede(estadoNuevo);
                    break;
            }
            this.rePinta();
            this.grafJUNG.repaint();


        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * M�todo que se encarga de generar el combo con los valores de los posibles movimientos
     * de los que se dispone.
     * Actualmente no hace nada
     *  
     * @return
     */
    @Deprecated
    private ComboBoxExtend<S> creaComboEstadoActual() {
        //Aqui voy a suponer que puedo tener un vector a partir de dameSiguientes() del navegador
        
        ComboBoxExtend<S> combo = null;
        /*
        //Aqui voy a suponer que puedo tener un vector a partir de dameSiguientes() del navegador
        Vector<StateAndLabel<S>> siguientes = new Vector<StateAndLabel<S>>();
        try {
            Iterator it = nav.damePosibles().iterator();

            while (it.hasNext()) {
                siguientes.add((StateAndLabel<S>) it.next());
            }
            combo = new ComboBoxExtend<S>(siguientes);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        return combo;
    }
    public Navegador<S> getNav() {
        return nav;
    }

    public void setNav(Navegador<S> nav) {
        this.nav = nav;
    }

    public void rePinta() {
        this.lienzo.rePinta(estadoactual);
        //this.combo.repaint();
        //this.grafJUNG.rePintnado(this.nav.dameRecorrido());
        this.repaint();



    //combo = creaComboEstadoActual();
    }

    public void setDrawer(Drawer<S> dw) {
        this.lienzo.setDrawer(dw);
    }

    /**
     * Se encarga de lanzar las acciones necesarias ante la pulsacion de una Accion del tipo Avanza
     * @param stateSelected
     * @return
     */
    private StateAndLabel<S> pulsadaAccionAvanza(StateAndLabel<S> stateSelected) {

        StateAndLabel<S> sat = null;
        try {

            if (stateSelected != null)
            {
                this.nav.Avanza(stateSelected.getState());
            }

        } catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sat;
    }


    /*private StateAndLabel<S> pulsadaAccion(Object o) {
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
    }*/

    /**
     * Crea un nuevo Vector para this.botononesAcciones que contine todas las posibles transiciones
     * Actulamente no hace nada
     * @param listenerAcciones
     */
    @Deprecated
    private void creaBotonesAccion(ActionListener listenerAcciones) {
        /*try {


            List<StateAndLabel<S>> l = this.nav.damePosibles();
            List<String> ltodas = this.nav.dameTransiciones();

            this.botonesAcciones = new Vector<JButton>();
            for (String nombreAccion : ltodas ){
                
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
*/
    }

    /**
     * Se encarga de Crear el JPanel que mostrar las acciones a realizar sobre el contra ejemplo, para el estado actual
     * 
     * @param actionListenerAvanzar  Es un Objeto de tipo MauseListener
     * que se añadirá a un JList y se encarga de que ante la accion de "Doble click"
     * se encarge de lanzar la accion de avanzar hacia el estado seleccionado.
     * El JList tiene un objeto ModelList con los estados posibles contenidos en objetos del tipo StateAndLabel<S>
     * 
     * @param actionListenerGo_to ActioListner que se encarga de lanzar la accion de Ir a (Go To) un estado Estado en concreto
     *
     * @param actionListenerRetroceder  ActionListener que se encarga de lanzar la accion de Retroceder a el estado precedente
     *
     * @return JPanel con todos los componenetes necesarios (Principalmente un JList y un numero indeterminado de botones)
     */
    private JPanel creaPanelAcciones(MouseListener actionListenerAvanzar,ActionListener actionListenerGo_to,ActionListener actionListenerRetroceder)
    {
        JPanel pane = new JPanel();
        GridBagLayout lay = new GridBagLayout();
        pane.setLayout(new GridLayout(2,1,5,5));

        


        // Creamos el JList que alvergara todas las transiciones apra une stado en concreto
        DefaultListModel modelo = new DefaultListModel();
        this.posiblesTransiciones = new JList(modelo);
        this.posiblesTransiciones.addMouseListener(actionListenerAvanzar);
        
        JButton boton3 = new JButton("Retroceder");
        boton3.setHorizontalTextPosition(SwingConstants.CENTER);
        boton3.addActionListener(actionListenerRetroceder);
        //boton3.setSize(new Dimension(10,10));
        // Problema con dimensiones no son bonitas.

        pane.add(this.posiblesTransiciones);
        pane.add(boton3);

        return pane;
    }

    /**
     *  Se encarga de actualizar el Panel que hace las funciones de Botonerade transiciones.
     * Activando solo los botones cuya transicion sea posible para el estado actual que estamos observando.
     */
    private void actualizaBotonera() {
        try
        {
            List<StateAndLabel<S>> l = this.nav.damePosibles();
            DefaultListModel model = (DefaultListModel) this.posiblesTransiciones.getModel();
            model.clear();
            for(StateAndLabel<S> sat : l)
            {
                model.addElement(sat);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(FrameAnimador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
