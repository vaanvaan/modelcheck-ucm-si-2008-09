package ucm.si.Checker.modular.defecto;

import ucm.si.basico.ecuaciones.EX;
import java.util.Iterator;
import java.util.List;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.Resultado;
import ucm.si.Checker.VisitanteConector;
import ucm.si.Checker.modular.VisitanteConectorModular;
import ucm.si.Checker.modular.VisitanteFormula;
import ucm.si.Checker.tabulacion.TabulacionFormulas;
import ucm.si.Checker.tabulacion.TabulacionMemSistema;
import ucm.si.util.GrafoCaminos;
import ucm.si.util.GrafoUnico;

public class VisitantePorDefectoEX<S> extends ModuloVisitante implements VisitanteFormula<S,EX>{

    private Interprete<S> interprete = null;
    private TabulacionFormulas<S> tabFormulas;
    private VisitanteConectorModular<S> conector;
    
    public VisitantePorDefectoEX(Interprete<S> interprete, VisitanteConectorModular<S> conector) {
        this.interprete = interprete;
        this.tabFormulas = new TabulacionMemSistema<S>();
        this.conector = conector;
    //this.tabFormulas = new TabulacionFormulas<S>();
    }
    
    public void visita(EX eventx) {
        S estado = this.conector.getEstado();
        List<S> listaEstados;
        S epadre = estado;
        listaEstados = interprete.transitar(estado);
        Iterator<S> it = listaEstados.iterator();
        boolean seguir = true;
        GrafoCaminos<S> grafoaux = new GrafoUnico<S>(estado);
        while (seguir && it.hasNext()) {
            estado = it.next();
            this.conector.setEstado(estado);
            eventx.getOperando(0).accept(this.conector);
            this.resParcial = this.conector.getResParcial();
            if (resParcial.equals(Resultado.COD_TRUE)) {
                //hemos encontrado uno que nos vale
                seguir = false;
                grafoaux = new GrafoUnico(epadre);
                grafoaux.setArista(epadre, estado);
                resParcial.setEjemplo(GrafoCaminos.CreateGrafo(grafoaux, resParcial.getEjemplo()));
            } else {
                grafoaux = GrafoCaminos.CreateGrafo(grafoaux, resParcial.getContraejemplo());
                grafoaux.setArista(epadre, estado);
            }
        }
        if (seguir) {
            // entonces, es que no hemos encontrado ninguno
            resParcial.setResultado(Resultado.COD_FALSE);
            resParcial.setContraejemplo(grafoaux);
        }
        estado = epadre;
        this.conector.setEstado(estado);
    }

}
