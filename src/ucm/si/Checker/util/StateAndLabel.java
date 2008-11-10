/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.util;

/**
 *
 * @author Pilar
 */
public class StateAndLabel<S>
{
    private S state;
    private String label;
    
    public StateAndLabel(S state, String label) {
        this.state = state;
        this.label = label;
    }

    public StateAndLabel() 
    {
        this.state = null;
        this.label = null;
    }
    
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public S getState() {
        return state;
    }

    public void setState(S state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StateAndLabel<S> other = (StateAndLabel<S>) obj;
        if (this.state != other.state && (this.state == null || !this.state.equals(other.state))) {
            return false;
        }
        if (this.label != other.label && (this.label == null || !this.label.equals(other.label))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 59 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }


    
    

}
