
package ucm.si.Checker;

class NodoLista<S> {
    NodoLista<S> ant;
    S s;

    public NodoLista(NodoLista<S> ant, S s) {
        this.ant = ant;
        this.s = s;
    }

    public NodoLista<S> getAnt() {
        return ant;
    }

    public void setAnt(NodoLista<S> ant) {
        this.ant = ant;
    }

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }
    
    
}
