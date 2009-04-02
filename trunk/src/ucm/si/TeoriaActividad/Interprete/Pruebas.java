/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.util.StateLabeledList;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class Pruebas implements Interprete<EstadoTA>
{
    public ItemGenerator itemGen;
    public ActividadGenerator activGen;
    public HashMap<Integer, Set<String>> conjuntosItems;
    public HashMap<Integer, Set<Actividad>> conjuntosActividades;
    public String[] actividades;
    public String[] items;
    
    public Pruebas(){
        Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        Item item4 = new Item("4");
        Item item5 = new Item("5");
        Item item6 = new Item("6");        
        itemGen = ItemGenerator.getReference();
        Item[] listaItem1 = {item1, item2, item3};
        Item[] listaItem2 = {item1, item2, item4};
        Item[] listaItem3 = {item1, item3, item5};
        
        Actividad actividad1 = new Actividad("A1", listaItem1, null, null, null);
        Actividad actividad2 = new Actividad("A2", listaItem2, null, null, null);
        Actividad actividad3 = new Actividad("A3", listaItem3, null, null, null);
        activGen = ActividadGenerator.getReference();
        try{
        itemGen.addItem(item1);
        itemGen.addItem(item2);
        itemGen.addItem(item3);
        itemGen.addItem(item4);
        itemGen.addItem(item5);
        itemGen.addItem(item6);
        activGen.addActividad(actividad1);
        activGen.addActividad(actividad2);
        activGen.addActividad(actividad3);
        
        
        // Creamos la tabla para particionar los items en subconjuntos
        // por ahora hacemos para 32 actividades, pero es facil hacerlo generico
        
        String[] lItems = itemGen.getItems();
        int[] lClaves = new int[lItems.length];
        Actividad[] lacts = activGen.getConjunto().values().
                toArray(new Actividad[activGen.Elements()]);
        for (int j = 0; j < lItems.length; j++){
            if (lacts[0].necesita(itemGen.getItem(lItems[j]))){
                lClaves[j] = 1;
            } else {
                lClaves[j] = 0;
            }
        }
        int pot2 = 2;
        for (int i = 1; i < lacts.length; i++){
            for (int j =0; j < lItems.length; j++){
                if (lacts[i].necesita(itemGen.getItem(lItems[j])))
                    lClaves[j] = lClaves[j] + pot2;
            }
            pot2 = pot2 << 1;
        }
        conjuntosItems = new HashMap<Integer,Set<String>>();
        for (int i=0; i < lClaves.length; i++){
            if (!conjuntosItems.containsKey(new Integer(lClaves[i]))){
                TreeSet<String> saux = new TreeSet<String>();
                saux.add(lItems[i]);                
                conjuntosItems.put(new Integer(lClaves[i]), saux);
            } else {
                conjuntosItems.get(lClaves[i]).add(lItems[i]);
            }
        }
        
        // Ahora nos quedamos solo con los conjuntos conflictivos
        if (conjuntosItems.containsKey(new Integer(0))){
                conjuntosItems.remove(new Integer(0));
        }
        pot2 = 1;
        for (int i = 0; i < lacts.length; i++) {
            Integer clave = new Integer(pot2);
            if (conjuntosItems.containsKey(clave)){
                conjuntosItems.remove(clave);
            }
            pot2 = pot2<<1;
        }
        // Ahora generamos, para cada conjunto conflictivo, el conjunto 
        // de actividades que lo necesitan.
        conjuntosActividades = new HashMap<Integer,Set<Actividad>>();
        for (Iterator<Integer> it = conjuntosItems.keySet().iterator(); it.hasNext();) {
            Integer i = it.next();
            //String[] lString = conjuntosItems.get(i).toArray(new String[0]);            
            pot2=1;
            for (int j=1;j<=lacts.length;j++){
                if ((pot2&i)>=1){
                    if (!conjuntosActividades.containsKey(i)){
                        HashSet<Actividad> hashaux = new HashSet<Actividad>();
                        hashaux.add(lacts[j-1]);
                        conjuntosActividades.put(i,hashaux);
                    } else {
                        conjuntosActividades.get(i).add(lacts[j-1]);
                    }
                }
                pot2 = pot2<<1;
            }
            /* Lo de aqui es para comprobar que funciona
            StringBuffer strbuf = new StringBuffer("Grupo items ");
            strbuf.append(i);
            strbuf.append(": ");
            strbuf.append(lString[0]);
            for (int k = 1; k < lString.length;k++){
                strbuf.append(',');
                strbuf.append(lString[k]);
            }
            strbuf.append(" => ");
            for (Iterator<Actividad> it1 = conjuntosActividades.get(i).iterator(); it1.hasNext();) {
                Actividad a = it1.next();
                strbuf.append(a.getNombre());
                strbuf.append(',');
            }
            strbuf.deleteCharAt(strbuf.length()-1);
            System.out.println(strbuf.toString());*/
        }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public List<EstadoTA> iniciales() {
        ListaEstadosActividades lEstAct = new ListaEstadosActividades();
        actividades = activGen.getConjunto().keySet().toArray(new String[0]);
        for (int i=0; i<actividades.length;i++){
        lEstAct.addEstado(actividades[i], EstadoActividad.Idle);
        }
        ListaEstadosItems lEstItems = new ListaEstadosItems();
        items = itemGen.getItems();
        for (int i=0; i<items.length;i++){
        lEstItems.addEstado(items[i], EstadoItem.FREE);
        }
        EstadoTA estadoIni = new EstadoTA(lEstItems, lEstAct, new TreeMap<Integer, Actividad>());
        return backtracking(estadoIni);
    }

    public List<EstadoTA> transitar(EstadoTA state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StateLabeledList<EstadoTA> transitarConEtiqueta(EstadoTA state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> dameTransiciones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    public List<EstadoTA> backtracking(EstadoTA estado)
    {
        TreeMap<Integer, Actividad> propietarias = new TreeMap<Integer,Actividad>();
        Integer[] conjItemsConflictivos = 
                conjuntosItems.keySet().toArray(new Integer[0]);        
        ArrayList<EstadoTA> laux = new ArrayList<EstadoTA>();
        backtracking2(estado,0,propietarias,conjItemsConflictivos,laux);
        return laux;
    }

    private void backtracking2(EstadoTA eini,int i, TreeMap<Integer, Actividad> propietarias,
            Integer[] conjItemsConflictivos, ArrayList<EstadoTA> laux) {
        Integer claveConj = conjItemsConflictivos[i];
        Actividad[] conjActs = conjuntosActividades.get(claveConj).toArray(new Actividad[0]);
        for (int j=0; j<conjActs.length;j++){
            Actividad a = conjActs[j];
            propietarias.put(claveConj,a);
            if (i==conjItemsConflictivos.length-1){ // ya hemos asignado el ultimo
                EstadoTA estadoaux = new EstadoTA(eini);
                estadoaux.propietarias = new TreeMap<Integer, Actividad>(propietarias);
                estadoaux.lanzarPosibles();
                laux.add(estadoaux);
            } else {
                backtracking2(eini,i+1, propietarias, conjItemsConflictivos, laux);
            }
        }
    }

}
