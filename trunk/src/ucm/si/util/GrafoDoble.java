/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
class GrafoDoble<S> extends GrafoCaminos<S> {

    private S inicial = null;
    private GrafoCaminos<S> camino1;
    private GrafoCaminos<S> camino2;
    private TLG caminoFinal;

    protected GrafoDoble(GrafoCaminos<S> c1) {
        this.camino1 = c1;
        this.camino2 = new GrafoUnico();
        this.caminoFinal = new TLG();
        this.inicial = c1.getInicio();

    // Almacena el grao mas uno vacio adicional haciendolo pasar por uno solo.
    }

    protected GrafoDoble(GrafoCaminos<S> c1, GrafoCaminos<S> c2) {
        this.camino1 = c1;
        this.camino2 = c2;
        this.caminoFinal = new TLG();
        this.inicial = c1.getInicio();

    //Almacena los dos grafos como si fueran uno.
    }

    @Override
    public void setArista(S eini, S efin) {
        if (caminoFinal.getTabla().containsKey(eini)){
            caminoFinal.getTabla().put(eini, efin);
        } else{ 
            camino1.setArista(eini, efin);
        }
    }

    @Override
    public void setInicio(S ini) {
        this.inicial = ini;
    }

    @Override
    public List<S> getHijos(S e) {
        if (caminoFinal.getTabla().containsKey(e)) {
            return caminoFinal.getHijo(e);
        } else {            
            List<S> c1 = this.camino1.getHijos(e);
            List<S> c2 = this.camino2.getHijos(e);
            // juntamos los 2 caminos contenidos mas luego tambien el del camino final
            // aun no se hace merge completo por problemas "tecnicos"
            List<S> cfinal = new ArrayList<S>();
            if (c1!=null&&c1.size()>0) cfinal.addAll(c1);
            if (c2!=null&&c2.size()>0) cfinal.addAll(c2);
            return cfinal;
        //return merge ( merge(c1,c2), this.caminoFinal.getHijo(e)  );
        }
    }

    @Override
    public void setS(S e, List<S> Hijos) {
        caminoFinal.getTabla().put(e, Hijos);
    }

    private List merge(List a, List b) {
        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }
        //  si ninguno de los dos es vacio los juntamos
        int la = a.size();
        int lb = b.size();
        ArrayList l = null;
        List ldirec = null;
        List lbase = null;
        // escojemos el mas corto para ahorranos iteraciones
        if (la < lb) {
            l = new ArrayList(b);
            ldirec = a;
            lbase = b;
        } else {
            l = new ArrayList(a);
            ldirec = b;
            lbase = a;
        }
        // juntamos los dos List
        for (int i = 0; i < ldirec.size(); i++) {
            Object o = ldirec.get(i);
            if (!lbase.contains(o)) {
                l.add(o);
            }
        }

        // devolvemos l que es la list final.
        return l;

    }

    @Override
    public S getInicio() {
        return this.inicial;
    }
}
