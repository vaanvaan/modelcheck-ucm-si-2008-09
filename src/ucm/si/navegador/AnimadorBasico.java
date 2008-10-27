package ucm.si.navegador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

/*import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.Renderer;
import edu.uci.ics.jung.visualization.VisualizationViewer;*/
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Estado;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Laberinto.Final;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.LaberintoPropo;
import ucm.si.Laberinto.Posicion;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Proposicion;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

public class AnimadorBasico<S> extends AnimadorInterface<S> {
	
	
	private S estadoactual;
        private Laberinto lab;
	
	
        
        public void setLaberinto(Laberinto lab){
                this.lab = lab;
        }
	
	public int pintaopciones(List<S> ops){
		int cont = 0;
		Iterator<S> it = ops.iterator();
                if (!it.hasNext()){
                    System.out.println("Fin del Camino");
                } else 
		while(it.hasNext()){
			System.out.println(cont++ + ".- " + it.next());                        
		}
                System.out.println("90.- retroceder, 99.- salir: ");
		Scanner scan = new Scanner(System.in);
		int op = scan.nextInt();
		
		return op;
	}

	@Override
	public void manejaAccion(Avanza<S> accion) {
		//System.out.println("--avanza---->" + accion.getEstado());                
		//List<S> listapos = navigator.damePosibles();
		//int op = pintaopciones(listapos);
		//navigator.Avanza(listapos.get(op));            
                estadoactual = accion.getEstado();
                pintaconsola();
	}

	@Override
	public void manejaAccion(GoToEstado<S> accion) {
		// TODO Auto-generated method stub
		System.out.println("--goto---->" + accion.getEstado());
		//List<S> listapos = navigator.damePosibles();
		//int op = pintaopciones(listapos);
		//navigator.GoToEstado(listapos.get(op));
                estadoactual = accion.getEstado();
                pintaconsola();
	}

	@Override
	public void manejaAccion(Retrocede<S> accion) {
		//navigator.Retrocede();
                estadoactual = accion.getEstado();
		System.out.println("Retroceso completado");
		System.out.println("El estado actual es:" + estadoactual.toString());
                pintaconsola();
	}
	
	public AnimadorBasico(Navegador<S> n)
        {
                super(n);
		super.navigator = n;
                super.navigator.addOyente(this);
		this.estadoactual = navigator.dameInicial();
	}
//	public static void pinta(){
//		Graph g = new DirectedSparseGraph();
//		Estado ini = navigator.dameInicial();
//		Vertex v1 = g.addVertex(new DirectedSparseVertex());
//		Layout l = new FRLayout( g );
//		Renderer r = new PluggableRenderer();
//		VisualizationViewer vv = new VisualizationViewer( l, r );
//		JFrame jf = new JFrame();
//		jf.getContentPane().add ( vv );
//
//
//	}
	public void pintaconsola(){		
                Posicion p = (Posicion) estadoactual;
		System.out.println(estadoactual.toString());
                for (int i = 0; i < lab.getDim();i++){
                    for (int j = 0; j < lab.getDim();j++)
                        if ((p.getPosX()==j)&&(p.getPosY()==i))
                            System.out.print(" X");
                        else
                            System.out.print(lab.checkPos(new Posicion(j, i))?" .":" #");
                    System.out.println();
                }
	}
	
        public static void main(String[] args) 
        {
            Laberinto lab = new Laberinto();
            ModelChecker<Posicion> m = new DefaultModelChecker<Posicion>(lab);
            Posicion pos = new Posicion(1,1);
            LaberintoPropo prop = new LaberintoPropo(pos);
            prop.setLab(lab);
            Final fin = new Final(6,6);
            Formula nofin = new And(new Not(fin),prop);
            Formula haycamino = new EU(nofin,fin);
            Resultado<Posicion> res = m.chequear(lab, new Not(haycamino),pos);
            Navegador<Posicion> nav = new Navegador<Posicion>(res.getContraejemplo(), res.getEjemplo()); 
            AnimadorBasico<Posicion> anim = new AnimadorBasico<Posicion>(nav);
            anim.setLaberinto(lab);
            nav.GoToEstado(nav.dameInicial());
            int op = anim.pintaopciones(nav.damePosibles());
            while (op<99){                
                switch (op){
                    case 90:
                       nav.Retrocede();
                       break;
                    case 99:
                       break;
                    default:
                       nav.Avanza(nav.damePosibles().get(op));
                       break;
                }           
                op = anim.pintaopciones(nav.damePosibles());
            }
	}

}
