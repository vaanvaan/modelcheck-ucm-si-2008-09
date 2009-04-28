/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.util;

import javax.swing.JMenuItem;

/**
 *
 * @author José Antonio
 */
public class JMenuItemInterprete extends JMenuItem
{
    private String nombre;
    private Class interprete;
    public JMenuItemInterprete( Class interprete )
    {
        this.interprete= interprete;
        this.nombre = interprete.getSimpleName();
    }

    @Override
    public String toString() {
        return this.nombre;
    }



}
