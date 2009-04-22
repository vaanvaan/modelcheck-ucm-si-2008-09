/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Paint;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import ucm.si.Checker.util.StateAndLabel;
import ucm.si.navegador.Navegador;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author José Antonio
 */



public class GrafoNavegation<S> extends JDialog
{

    

    private Graph<S, String> g;
    private VisualizationViewer<S,String> vv;
    private Navegador<S> nav;
    private FrameAnimador<S> parent;

    private boolean visible;

    private WindowListener closeListener = new WindowListener() {

        public void windowOpened(WindowEvent e) {

        }

        public void windowClosing(WindowEvent e) {
            parent.setCheckGrafoNavegacion(false);
        }

        public void windowClosed(WindowEvent e) {

        }

        public void windowIconified(WindowEvent e) {

        }

        public void windowDeiconified(WindowEvent e) {

        }

        public void windowActivated(WindowEvent e) {

        }

        public void windowDeactivated(WindowEvent e) {

        }
    };



    public GrafoNavegation(Frame owner, String title, ModalityType modalityType, 
            boolean visible, Navegador<S> nav, FrameAnimador<S> parent) {
        super(owner, title, modalityType);
        this.nav = nav;
        this.parent = parent;
        this.visible = visible;
        this.g = new DirectedSparseMultigraph();
        super.setVisible(visible);
        this.addWindowListener(closeListener);

    }

    public void inicializa()
    {

        // Layout<V, E>, VisualizationComponent<V,E>
        Layout<S, String> layout = new KKLayout<S, String>(this.g);
        layout.setSize(new Dimension(300,300));
        //final VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
        this.vv = new VisualizationViewer<S,String>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        
        Transformer<S,Paint> vertexPaint = new Transformer<S,Paint>() 
        {
            public Paint transform(S s) 
            {
                Navegador<S> n = nav;
                if(nav.dameRecorrido().contains(s))
                    return Color.GREEN;
                return Color.RED;
            }
        };

        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        vv.addKeyListener(gm.getModeKeyListener());
        this.getContentPane().add(vv);
        this.pack();
    }

    public void transicion(FrameAnimador.Accion accion, S estado)
    {
    }

    public void transicionAvance( S estadoViejo , S estadoNuevo, List<StateAndLabel<S>> listaPosibles)
    {
        for(StateAndLabel sat: listaPosibles)
        {
            S s = (S) sat.getState();
            g.addVertex(s);
            String labe = sat.getLabel();
            this.g.addEdge(labe, estadoViejo, s);

        }
    }

    public void transicionRetrocede( S estado)
    {
        
    }

    




    @Override
    public void setVisible(boolean b) {
        this.visible = b;
        super.setVisible(b);
    }






}


