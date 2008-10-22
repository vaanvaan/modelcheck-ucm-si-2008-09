/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ucm.si.Checker.Estado;

/**
 *
 * @author Pilar
 */
public class TLG 
{
    private HashMap<Estado,ArrayList<Estado>> tabla;
    
    public TLG()
    {
        this.tabla = new HashMap<Estado, ArrayList<Estado>>();
    }
    
    public TLG(int tam)
    {
        this.tabla = new HashMap<Estado, ArrayList<Estado>>(tam);
    }
    
    
    
    public List getHijo (Estado e)
    {
        return this.tabla.get(e);
    }
    
    public void setArista (Estado eini, Estado efin) 
    {
        if(this.tabla.containsKey(eini))
        {
            this.tabla.get(eini).add(efin);
        }
        else
        {
            ArrayList<Estado> l = new ArrayList<Estado>();
            l.add(efin);
            this.tabla.put(eini,l);
            if(!this.tabla.containsKey(efin))
            {
                l = new ArrayList<Estado>();
                this.tabla.put(efin,l);
            }
        }
    }

    public HashMap<Estado, ArrayList<Estado>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<Estado, ArrayList<Estado>> tabla) {
        this.tabla = tabla;
    }

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
