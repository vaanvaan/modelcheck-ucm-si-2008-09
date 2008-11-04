/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacionExperimEjm;

import ucm.si.basico.ecuaciones.Formula;

/**
 *
 * @author Pilar
 */
public class Indice <S>
{
    private S estado;
    private Formula formula;

    public Indice(S estado, Formula formula) {
        this.estado = estado;
        this.formula = formula;
    }

    public S getEstado() {
        return estado;
    }

    public void setEstado(S estado) {
        this.estado = estado;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Indice<S> other = (Indice<S>) obj;
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.formula != other.formula && (this.formula == null || !this.formula.equals(other.formula))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 41 * hash + (this.formula != null ? this.formula.hashCode() : 0);
        return hash;
    }
    
    

}
