/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.util;

import java.util.List;

/**
 *
 * @author Pilar
 */
public class StateLabeledList<S> 
{
    private List<S> listaEstados;
    private List<String> listaEtiquetas;

    public StateLabeledList(List<S> listaEstados, List<String> listaEtiquetas) {
        this.listaEstados = listaEstados;
        this.listaEtiquetas = listaEtiquetas;
    }

    
    
    public List<S> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<S> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<String> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<String> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }
    
    

}
