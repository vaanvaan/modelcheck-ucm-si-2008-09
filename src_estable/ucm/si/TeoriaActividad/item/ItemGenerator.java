/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.item;

import java.util.HashMap;

/**
 *
 * @author Niko, Jose Antonio, Ivan Antonio
 */
public class ItemGenerator {

    private static ItemGenerator reference;

    public static ItemGenerator getReference()
    {
        if(ItemGenerator.reference == null)
            ItemGenerator.createObject();
        return reference;
    }

    private static synchronized  void createObject()
    {
        if(ItemGenerator.reference == null)
            ItemGenerator.reference = new ItemGenerator();
    }

    private HashMap<String,Item> conjunto = new HashMap<String,Item>();

    public void addItem(Item item) throws Exception
    {
        if(this.conjunto.containsKey(item.getClave() ))
                throw new Exception("Ya Existe clave");

        this.conjunto.put(item.getClave(), item);
    }

    public Item getItem (String clave )
    {
        return this.conjunto.get(clave);
    }

    public int Elements()
    {
        return this.conjunto.size();
    }

    public boolean Contains(Item item)
    {
        return this.conjunto.containsKey(item.getClave());
    }

    public String[] getItems(){
        return this.conjunto.keySet().toArray(new String[0]);
    }

}
