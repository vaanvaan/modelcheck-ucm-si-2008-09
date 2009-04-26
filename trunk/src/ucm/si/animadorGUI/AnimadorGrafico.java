package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.util.StateAndLabel;
import ucm.si.Laberinto.Final;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.LaberintoPropo;
import ucm.si.Laberinto.Posicion;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.navegador.AnimadorInterface;
import ucm.si.navegador.Navegador;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

/**
 * Es una clase que se encarga de escuchar las operaciones que realiza el navegador
 * y de ahi controlar las acciones del FrameAnimador.
 * Ya que FrameAnimador es el encargado de mostrar la parte grafica.
 * @author José Antonio
 * @param <S> Tipo del estado sobre el cual se va a parametrizar el objeto.
 */
public class AnimadorGrafico<S> extends AnimadorInterface<S> 
{
	private S estadoactual;
	private static Laberinto lab;
	FrameAnimador<S> frame;

    /**
     * Constructor por defecto requiere de un navegador, un drawer y opcionalmente de un contexto
     * @param n
     * @param dw
     * @param cntxt
     */
        public AnimadorGrafico(Navegador<S> n, Drawer<S> dw, Contexto cntxt) 
        {
        	super(n); 
            
            this.estadoactual = navigator.dameInicial();
            this.frame = new FrameAnimador<S>(this, dw, cntxt);
            //this.frame.setDrawer(dw);
	}

	
        /**
        * Invoca la operacion para modificar el drawer asignado para dibujar(representar) un estado
        * @param dw
        */
        public void setDrawer(Drawer<S> dw)
        {
            this.frame.setDrawer(dw);
        }
        
	/**
     * Maneja una accion del tipo avanza notificada por el Navegador
     * @param accion
     */
	public void manejaAccion(Avanza<S> accion) {

        S estadoViejo = estadoactual;
		estadoactual = accion.getEstado();
		//this.printRecorrido();
        frame.realizaAccion(FrameAnimador.Accion.Avance, estadoViejo ,estadoactual);
		/*frame.setEstadoactual(estadoactual);
		frame.rePinta();*/
	}

	/**
     * Maneja una accion del tipo Go TO (ir a..) notificada por el Navegador
     * @param accion
     */
	public void manejaAccion(GoToEstado<S> accion) {
		 S estadoViejo = estadoactual;
		estadoactual = accion.getEstado();
        frame.realizaAccion(FrameAnimador.Accion.GoTo, estadoViejo, estadoactual);
		//this.printRecorrido();
	}

	/**
     * Maneja una accion del tipo Retrocede notificada por el Navegador
     * @param accion
     */
	public void manejaAccion(Retrocede<S> accion) {

        S estadoViejo = null;
		if(accion.getEstado()!=null)
        {
            estadoViejo = estadoactual;
            estadoactual = accion.getEstado();

        }//this.printRecorrido();
        frame.realizaAccion(FrameAnimador.Accion.Return, estadoViejo, estadoactual);
		/*frame.setEstadoactual(estadoactual);
		frame.rePinta();*/
	}

    /**
     * Escribe por consola el recorrido que se ha hecho hasta ahora
     */
	public void printRecorrido() {
        List<S> l = this.navigator.dameRecorrido();

        for (int i = 0; i < l.size(); i++) //for (int i = l.size(); i < 0; i--)
        {
            S s = l.get(i);
            System.out.println("Paso " + i + " : " + s.toString());

        }

    }

	public S getEstadoactual() {
		return estadoactual;
	}

	public void setEstadoactual(S estadoactual) {
		this.estadoactual = estadoactual;
	}

	/*public Laberinto getLab() {
		return lab;
	}*/
	
	public void aplicaAvanza(S s)
        {
            //if (s != null)
            {   
		Iterator<StateAndLabel<S>> it;
		try {
			it = this.navigator.damePosibles().iterator();

			while (it.hasNext()) {
				if (it.next().getState().equals(s))
					this.navigator.Avanza(s);
				else {
					// lanzar excepcion
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
            }
	}

	public void aplicaRetrocede(){
		this.navigator.Retrocede();
	}
	
	public void aplicaGoTo(S s){
		this.navigator.GoToEstado(s);
		
	}
        
        /**
	 * @param args
	 */
	/*   public static void main(String[] args) {
        
        lab = new Laberinto(20);
        DefaultModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();
        Posicion pos = new Posicion(1, 1);
        LaberintoPropo prop = new LaberintoPropo(pos);
        prop.setLab(lab);
        Final fin = new Final(lab.getDim() - 1, lab.getDim() - 1);
        Formula nofin = new And(new Not(fin), prop);
        Formula haycamino = new EU(nofin, fin);
        Resultado<Posicion> res = m.chequear(lab, new Not(haycamino), pos);
        Navegador<Posicion> nav;
        if (res.equals(Resultado.COD_TRUE)) {
            System.out.println("La formula es cierta.");
            nav = new Navegador<Posicion>(res.getEjemplo(), m.getRoseta());
        } else {
            System.out.println("La formula es falsa.");
            nav = new Navegador<Posicion>(res.getContraejemplo(), m.getRoseta());
        }
        ContextoLaberinto contxt = new ContextoLaberinto();
        contxt.setLab(lab);
        AnimadorGrafico<Posicion> anim = new AnimadorGrafico<Posicion>(nav, new DrawerLaberinto(), contxt);
    }*/

}
