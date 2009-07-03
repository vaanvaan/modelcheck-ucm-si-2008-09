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
 * Es una facilidad que se usa para lanzar una operacion de modelcheck asi se oculta operaciones tediosas y repetitivas.
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
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

    /**
     * Constructor
     * @param navegador
     * @param contexto
     * @param checker
     * @param interprete
     * @param formula
     */
    public Launcher(NavigatorInterface<S> navegador, Contexto contexto, ModelChecker checker, Interprete<S> interprete, Formula formula) {
        this.navegador = navegador;
        this.contexto = contexto;
        this.checker = checker;
        this.interprete = interprete;
        this.formula = formula;
    }

    /**
     * Constructor
     * @param contexto
     * @param interprete
     * @param formula
     */
    public Launcher(Contexto contexto, Interprete<S> interprete, Formula formula) {
        this.contexto = contexto;
        this.interprete = interprete;
        this.formula = formula;
        this.checker = new DefaultModelChecker<S>();
        //this.navegador = new Navegador<S>();
        
    }

    /**
     * Lanzar un chekeo sobre el conjunto de estados iniciales que proporciona el interprete
     * @return
     */
    public Resultado<S> runCheck()
    {
        S ini = this.interprete.iniciales().get(0);
        return this.runCheck(ini);
    }


    /**
     * Lanzar un chekeo sobre un estado marcado
     * @param inicio
     * @return
     */
    public Resultado<S> runCheck(S inicio)
    {
        this.resultado = this.checker.chequear(interprete, formula, inicio);
        return this.resultado;
    }

    /**
     * Lanza el animadro grafico una vez que se ha realizado el chequeo.
     * @param dw
     * @param tipoBotonera
     */
    public void launchGrafico(Drawer<S> dw , int tipoBotonera)
    {
        Resultado<S> res = this.resultado;
        GrafoCaminos<S> camino = null;
        if (res.equals(Resultado.COD_TRUE))
        {
            camino = this.resultado.getEjemplo();
        }
        else if(res.equals(Resultado.COD_FALSE))
        {
            camino = this.resultado.getContraejemplo();
        }

        Roseta<S> roseta = this.checker.getRoseta();
        this.navegador = new Navegador<S>(camino, roseta,interprete.dameTransiciones());
        AnimadorGrafico<S> anim = new AnimadorGrafico<S>((Navegador<S>) this.navegador,
                dw, this.contexto, tipoBotonera);
    }
    
    

}
