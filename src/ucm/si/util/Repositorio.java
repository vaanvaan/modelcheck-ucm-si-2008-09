/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.util;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Pilar
 */
public class Repositorio 
{
    // Parte para hacer la clase singleton...
    // Singleton-> solo existe una clase en todo el sistema (o mejor dicho en la maquina virtual de java)
    // y esta es accesible desde cualqueir obeto mediante la funcion Repositorio.getInstance()
    
    private static Repositorio REFERENCIA = null;
    
    public static Repositorio getInstance()
    {
        if( REFERENCIA == null)
            createInstance();
        return REFERENCIA;
    }
    
    private static synchronized void createInstance()
    {
        if(REFERENCIA == null)
           REFERENCIA = new Repositorio();
    }
    
    private HashMap<String, Object> repositorio;

    public Repositorio(HashMap<String, Object> repositorio) {
        this.repositorio = repositorio;
    }

    public Repositorio() 
    {
    }
    
    public void setAtributo (String s, Object o)
    {
        this.repositorio.put(s, o);
    }
    
    public Object getAtributo(String s)
    {
        return this.repositorio.get(s);
    }
    
    public boolean existeAtributo(String s)
    {
        return this.repositorio.containsKey(s);
    }
    
    
    public boolean existeObjeto(Object o)
    {
        return this.repositorio.containsValue(o);
    }
    
    /**
    * Indica si el repositorio de atributos esta vacio
    *
    */
    public boolean vacia()
    {
        return this.repositorio.isEmpty();
       
    }
    /**
    * Vacia el repositorio de atributos
    *.
    */
    public void vaciar()
    {
        this.repositorio.clear();
    }
    
    /**
    * Devuelve un Set de todos los atributos almacenados en el repositorio
    */
    public Set<String> Atributos()
    {
        return this.repositorio.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Repositorio other = (Repositorio) obj;
        if (this.repositorio != other.repositorio && (this.repositorio == null || !this.repositorio.equals(other.repositorio))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.repositorio != null ? this.repositorio.hashCode() : 0);
        return hash;
    }
    
    
    
}
