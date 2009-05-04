package ucm.si.Checker.modular.defecto;

import java.util.Iterator;
import java.util.List;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.util.GrafoCaminos;
import ucm.si.util.GrafoUnico;

public class VisitantePorDefectoAX<S> extends ModuloVisitante implements VisitanteFormula<S,AX>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoAX(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
    }
    
    public void visita(AX allnext) {
        S estado = this.conector.getEstado();
        List<S> listaEstados;
        S epadre = estado;
        GrafoCaminos<S> ej = new GrafoUnico<S>(epadre);
        listaEstados = interprete.transitar(epadre);
        Iterator<S> it = listaEstados.iterator();
        boolean seguir = true;
        while (it.hasNext() && seguir) {
            estado = it.next();
            this.conector.setEstado(estado);
            allnext.getOperando(0).accept(this.conector);
            this.resParcial = this.conector.getResParcial();
            if (!resParcial.equals(Resultado.COD_TRUE)) {
                seguir = false;
                //resParcial se queda con false, asi que no lo tocamos.
                GrafoCaminos<S> gaux = resParcial.getContraejemplo();
                gaux.setArista(epadre, estado);
                gaux.setInicio(epadre);
                //GrafoCaminos<S> gaux2 = GrafoCaminos.CreateGrafo(gaux, resParcial.getContraejemplo());
                //gaux2.setInicio(epadre);                
                resParcial.setContraejemplo(gaux);
            } else {
                ej.setArista(epadre, estado);
            }
        }
        if (seguir) {
            resParcial.setResultado(Resultado.COD_TRUE);
            resParcial.setEjemplo(ej);
        }
        estado = epadre;
        this.conector.setEstado(estado);
        this.conector.setResParcial(this.resParcial);
    }

}
