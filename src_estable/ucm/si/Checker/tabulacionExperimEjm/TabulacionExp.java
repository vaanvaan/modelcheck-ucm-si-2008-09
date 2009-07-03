/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.tabulacionExperimEjm;

import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Niko, Jose Antonio, Ivan
 */
public interface TabulacionExp<S> extends TabulacionFormulas<S> 
{

    public void aniadirEjem(S s, Formula f, GrafoCaminos ej, GrafoCaminos contra);
    
    public void aniadirEjem(Indice<S> i,  GrafoCaminos ej, GrafoCaminos contra);
    
    public void aniadirEjem(S s, Formula f, EjmContrEjem ind);
    
    public GrafoCaminos getEjem (S s, Formula f);
    
    public GrafoCaminos getContraEjem (S s, Formula f);
    
    public EjmContrEjem getEjemYContra (S s, Formula f);
}