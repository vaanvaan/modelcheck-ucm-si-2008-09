/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.animadorGUI.util;

import ucm.si.animadorGUI.AnimadorGrafico;
import ucm.si.animadorGUI.Drawer;
import ucm.si.util.Contexto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.util.Roseta;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.navegador.AnimadorInterface;
import ucm.si.navegador.Navegador;
import ucm.si.navegador.NavigatorInterface;
import ucm.si.util.GrafoCaminos;

/**
 *
 * @author Pilar
 */
public class Launcher<S> 
{
    //private AnimadorInterface<S> animador;
    private NavigatorInterface<S> navegador;
    private Contexto contexto;
    private ModelChecker checker;
    private Interprete<S> interprete;
    private Formula formula;
    private Resultado<S> resultado;

    public Launcher(NavigatorInterface<S> navegador, Contexto contexto, ModelChecker checker, Interprete<S> interprete, Formula formula) {
        this.navegador = navegador;
        this.contexto = contexto;
        this.checker = checker;
        this.interprete = interprete;
        this.formula = formula;
    }

    public Launcher(Contexto contexto, Interprete<S> interprete, Formula formula) {
        this.contexto = contexto;
        this.interprete = interprete;
        this.formula = formula;
        this.checker = new DefaultModelChecker<S>();
        //this.navegador = new Navegador<S>();
        
    }

    public void runCheck()
    {
        /*this.resultado = this.checker.chequear(interprete, formula, resultado);
        String res = this.resultado.getResultado();
        GrafoCaminos<S> camino = null;
        if(res.equals(Resultado.COD_TRUE) || res.equals(Resultado.COD_MAYBET) )
        {
            camino = this.resultado.getEjemplo();
        }
        else if(res.equals(Resultado.COD_FALSE) || res.equals(Resultado.COD_MAYBEF) )
        {
            camino = this.resultado.getContraejemplo();
        }
        else
        {
            // generar log de error
        }
        
        Roseta<S> roseta = this.checker.getRoseta();
        this.navegador = new Navegador<S>(camino, roseta);*/
        S ini = this.interprete.iniciales().get(0);
        this.runCheck(ini);
    
    }
    
    public void runCheck(S inicio)
    {
        //m.chequear(lab, new Not(haycamino), pos)
        this.resultado = this.checker.chequear(interprete, formula, inicio);
        String res = this.resultado.getResultado();
        GrafoCaminos<S> camino = null;
        if(res.equals(Resultado.COD_TRUE) || res.equals(Resultado.COD_MAYBET) )
        {
            camino = this.resultado.getEjemplo();
            JOptionPane.showMessageDialog(new JFrame(), "La formula es "+ res);
        }
        else if(res.equals(Resultado.COD_FALSE) || res.equals(Resultado.COD_MAYBEF) )
        {
            camino = this.resultado.getContraejemplo();
            JOptionPane.showMessageDialog(new JFrame(), "La formula es falsa");
        }
        else
        {
            // generar log de error
        }
        
        Roseta<S> roseta = this.checker.getRoseta();
        this.navegador = new Navegador<S>(camino, roseta,interprete.dameTransiciones());

    }
    
    public void launchGrafico(Drawer<S> dw )
    {
        AnimadorGrafico<S> anim = new AnimadorGrafico<S>((Navegador<S>) this.navegador, dw, this.contexto);
        
        /*lab = new Laberinto(20);
        DefaultModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();
        Posicion pos = new Posicion(1, 1);
        LaberintoPropo prop = new LaberintoPropo(pos);
        prop.setLab(lab);
        Final fin = new Final(lab.getDim() - 1, lab.getDim() - 1);
        Formula nofin = new And(new Not(fin), prop);
        Formula haycamino = new EU(nofin, fin);
        Resultado<Posicion> res = m.chequear(lab, new Not(haycamino), pos);
        Navegador<Posicion> nav;
        if (res.equals(Resultado.COD_TRUE)) {
            System.out.println("La formula es cierta.");
            nav = new Navegador<Posicion>(res.getEjemplo(), m.getRoseta());
        } else {
            System.out.println("La formula es falsa.");
            nav = new Navegador<Posicion>(res.getContraejemplo(), m.getRoseta());
        }
        ContextoLaberinto contxt = new ContextoLaberinto();
        contxt.setLab(lab);
        AnimadorGrafico<Posicion> anim = new AnimadorGrafico<Posicion>(nav, new DrawerLaberinto(), contxt);*/
    }
    
    

}
