/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pilar
 */
public class TLG<S> 
{
    private HashMap<S,ArrayList<S>> tabla;
    
    public TLG()
    {
        this.tabla = new HashMap<S, ArrayList<S>>();
    }
    
    public TLG(int tam)
    {
        this.tabla = new HashMap<S, ArrayList<S>>(tam);
    }
    
    
    
    public List<S> getHijo (S e)
    {
        return this.tabla.get(e);
    }
    
    public void setArista (S eini, S efin) 
    {
        if(this.tabla.containsKey(eini))
        {
            this.tabla.get(eini).add(efin);
        }
        else
        {
            ArrayList<S> l = new ArrayList<S>();
            l.add(efin);
            this.tabla.put(eini,l);
            if(!this.tabla.containsKey(efin))
            {
                l = new ArrayList<S>();
                this.tabla.put(efin,l);
            }
        }
    }

    public HashMap<S, ArrayList<S>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<S, ArrayList<S>> tabla) {
        this.tabla = tabla;
    }
    
    // apaños para el chache
    /*public void removeArista()
    {}*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TLG other = (TLG) obj;
        if (this.tabla != other.tabla && (this.tabla == null || !this.tabla.equals(other.tabla))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.tabla != null ? this.tabla.hashCode() : 0);
        return hash;
    }
    
    
}
