/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad2.actividad;

//import com.sun.xml.internal.ws.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author José Antonio
 */
public class ActividadGenerator {

    private static ActividadGenerator reference;

    public static ActividadGenerator getReference() {
        if (ActividadGenerator.reference == null) {
            ActividadGenerator.createObject();
        }
        return reference;
    }

    private synchronized static void createObject() {
        if (ActividadGenerator.reference == null) {
            ActividadGenerator.reference = new ActividadGenerator();
        }
    }
    private HashMap<String, Actividad> conjunto;

    public ActividadGenerator() {
        this.conjunto = new HashMap<String, Actividad>();
    }

    public HashMap<String, Actividad> getConjunto() {
        return conjunto;
    }

    public void setConjunto(HashMap<String, Actividad> conjunto) {
        this.conjunto = conjunto;
    }

    @Deprecated
    /**
     * Preferiblemente no usar esta funcion y usar addActividadSimple
     */
    public void addActividad(Actividad actividad) throws Exception {
        if (this.conjunto.containsKey(actividad.getNombre())) {
            throw new Exception("Ya Existe clave");
        }

        this.conjunto.put(actividad.getNombre(), actividad);
        actividad.setAvailableArtifacts();
    }

    /**
     * Añade una actividad al Generador sin padre
     * @param actividad
     * @param nombreActividad
     */
    public void addActividadSimple(Actividad actividad, String nombreActividad) {
        this.addActividadSimple(actividad, nombreActividad, null);
    }

    /**
     * Si introduces en padre null, significa que no tiene padre, es importante añadir el campo nombreactiviada y que no sea null ni empty
     * @param actividad
     * @param nombreActividad
     * @param nombreActividadPadre
     */
    public void addActividadSimple(Actividad actividad, String nombreActividad, String nombreActividadPadre) {
        if (this.conjunto.containsKey(nombreActividad))
        {
            return;
        }
        else
        {
            if (nombreActividadPadre == null || nombreActividadPadre.isEmpty())
            {
                actividad.setNombre(nombreActividad);
                actividad.setPadre(null);
            }
            else
            {
                actividad.setNombre(nombreActividad);
                Actividad padre = this.conjunto.get(nombreActividadPadre);
                if (padre != null) {
                    actividad.setNombre(nombreActividad);
                    actividad.setPadre(padre);
                    padre.getSubActividades().add(actividad);
                // error dos activiades con el mismo nombre.

                }
                else
                {
                    return;
                    // El padre no existe se deberia lanzar una excepcion
                }
            }
            // generamos la lista que nos guia para saber que items van a estar accesibles para nosotros
            actividad.setAvailableArtifacts();
            // añadimos la actividad al conjunto de actividades almacenadas en el generador
            this.conjunto.put(nombreActividad, actividad);
        }
    }

    public Actividad getActividad(String clave) {
        return this.conjunto.get(clave);
    }

    public int Elements() {
        return this.conjunto.size();
    }

    public boolean Contains(Actividad actividad) {
        return this.conjunto.containsKey(actividad.getNombre());
    }
}
