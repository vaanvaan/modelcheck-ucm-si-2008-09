/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.navegador;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ucm.si.Checker.Estado;
import ucm.si.navegador.events.Avanza;
import ucm.si.navegador.events.GoToEstado;
import ucm.si.navegador.events.Retrocede;
import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Pilar
 */
public class Navegador extends NavigatorInterface {

    private GrafoCaminos contraEj;
    private GrafoCaminos ejemplo;
    private Stack<Estado> recorrido;

    public void GoToEstado(Estado e) {
        synchronized (this) {
            int pos = this.recorrido.search(e);
            if (pos >= 0) {
                this.recorrido.retainAll(this.recorrido.subList(0, pos));
            } else // significa que el estado no sta en la pila y se queire partir de uno a cero
            {
                this.recorrido = null;
                this.recorrido = new Stack<Estado>();
                this.recorrido.push(e);
            }
        }

        super.notificarOyentes(new GoToEstado(this, e));


    }
    public  void Avanza(Estado e)
    {
        synchronized(this)   
        {
            this.recorrido.push(e);
        }
        
        super.notificarOyentes(new Avanza(this, e));
        
    }
    
    public void Retrocede()
    {
        Estado e = null;
        synchronized(this)
        {
            e = this.recorrido.pop();
        }
        
        super.notificarOyentes(new Retrocede(this, e));
    }

    @Override
    public List damePosibles() {
        Estado e = this.recorrido.peek();
        if(e == null)
        {
            List l = new ArrayList();
            l.add(this.contraEj.getInicio());
            return l;
        }
        return this.contraEj.getHijos(e);
    }
    
    
    
    

}
