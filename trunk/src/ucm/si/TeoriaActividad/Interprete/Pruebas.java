/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;

/**
 *
 * @author José Antonio
 */
public class Pruebas
{
    public static void main(String[] args) throws Exception {

        Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        ItemGenerator itemGen = ItemGenerator.getReference();
        Item[] listaItem1 = {item1, item2};
        Item[] listaItem2 = {item2, item3};
        Actividad actividad1 = new Actividad("A1", listaItem1, null, null, null);
        Actividad actividad2 = new Actividad("A2", listaItem2, null, null, null);
        ActividadGenerator activGen = ActividadGenerator.getReference();
        itemGen.addItem(item1);
        itemGen.addItem(item2);
        itemGen.addItem(item3);
        activGen.addActividad(actividad1);
        activGen.addActividad(actividad2);
        
        // Creamos la tabla para particionar los items en subconjuntos
        // por ahora hacemos para 32 actividades, pero es facil hacerlo generico
        
        String[] lItems = itemGen.getItems();
        int[] lClaves = new int[lItems.length];
        Actividad[] lacts = activGen.getConjunto().keySet().
                toArray(new Actividad[activGen.Elements()]);
        for (int j = 0; j < lItems.length; j++){
            if (lacts[0].necesita(itemGen.getItem(lItems[j]))){
                lClaves[j] = 1;
            } else {
                lClaves[j] = 0;
            }
        }
        for (int i = 0; i < lacts.length; i++){
            for (int j =0; j < lItems.length; j++){
                if (lacts[i].necesita(itemGen.getItem(lItems[j])))
                    lClaves[j] = lClaves[j] + 2^i;
            }
        }
        HashMap<Integer,Set<String>> conjuntosItems = new HashMap<Integer,Set<String>>();
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
        for (int i = 0; i < lacts.length+1; i++) {
            Integer clave = new Integer(2^i);
            if (conjuntosItems.containsKey(clave)){
                conjuntosItems.remove(clave);
            }
        }
        
        PseudoEstado estadoIni = new PseudoEstado();
        estadoIni.actividades = new ListaEstadosActividades();
        estadoIni.items = new ListaEstadosItems();


        backtracking(estadoIni);





    }

    public static void backtracking(PseudoEstado estado)
    {
        /* suponemos que obtenemos el conjunto de Actividades que podriamos
           lanzar en el siguiente estado
         */
        if(estado.isEmpty())
        {} // este if no sirve de nada




        int numActiv = ActividadGenerator.getReference().Elements();
        for (String st : ActividadGenerator.getReference().getConjunto().keySet())
        {

            PseudoEstado estadoNuevo = new PseudoEstado();


            List<EstadoActividad> lista = new ArrayList<EstadoActividad>();

            List<String> listaClaves = new ArrayList<String>();
            listaClaves.add(st);
            estadoNuevo.actividades = new ListaEstadosActividades(lista, listaClaves);
            backtracking(estadoNuevo); // Aqui se esta haciendo bucle infinito

        }
    }

}
