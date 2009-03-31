/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.actividad;

import java.util.HashMap;

/**
 *
 * @author José Antonio
 */
public class ActividadGenerator
{
    private static ActividadGenerator reference;

    public static ActividadGenerator getReference()
    {
        if(ActividadGenerator.reference == null)
            ActividadGenerator.createObject();
        return reference;
    }

    private synchronized static void createObject()
    {
        if(ActividadGenerator.reference == null)
            ActividadGenerator.reference = new ActividadGenerator();
    }


    private HashMap<String,Actividad> conjunto;

    public HashMap<String, Actividad> getConjunto() {
        return conjunto;
    }

    
    public void setConjunto(HashMap<String, Actividad> conjunto) {
        this.conjunto = conjunto;
    }

    public void addActividad(Actividad actividad) throws Exception
    {
        if(this.conjunto.containsKey(actividad.getNombre() ))
                throw new Exception("Ya Existe clave");

        this.conjunto.put(actividad.getNombre(), actividad);
    }

    public Actividad getItem (String clave )
    {
        return this.conjunto.get(clave);
    }

    public int Elements()
    {
        return this.conjunto.size();
    }

    public boolean Contains(Actividad actividad)
    {
        return this.conjunto.containsKey(actividad.getNombre());
    }

}
