/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.navegador;


import ucm.si.Checker.*;
import ucm.si.navegador.AccionEnum;
import java.util.Observable;
import java.util.Observer;
import ucm.si.navegador.Listener.AccionListener;

/**
 * Clase que funciona como interfaz. Esta claase implementa un patron oyente con el navegador.
 * Esta interfaz se encarga de avisar a su implementacion de los cambios realizados en el navegador, y dar se alta como oyente en el navegador.
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
 */
public abstract class AnimadorInterface<S> implements AccionListener<S>
{
    protected Navegador<S> navigator;
    
    public Navegador<S> getNavigator() {
		return navigator;
	}

	public void setNavigator(Navegador<S> navigator) {
                this.navigator.removeOyente(this);
		this.navigator = navigator;
                this.navigator.addOyente(this);
	}
    
        public AnimadorInterface(Navegador<S> n) 
        {
            this.navigator = n;
            this.navigator.addOyente(this);
	}

    //  metodos abstractor propios del animador los metodos para manejar los eventos los hereda 
    //    de AccionListener, ya se implementaran en la clase final
    
}
