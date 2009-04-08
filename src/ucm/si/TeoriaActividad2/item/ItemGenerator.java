/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.item;

import java.util.HashMap;

/**
 *
 * @author José Antonio
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

    private HashMap<String,Item> conjunto;

    public ItemGenerator()
    {
        this.conjunto = new HashMap<String, Item>();
    }


    public void addItem(Item item)
    {
        if(!this.conjunto.containsKey(item.getClave() ))
               // throw new Exception("Ya Existe clave");
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

    public HashMap<String, Item> getConjunto() {
        return conjunto;
    }

    public void setConjunto(HashMap<String, Item> conjunto) {
        this.conjunto = conjunto;
    }


}
