/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.actividad;

import java.util.ArrayList;
import java.util.List;
import ucm.si.TeoriaActividad2.item.Item;

/**
 *
 * @author José Antonio
 */
public class Actividad extends Item
{
    private ArrayList<Item> itemNecesarios;
    private ArrayList<Item> itemToDispose;
    private ArrayList<Item> itemToGenerate;
    private ArrayList<Conditions> condiciones;
    private ArrayList<String> itemAccesibles;
    
    private Actividad padre;
    private ArrayList<Actividad> subActividades;

    //private String nombre;

    public Actividad(ArrayList<Item> itemNecesarios, ArrayList<Item> itemToDispose, ArrayList<Item> itemToGenerate, ArrayList<Conditions> condiciones, ArrayList<Actividad> subActividades) 
    {
        
        if(itemNecesarios == null)
            this.itemAccesibles = new ArrayList<String>();
        else
            this.itemNecesarios = itemNecesarios;
        
        if(itemToDispose == null)
            this.itemToDispose = new ArrayList<Item>();
        else
            this.itemToDispose = itemToDispose;
        
        if(itemToGenerate == null)
            this.itemToGenerate = new ArrayList<Item>();
        else
            this.itemToGenerate = itemToGenerate;
        
        if(condiciones == null)
            this.condiciones = new ArrayList<Conditions>();
        else
            this.condiciones = condiciones;
        
        if(subActividades == null)
            this.subActividades = new ArrayList<Actividad>();
        else
            this.subActividades = subActividades;
       
    }





    /**
     * Devuelve los item que comparte con las activiadaes padre
     * @return
     */
    public ArrayList<String> getItemAccesibles() {
        return itemAccesibles;
    }

    /*public void setItemAccesibles()
    {

        this.itemAccesibles = (ArrayList<String>) this.availableArtifacts();
    }*/

    public Conditions[] getArrayCondiciones() {
        return (Conditions[]) condiciones.toArray();
    }

    public boolean CondicionesSatisfy(Contexto contx)
    {
        boolean b = true;
        int cont = 0;
        int max = this.condiciones.size();
        Conditions c;
        while (b && (cont < max) )
        {
            c = this.condiciones.get(cont);
            b = b && c.Cumple(contx);
            cont++;
        }
        return b;
    }

    public ArrayList<Conditions> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(ArrayList<Conditions> condiciones) {
        this.condiciones = condiciones;
    }

    public ArrayList<Item> getItemNecesarios() {
        return itemNecesarios;
    }

    public void setItemNecesarios(ArrayList<Item> itemNecesarios) {
        this.itemNecesarios = itemNecesarios;
    }

    public ArrayList<Item> getItemToDispose() {
        return itemToDispose;
    }

    public void setItemToDispose(ArrayList<Item> itemToDispose) {
        this.itemToDispose = itemToDispose;
    }

    public ArrayList<Item> getItemToGenerate() {
        return itemToGenerate;
    }

    public void setItemToGenerate(ArrayList<Item> itemToGenerate) {
        this.itemToGenerate = itemToGenerate;
    }

    public String getNombre() {
        return super.getClave();
    }

    public void setNombre(String nombre) {
        super.setClave(nombre);
    }

    public Actividad getPadre() {
        return padre;
    }

    public void setPadre(Actividad padre) {
        this.padre = padre;
    }

    public ArrayList<Actividad> getSubActividades() {
        return subActividades;
    }

    public void setSubActividades(ArrayList<Actividad> subActividades) {
        this.subActividades = subActividades;
    }

    /**
     * Calcula los artefactos (Item) que serán accesibles desde la actividad a que comparten con los padres
     * @return
     */
    public void setAvailableArtifacts()
    {
        ArrayList<String> l = null;
        if(padre != null)
            l = padre.getAvailableArtifacts();
        else
            l = new ArrayList<String>();
        this.itemAccesibles =  l;
        //return this._availableArtifacts();
    }

    public ArrayList<String> getAvailableArtifacts()
    {
        ArrayList<String> lista = null;
        if(padre == null)
        {
            lista = new ArrayList<String>();
        }
        else
        {
            lista = padre.getItemAccesibles();
        }
        for (Item i : this.itemNecesarios)
        {
            String clave = i.getClave();
            // Si no contiene la clave significa que podmeos añadir el nuevo item
            if (!lista.contains(clave))
            {
                lista.add(clave);
            }
        }
        return lista;
    }


    /**
     *
     * @param l Se encarga de
     * @return
     */
    private List<String> _availableArtifacts()
    {
        List<String> lista = null;
        if(this.padre != null)
        {
            lista = this.padre.getItemAccesibles();
            for(Item i: this.itemNecesarios )
            {
                String clave = i.getClave();
                if(!lista.contains(clave))
                    lista.add(clave);
            }
        }
        else 
        {
            lista = new ArrayList<String>();
            for(Item i: this.itemNecesarios )
            {
                String clave = i.getClave();
                // Si no contiene la clave significa que podmeos añadir el nuevo item
                if(!lista.contains(clave) )
                    lista.add(clave );
            }
        }
        return lista;
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
        if (this.itemAccesibles != other.itemAccesibles && (this.itemAccesibles == null || !this.itemAccesibles.equals(other.itemAccesibles))) {
            return false;
        }
        if (this.padre != other.padre && (this.padre == null || !this.padre.equals(other.padre))) {
            return false;
        }
        if (this.subActividades != other.subActividades && (this.subActividades == null || !this.subActividades.equals(other.subActividades))) {
            return false;
        }
        // apaño introducido a mano para controlar el nombre y que funcione bien el equals
        if(this.getNombre() != other.getNombre() && (this.getNombre() == null || !this.getNombre().equals(other.getNombre())))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.itemNecesarios != null ? this.itemNecesarios.hashCode() : 0);
        hash = 29 * hash + (this.itemToDispose != null ? this.itemToDispose.hashCode() : 0);
        hash = 29 * hash + (this.itemToGenerate != null ? this.itemToGenerate.hashCode() : 0);
        hash = 29 * hash + (this.condiciones != null ? this.condiciones.hashCode() : 0);
        hash = 29 * hash + (this.itemAccesibles != null ? this.itemAccesibles.hashCode() : 0);
        hash = 29 * hash + (this.padre != null ? this.padre.hashCode() : 0);
        hash = 29 * hash + (this.subActividades != null ? this.subActividades.hashCode() : 0);
        // apaño incluido manualmente para el hashcode
        hash = 29 * hash + (this.getNombre() != null ? this.getNombre().hashCode(): 0);
        return hash;
    }


    

    

}

   