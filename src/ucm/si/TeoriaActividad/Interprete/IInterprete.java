/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

/**
 *
 * @author nico
 */
public interface IInterprete {
    public String[] getItemsNombre();
    public String[] getActividadesNombre();
    public String[] getActividadesHijas(String actividad);
}
