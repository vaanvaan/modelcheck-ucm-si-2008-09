package ucm.si.animadorGUI;

import ucm.si.util.Contexto;
import java.util.Iterator;
import java.util.List;


import ucm.si.Checker.util.StateAndLabel;
import ucm.si.navegador.AnimadorInterface;
import ucm.si.navegador.Navegador;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;

public class AnimadorGrafico<S> extends AnimadorInterface<S> 
{
	private S estadoactual;
	FrameAnimador<S> frame;
    public static final int BOTONERA_CLASICA = 0;
    public static final int BOTONERA_COMBO = 1;
	
        public AnimadorGrafico(Navegador<S> n, Drawer<S> dw, Contexto cntxt, int tipoBotonera)
        {
        	super(n); 
            
            this.estadoactual = navigator.dameInicial();
            this.frame = new FrameAnimador<S>(this, dw, cntxt,tipoBotonera);
            //this.frame.setDrawer(dw);
	}

	

        public void setDrawer(Drawer<S> dw)
        {
            this.frame.setDrawer(dw);
        }
        
	
	public void manejaAccion(Avanza<S> accion) {
		
		estadoactual = accion.getEstado();
		//this.printRecorrido();
		frame.setEstadoactual(estadoactual);
		frame.rePinta();
	}

	
	public void manejaAccion(GoToEstado<S> accion) {
		
		estadoactual = accion.getEstado();
		//this.printRecorrido();
	}

	
	public void manejaAccion(Retrocede<S> accion) {
		
		if(accion.getEstado()!=null)
		estadoactual = accion.getEstado();
		//this.printRecorrido();
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
