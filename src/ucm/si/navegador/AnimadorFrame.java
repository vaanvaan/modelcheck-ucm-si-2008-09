package ucm.si.navegador;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Laberinto.Final;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.LaberintoPropo;
import ucm.si.Laberinto.Posicion;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

public class AnimadorFrame<S> extends AnimadorInterface<S> {
	private S estadoactual;
	private static Laberinto lab;
	FrameAnimador<Posicion> frame;
	public AnimadorFrame(Navegador<S> n) {
		super(n);
		super.navigator = n;
        this.estadoactual = navigator.dameInicial();
        frame = new FrameAnimador(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lab = new Laberinto(5);
		ModelChecker<Posicion> m = new DefaultModelChecker<Posicion>(lab);
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
        AnimadorFrame<Posicion> anim = new AnimadorFrame<Posicion>(nav);
	}

	@Override
	public void manejaAccion(Avanza<S> accion) {
		// TODO Auto-generated method stub
		estadoactual = accion.getEstado();
		this.printRecorrido();
		frame.setEstadoactual((Posicion) estadoactual);
		frame.rePinta();
	}

	@Override
	public void manejaAccion(GoToEstado<S> accion) {
		// TODO Auto-generated method stub
		estadoactual = accion.getEstado();
		this.printRecorrido();
	}

	@Override
	public void manejaAccion(Retrocede<S> accion) {
		// TODO Auto-generated method stub
		estadoactual = accion.getEstado();
		this.printRecorrido();
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

	public Laberinto getLab() {
		return lab;
	}
	
	public void aplicaAvanza(){
		Iterator<S> it = this.navigator.damePosibles().iterator();
		S s = it.next();
        this.navigator.Avanza(s);
	}

	

}
