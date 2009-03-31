/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
        {}




        int numActiv = ActividadGenerator.getReference().Elements();
        for (String st : ActividadGenerator.getReference().getConjunto().keySet())
        {

            PseudoEstado estadoNuevo = new PseudoEstado();


            List<EstadoActividad> lista = new ArrayList<EstadoActividad>();

            List<String> listaClaves = new ArrayList<String>();
            listaClaves.add(st);
            estadoNuevo.actividades = new ListaEstadosActividades(lista, listaClaves);
            backtracking(estadoNuevo);

        }
    }

}
