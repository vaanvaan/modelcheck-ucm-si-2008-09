/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad.actividad.Contexto;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.estado.EstadoTA;

/**
 *
 * @author José Antonio
 */
public class PseudoEstado extends EstadoTA{
    public boolean finalizado = false;

    public boolean isEmpty()
    {
        return this.actividades.size() == 0;
    }

    /*
     *  generar la logica para lanzar estados
    private
    */
    public List<String> ActividadesThrowables()
    {
        List<String> c = new ArrayList<String>();
        ActividadGenerator ag = ActividadGenerator.getReference();
        for( String s : ag.getConjunto().keySet())
        {
            Actividad a = ag.getItem(s);
            EstadoActividad estadoA = this.actividades.getEstado(s);
            boolean b = true;
            if(estadoA == EstadoActividad.Idle)
            {
                 b = a.CondicionesSatisfy(Contexto.getReference());
                 
                 // Colocar aqui el calculo segun estados
            }
            //Actividad a= ag.getItem(s);

        }

        return c;
    }

}
