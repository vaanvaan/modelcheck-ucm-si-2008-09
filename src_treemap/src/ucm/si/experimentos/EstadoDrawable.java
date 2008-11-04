/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.experimentos;

import ucm.si.Checker.*;

/**
 *
 * @author Pilar
 */
public abstract class EstadoDrawable<S>
{
    private S contenido; 

    @Override
    public abstract String toString();
    
    public abstract void draw(Contexto c);

    public EstadoDrawable(S contenido) {
        this.contenido = contenido;
    }

    public S getContenido() {
        return contenido;
    }

    public void setContenido(S contenido) {
        this.contenido = contenido;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoDrawable other = (EstadoDrawable) obj;
        if (this.contenido != other.contenido && (this.contenido == null || !this.contenido.equals(other.contenido))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.contenido != null ? this.contenido.hashCode() : 0);
        return hash;
    }
    
    
}
