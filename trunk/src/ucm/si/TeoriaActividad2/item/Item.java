/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.item;

/**
 *
 * @author José Antonio
 */
public class Item
{
    private String clave;

    public Item(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Item()
    {
        this.clave = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if ((this.clave == null) ? (other.clave != null) : !this.clave.equals(other.clave)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.clave != null ? this.clave.hashCode() : 0);
        return hash;
    }


}
