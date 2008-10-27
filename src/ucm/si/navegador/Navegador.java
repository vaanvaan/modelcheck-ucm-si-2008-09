/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.navegador;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;
import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Pilar
 */
public class Navegador<S> extends NavigatorInterface<S> {

    private GrafoCaminos<S> contraEj;
    private GrafoCaminos<S> ejemplo;
    private Stack<S> recorrido;

    public GrafoCaminos<S> getContraEj() {
        return contraEj;
    }

    public void setContraEj(GrafoCaminos<S> contraEj) {
        this.contraEj = contraEj;
    }

    public GrafoCaminos<S> getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(GrafoCaminos<S> ejemplo) {
        this.ejemplo = ejemplo;
    }

    public Navegador(GrafoCaminos<S> contraEj, GrafoCaminos<S> ejemplo) {
        this.contraEj = contraEj;
        this.ejemplo = ejemplo;
        this.recorrido = new Stack<S>();
        this.recorrido.push(this.contraEj.getInicio());
    }

    
    @Override
    public void GoToEstado(S e) {
        synchronized (this) {
            int pos = this.recorrido.search(e);
            if (pos >= 0) {
                this.recorrido.retainAll(this.recorrido.subList(0, pos));
            } else // significa que el estado no sta en la pila y se queire partir de uno a cero
            {
                this.recorrido = null;
                this.recorrido = new Stack<S>();
                this.recorrido.push(e);
            }
        }

        super.notificarOyentes(new GoToEstado(this, e));
    }
    
    @Override
    public  void Avanza(S e)
    {
        synchronized(this)   
        {
            this.recorrido.push(e);
        }
        
        super.notificarOyentes(new Avanza(this, e));
        
    }
    
    @Override
    public void Retrocede()
    {
        S e = null;
        synchronized(this)
        {
            this.recorrido.pop();
            e = this.recorrido.peek();
        }
        
        super.notificarOyentes(new Retrocede(this, e));
    }

    @Override
    public List<S> damePosibles() {
        S e = this.recorrido.peek();
        if(e == null)
        {
            List l = new ArrayList();
            l.add(this.contraEj.getInicio());
            return l;
        }
        return this.contraEj.getHijos(e);
    }

    @Override
    public S dameInicial() {
        return this.contraEj.getInicio();
    }

    @Override
    public List<S> dameRecorrido() {
        return this.recorrido;
    }
    
    
    
    

}
