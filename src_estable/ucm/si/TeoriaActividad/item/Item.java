/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.item;

import ucm.si.TeoriaActividad.actividad.Actividad;

/**
 *
 * @author José Antonio
 */
public class Item implements Comparable<Item>
{
    private String clave;
    private Actividad propietaria;
    
    public Item(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setPropietaria(Actividad a) {
        this.propietaria = a;
    }

    public Actividad getPropietaria() {
        return propietaria;
    }

    public int compareTo(Item arg0) {
        return this.clave.compareToIgnoreCase(arg0.clave);
    }


}
