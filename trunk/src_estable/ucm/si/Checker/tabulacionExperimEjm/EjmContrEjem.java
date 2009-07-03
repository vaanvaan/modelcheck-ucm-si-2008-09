/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacionExperimEjm;

import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class EjmContrEjem 
{
    private GrafoCaminos ejemplo;
    private GrafoCaminos contraEjemplo;

    public EjmContrEjem(GrafoCaminos ejemplo, GrafoCaminos contraEjemplo) {
        this.ejemplo = ejemplo;
        this.contraEjemplo = contraEjemplo;
    }

    public GrafoCaminos getContraEjemplo() {
        return contraEjemplo;
    }

    public void setContraEjemplo(GrafoCaminos contraEjemplo) {
        this.contraEjemplo = contraEjemplo;
    }

    public GrafoCaminos getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(GrafoCaminos ejemplo) {
        this.ejemplo = ejemplo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EjmContrEjem other = (EjmContrEjem) obj;
        if (this.ejemplo != other.ejemplo && (this.ejemplo == null || !this.ejemplo.equals(other.ejemplo))) {
            return false;
        }
        if (this.contraEjemplo != other.contraEjemplo && (this.contraEjemplo == null || !this.contraEjemplo.equals(other.contraEjemplo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.ejemplo != null ? this.ejemplo.hashCode() : 0);
        hash = 97 * hash + (this.contraEjemplo != null ? this.contraEjemplo.hashCode() : 0);
        return hash;
    }

}
