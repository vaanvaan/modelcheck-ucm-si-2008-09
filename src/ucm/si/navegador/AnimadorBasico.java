package ucm.si.navegador;

import java.util.ArrayList;
import java.util.Iterator;
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
import ucm.si.Checker.Estado;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

public class AnimadorBasico extends AnimadorInterface {
	
	private static Navegador navigator;
	private Estado estadoactual;
	
	public Navegador getNavigator() {
		return navigator;
	}

	public void setNvigator(Navegador nvigator) {
		this.navigator = nvigator;
	}
	
	public int pintaopciones(ArrayList ops){
		int cont = 0;
		Iterator it = ops.iterator();
		while(it.hasNext()){
			System.out.print(cont + ".- ");
			System.out.println(it.next().toString());
		}
		Scanner scan = new Scanner(System.in);
		int op = scan.nextInt();
		
		return op;
	}

	@Override
	public void manejaAccion(Avanza accion) {
		System.out.println("------>");
		ArrayList listapos = (ArrayList) navigator.damePosibles();
		int op = pintaopciones(listapos);
		navigator.Avanza((Estado) listapos.get(op));
	}

	@Override
	public void manejaAccion(GoToEstado accion) {
		// TODO Auto-generated method stub
		System.out.println("------>");
		ArrayList listapos = (ArrayList) navigator.damePosibles();
		int op = pintaopciones(listapos);
		navigator.GoToEstado((Estado) listapos.get(op));
	}

	@Override
	public void manejaAccion(Retrocede accion) {
		navigator.Retrocede();
		System.out.println("Retroceso completado");
		System.out.println("El estado actual es:" + estadoactual.toString());
	}
	
	public AnimadorBasico(Navegador n){
		navigator = n;
                this.navigator.addOyente(this);
		Estado ini = navigator.dameInicial();
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
		estadoactual = navigator.dameInicial();
		System.out.print(estadoactual.toString());
	}
	public static void main(String[] args) {
//		pinta();
	}

}
