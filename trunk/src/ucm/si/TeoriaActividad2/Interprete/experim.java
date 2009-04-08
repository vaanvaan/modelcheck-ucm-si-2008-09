/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad2.Interprete;

import ucm.si.TeoriaActividad2.Interprete.*;
import java.util.ArrayList;

/**
 *
 * @author José Antonio
 */
public class experim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException
    {
       Pepe p = new Pepe();
       p.array.add("string1");
       p.array.add("string2");
       Pepe p2 = (Pepe) p.clone();
       p2.array.add("string3");
       p2.toString();
        // TODO code application logic here
    }

}

class Pepe 
{
    public ArrayList<String> array = new ArrayList<String>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        
        Object clone = null;
        try{
         clone =  super.clone();
        }
        catch(CloneNotSupportedException e)
        {}
        Pepe p = (Pepe) clone;
        p.array =  (ArrayList<String>) this.array.clone();
        return clone;
    }
    
}


