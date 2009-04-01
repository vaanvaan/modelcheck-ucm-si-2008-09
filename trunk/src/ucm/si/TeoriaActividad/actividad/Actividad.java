/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.actividad;

import ucm.si.TeoriaActividad.item.Item;

/**
 *
 * @author José Antonio
 */
public class Actividad
{
    private Item[] itemNecesarios;
    private Item[] itemToDispose;
    private Item[] itemToGenerate;
    private Conditions[] condiciones;

    private String nombre;

    public Actividad(String nombre, Item[] itemNecesarios, Item[] itemToDispose, Item[] itemToGenerate, Conditions[] condiciones) {
        this.itemNecesarios = itemNecesarios;
        this.itemToDispose = itemToDispose;
        this.itemToGenerate = itemToGenerate;
        this.condiciones = condiciones;
        this.nombre = nombre;
    }

    public Conditions[] getCondiciones() {
        return condiciones;
    }

    public boolean CondicionesSatisfy(Contexto contx)
    {
        boolean b = true;
        int cont = 0;
        int max = this.condiciones.length;
        Conditions c;
        while (b && (cont < max) )
        {
            c = this.condiciones[cont];
            b = b && c.Cumple(contx);
            cont++;
        }
        return b;
    }

    public void setCondiciones(Conditions[] condiciones) {
        this.condiciones = condiciones;
    }

    public Item[] getItemNecesarios() {
        return itemNecesarios;
    }

    public void setItemNecesarios(Item[] itemNecesarios) {
        this.itemNecesarios = itemNecesarios;
    }

    public Item[] getItemToDispose() {
        return itemToDispose;
    }

    public void setItemToDispose(Item[] itemToDispose) {
        this.itemToDispose = itemToDispose;
    }

    public Item[] getItemToGenerate() {
        return itemToGenerate;
    }

    public void setItemToGenerate(Item[] itemToGenerate) {
        this.itemToGenerate = itemToGenerate;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean necesita(Item item){
        int i=0;
        boolean encontrado = false;
        while ((i<itemNecesarios.length)&&(!encontrado)){
            encontrado = itemNecesarios[i].equals(item);
            i++;
        }
        return encontrado;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actividad other = (Actividad) obj;
        if (this.itemNecesarios != other.itemNecesarios && (this.itemNecesarios == null || !this.itemNecesarios.equals(other.itemNecesarios))) {
            return false;
        }
        if (this.itemToDispose != other.itemToDispose && (this.itemToDispose == null || !this.itemToDispose.equals(other.itemToDispose))) {
            return false;
        }
        if (this.itemToGenerate != other.itemToGenerate && (this.itemToGenerate == null || !this.itemToGenerate.equals(other.itemToGenerate))) {
            return false;
        }
        if (this.condiciones != other.condiciones && (this.condiciones == null || !this.condiciones.equals(other.condiciones))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.itemNecesarios != null ? this.itemNecesarios.hashCode() : 0);
        hash = 37 * hash + (this.itemToDispose != null ? this.itemToDispose.hashCode() : 0);
        hash = 37 * hash + (this.itemToGenerate != null ? this.itemToGenerate.hashCode() : 0);
        hash = 37 * hash + (this.condiciones != null ? this.condiciones.hashCode() : 0);
        return hash;
    }



}
