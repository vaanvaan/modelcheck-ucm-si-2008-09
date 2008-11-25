package ucm.si.animadorGUI.laberinto;

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

public class AnimadorGrafico<S> extends AnimadorInterface<S> 
{
	private S estadoactual;
	private static Laberinto lab;
	FrameAnimador<S> frame;
	
        public AnimadorGrafico(Navegador<S> n, Drawer<S> dw, Contexto cntxt) 
        {
        	super(n); 
            
            this.estadoactual = navigator.dameInicial();
            this.frame = new FrameAnimador<S>(this, dw, cntxt);
            //this.frame.setDrawer(dw);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lab = new Laberinto(20);
	DefaultModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();
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
            nav = new Navegador<Posicion>(res.getEjemplo(), m.getRoseta());
        } else {
            System.out.println("La formula es falsa.");
            nav = new Navegador<Posicion>(res.getContraejemplo(), m.getRoseta());
        }
        ContextoLaberinto contxt = new ContextoLaberinto();
        contxt.setLab(lab);
        AnimadorGrafico<Posicion> anim = new AnimadorGrafico<Posicion>(nav, new DrawerLaberinto(), contxt);
	}

        public void setDrawer(Drawer<S> dw)
        {
            this.frame.setDrawer(dw);
        }
        
	
	public void manejaAccion(Avanza<S> accion) {
		// TODO Auto-generated method stub
		estadoactual = accion.getEstado();
		this.printRecorrido();
		frame.setEstadoactual(estadoactual);
		frame.rePinta();
	}

	
	public void manejaAccion(GoToEstado<S> accion) {
		// TODO Auto-generated method stub
		estadoactual = accion.getEstado();
		this.printRecorrido();
	}

	
	public void manejaAccion(Retrocede<S> accion) {
		// TODO Auto-generated method stub
		if(accion.getEstado()!=null)
		estadoactual = accion.getEstado();
		this.printRecorrido();
		frame.setEstadoactual(estadoactual);
		frame.rePinta();
	}
	
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
	
	public void aplicaAvanza(S s){
		Iterator<StateAndLabel<S>> it;
		try {
			it = this.navigator.damePosibles().iterator();

			while (it.hasNext()) {
				if (it.next().getState().equals(s))
					this.navigator.Avanza(s);
				else {
					// lenzar excepcion
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void aplicaRetrocede(){
		this.navigator.Retrocede();
	}
	
	public void aplicaGoTo(S s){
		this.navigator.GoToEstado(s);
		
	}

}
