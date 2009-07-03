/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacionExperimEjm;

import java.util.Set;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.tabulacion.*;
import java.util.HashMap;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public class TabulacionMemSistemaconEjemployContraEjemplo<S> implements TabulacionExp<S>
{
    private TabulacionMemSistema<S> tabla;
    private HashMap<Indice<S>, EjmContrEjem> tablaEjem;

    public TabulacionMemSistemaconEjemployContraEjemplo(TabulacionMemSistema<S> tabla, HashMap<Indice<S>, EjmContrEjem> tablaEjem) {
        this.tabla = tabla;
        this.tablaEjem = tablaEjem;
    }

    public TabulacionMemSistemaconEjemployContraEjemplo() {
    }
        
        
    

    public boolean tieneEtiqueta(S estado, Formula formula) {
        return tabla.tieneEtiqueta(estado, formula);
    }

    public boolean tieneEstado(S estado) {
        return tabla.tieneEstado(estado);
    }

    

    public Set<Formula> getEtiquetas(S estado) {
        return tabla.getEtiquetas(estado);
    }

       
    
    // Metodos estandar para añadir ejemplos y contraEjemplos a la tabla de tabulacion
    public void aniadirEjem(S s, Formula f, GrafoCaminos ej, GrafoCaminos contra)
    {
        this.tablaEjem.put(new Indice<S>(s, f), new EjmContrEjem(ej, contra));
    }
    
    public void aniadirEjem(Indice<S> i,  GrafoCaminos ej, GrafoCaminos contra)
    {
        this.tablaEjem.put(i, new EjmContrEjem(ej, contra));
    }
    
    public void aniadirEjem(S s, Formula f, EjmContrEjem ind)
    {
        this.tablaEjem.put(new Indice<S>(s, f), ind);
    }
    
    public GrafoCaminos getEjem (S s, Formula f)
    {
        return this.tablaEjem.get(new Indice<S>(s,f)).getEjemplo();
    }
    
    public GrafoCaminos getContraEjem (S s, Formula f)
    {
        return this.tablaEjem.get(new Indice<S>(s,f)).getContraEjemplo();
    }
    
    public EjmContrEjem getEjemYContra (S s, Formula f)
    {
        return this.tablaEjem.get(new Indice<S> (s,f));
    }
    
    
    
        
    public TabulacionMemSistema<S> getTabla() {
        return tabla;
    }

    public void setTabla(TabulacionMemSistema<S> tabla) {
        this.tabla = tabla;
    }

    public HashMap<Indice<S>, EjmContrEjem> getTablaEjem() {
        return tablaEjem;
    }

    public void setTablaEjem(HashMap<Indice<S>, EjmContrEjem> tablaEjem) {
        this.tablaEjem = tablaEjem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TabulacionMemSistemaconEjemployContraEjemplo<S> other = (TabulacionMemSistemaconEjemployContraEjemplo<S>) obj;
        if (this.tabla != other.tabla && (this.tabla == null || !this.tabla.equals(other.tabla))) {
            return false;
        }
        if (this.tablaEjem != other.tablaEjem && (this.tablaEjem == null || !this.tablaEjem.equals(other.tablaEjem))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.tabla != null ? this.tabla.hashCode() : 0);
        hash = 89 * hash + (this.tablaEjem != null ? this.tablaEjem.hashCode() : 0);
        return hash;
    }

    public void aniadirEtiqueta(S estado, Formula formula, Resultado r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Resultado getResultado(S estado, Formula formula) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    
    

}
