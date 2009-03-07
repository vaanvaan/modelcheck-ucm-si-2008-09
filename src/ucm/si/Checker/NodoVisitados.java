package ucm.si.Checker;


public class NodoVisitados<S> {
    NodoVisitados<S> ant;
    S estado;
    
    public NodoVisitados() {
        this.ant = null;
        this.estado = null;
    }
    
    public NodoVisitados(NodoVisitados<S> ant) {
        this.ant = ant;
        this.estado = null;
    }
    
    public boolean contains(S s){
        if ((estado!=null)&&(estado.equals(s))){
            return true;
        } else if (ant==null){
            return false;
        } else return ant.contains(s);
    }
    
    public void add(S s){
        this.estado = s;
    }

}
