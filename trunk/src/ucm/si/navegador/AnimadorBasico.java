package ucm.si.navegador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucm.si.Checker.DefaultModelChecker;
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
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

public class AnimadorBasico<S> extends AnimadorInterface<S> {

    private S estadoactual;
    private Laberinto lab;

    public void setLaberinto(Laberinto lab) {
        this.lab = lab;
    }

    public int pintaopciones(List<S> ops) {
        int cont = 0;
        Iterator<S> it = ops.iterator();
        if (!it.hasNext()) {
            System.out.println("Fin del Camino");
        } else {
            while (it.hasNext()) {
                System.out.println(cont++ + ".- " + it.next());
            }
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
        System.out.println("-- RECORRIDO ---");
        this.printRecorrido();
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
        System.out.println("-- NUEVO RECORRIDO ---");
        this.printRecorrido();
        pintaconsola();
    }

    @Override
    public void manejaAccion(Retrocede<S> accion) {
        //navigator.Retrocede();
        estadoactual = accion.getEstado();
        System.out.println("Retroceso completado");
        System.out.println("El estado actual es:" + estadoactual.toString());
        System.out.println("-- RECORRIDO ---");
        this.printRecorrido();
        pintaconsola();
    }

    public AnimadorBasico(Navegador<S> n) {
        super(n);
        super.navigator = n;
        //super.navigator.addOyente(this);
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
    public void pintaconsola() {
        Posicion p = (Posicion) estadoactual;
        System.out.println(estadoactual.toString());
        for (int i = 0; i < lab.getDim(); i++) {
            for (int j = 0; j < lab.getDim(); j++) {
                if ((p.getPosX() == j) && (p.getPosY() == i)) {
                    System.out.print(" X");
                } else {
                    System.out.print(lab.checkPos(new Posicion(j, i)) ? " ." : " #");
                }
            }
            System.out.println();
        }
    }

    public void printRecorrido() {
        List<S> l = this.navigator.dameRecorrido();

        for (int i = 0; i < l.size(); i++) //for (int i = l.size(); i < 0; i--)
        {
            S s = l.get(i);
            System.out.println("Paso " + i + " : " + s.toString());

        }

    }

    public void printOpciones() {
        Iterator<S> it = this.navigator.damePosibles().iterator();
        int i = 0;
        while (it.hasNext()) {
            S s = it.next();
            System.out.println("Opcion " + i + " : " + s.toString());
            i++;
        }
        int pos = this.navigator.dameRecorrido().size() - 1;
        if (pos > 0) {
            System.out.println("Opcion " + i + " : " + "RETROCEDER a estado : " + this.navigator.dameRecorrido().get(pos - 1));
        }
    }

    public int getOpciones() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String number;
        System.out.print("Introduce Opcion  : ");
        try {
            number = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(AnimadorBasico.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al introducir opcion, intente de nuevo");
            return this.getOpciones();
        }

        try {
            int op = Integer.parseInt(number);
            return op;
        } catch (NumberFormatException e) {
            //
            //return getOpciones();
            System.out.println("FIN DE APLICACION");
            return (-10);
        }
    }

    public boolean opcionCorrecta(int op) {
        Set<S> l = this.navigator.damePosibles();
        if ((op <= l.size()) && (op >= 0)) {
            return true;
        }
        return false;
    }

    public void aplicaOpcion(int op) {
        if ((op == this.navigator.damePosibles().size()) && (this.navigator.dameRecorrido().size() > 1)) {
            this.navigator.Retrocede();
        } else if ((op < this.navigator.damePosibles().size()) &&
                op >= 0) {
            Iterator<S> it = this.navigator.damePosibles().iterator();

            int i = 0;
            while (op > i++) {
                it.next();
            }
            S s = it.next();
            this.navigator.Avanza(s);
        }
    }

    public void inicia() {
        System.out.println("             ANIMADOR BASICO POR CONSOLA");
        System.out.println(" ===================================================");
        System.out.println(" Introduce un Numero NEGATIVO o LETRAS para SALIR");
        System.out.println(" ===================================================");
        System.out.println();
        this.printRecorrido();
        this.pintaconsola();
        this.espera();
    }

    public void espera() {
        boolean b = true;
        while (b) {
            /*if(this.navigator.damePosibles().isEmpty())
            break;*/

            this.printOpciones();



            int op = this.getOpciones();
            if (this.opcionCorrecta(op)) {
                this.aplicaOpcion(op);
            } else {
                b = false;
            }
        }

    }

    public static void main(String[] args) {        
        Laberinto lab = new Laberinto(100);
        ModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();
        Posicion pos = new Posicion(1, 1);
        LaberintoPropo prop = new LaberintoPropo(pos);
        prop.setLab(lab);
        Final fin = new Final(lab.getDim()-1, lab.getDim()-1);
        Formula nofin = new And(new Not(fin), prop);
        Formula haycamino = new EU(nofin, fin);
        Resultado<Posicion> res = m.chequear(lab, new Not(haycamino), pos);
        Navegador<Posicion> nav;
        if (res.equals(Resultado.COD_TRUE)) {
            System.out.println("La formula es cierta.");
            nav = new Navegador<Posicion>(res.getEjemplo());
        } else {
            System.out.println("La formula es falsa.");
            nav = new Navegador<Posicion>(res.getContraejemplo());
        }
        AnimadorBasico<Posicion> anim = new AnimadorBasico<Posicion>(nav);

        anim.setLaberinto(lab);

        anim.inicia();    
    }
}
