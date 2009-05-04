/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.laberinto;

import ucm.si.animadorGUI.util.Launcher;
import ucm.si.animadorGUI.Drawer;
import ucm.si.Laberinto.Final;
import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.LaberintoPropo;
import ucm.si.Laberinto.Posicion;
import ucm.si.basico.ecuaciones.*;

/**
 *
 * @author Pilar
 */
public class DemoLaberinto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ContextoLaberinto context = new ContextoLaberinto();
        Laberinto lab = new Laberinto(10);
        context.setLab(lab);
        
        Posicion pos = new Posicion(1, 1);
        LaberintoPropo prop = new LaberintoPropo(pos);
        prop.setLab(lab);
        Final fin = new Final(lab.getDim() - 1, lab.getDim() - 1);
        Formula nofin = new And(new Not(fin), prop);
        Formula haycamino = new AU(nofin, fin);
        Formula formula = new Not(haycamino);
        
        Launcher<Posicion> launcher = new Launcher<Posicion> (context, lab, formula);
        
        launcher.runCheck(pos);
        
        Drawer dw = new DrawerLaberinto();
        launcher.launchGrafico(dw);
    }

}
