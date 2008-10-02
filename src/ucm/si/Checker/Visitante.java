package ucm.si.Checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;


// aï¿½adir constructora para usar logs globales, si es necesario.
public  class Visitante <S> {
	private Resultado resParcial = new Resultado(Resultado.COD_TRUE);
	private Stack<Formula> pilaFormulas = new Stack<Formula>();
	private boolean inicio = false;
	private S estado;
	private Interprete<S> interprete= null;
        private TabulacionFormulas<S> tabFormulas;
	
	public Visitante(){
		
	}
	
	public Visitante(S estado, Interprete<S> interprete) {
		super();
		this.estado = estado;
		this.interprete = interprete;
                this.tabFormulas = new TabulacionFormulas<S>();
	}

	public Resultado visita(Formula expresion){
		expresion.accept(this);
		return resParcial;
	}

	public Resultado getResParcial() {
		return resParcial;
	}

	public void setResParcial(Resultado resParcial) {
		this.resParcial = resParcial;
	}
        
    	public void visita(Not n){
		n.getFormula().accept(this);
		if (resParcial.equals(Resultado.COD_TRUE)){
			resParcial.setResultado(Resultado.COD_FALSE);
		} else if (resParcial.equals(Resultado.COD_FALSE)){
			resParcial.setResultado(Resultado.COD_TRUE);
		}else if (resParcial.equals(Resultado.COD_MAYBEF))
			resParcial.setResultado(Resultado.COD_MAYBET);
		else resParcial.setResultado(Resultado.COD_MAYBEF);		
	}
	public void visita(Or or){
		or.getExpIzq().accept(this);
		Resultado resIzq = resParcial;
		or.getExpDer().accept(this);
		Resultado resDer = resParcial;
		Resultado resAND;
		try{
			boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
			boolean part2 = Boolean.parseBoolean(resDer.getResultado());
			part1 = part1 || part2;
			resAND = new Resultado(String.valueOf(part1));
		}catch(Exception e){
			if((resIzq.getResultado().equals(Resultado.COD_TRUE)) ||
					(resDer.getResultado().equals(Resultado.COD_TRUE)) ){
				resAND = new Resultado(Resultado.COD_TRUE);
			}else{
				resAND = new Resultado(Resultado.COD_MAYBET);
			}
		}
		resParcial=resAND;
	}
	public void visita(And and){

		// TODO Auto-generated method stub
		and.getExpIzq().accept(this);
		Resultado resIzq = resParcial;
		and.getExpDer().accept(this);
		Resultado resDer = resParcial;
		Resultado resAND;
		try{
			boolean part1 = Boolean.parseBoolean(resIzq.getResultado());
			boolean part2 = Boolean.parseBoolean(resDer.getResultado());
			part1 = part1 && part2;
			resAND = new Resultado(String.valueOf(part1));
		}catch(Exception e){
			if((resIzq.getResultado().equals(Resultado.COD_FALSE)) ||
					(resDer.getResultado().equals(Resultado.COD_FALSE)) ){
				resAND = new Resultado(Resultado.COD_FALSE);
			}else{
				resAND = new Resultado(Resultado.COD_MAYBEF);
			}
		}
		resParcial=resAND;
	
	}
	
	public void visita(AX allnext){
		ArrayList<S> listaEstados;
		S epadre = estado;
		listaEstados = (ArrayList<S>) interprete.transitar(estado);
		Iterator it = listaEstados.iterator();
		boolean seguir = true;
		while (it.hasNext() && seguir){
//			Visitante vhijo = new Visitante<S>();
//			vhijo.estado= it.next();
			estado = (S) it.next();
			allnext.getFormula().accept(this);
			// supongo que el get formula nos devolverá la fórmula interna
			// del all next
			if (!resParcial.equals(Resultado.COD_TRUE)){
				seguir=false;
				//resParcial se queda con false, asi que no lo tocamos.
			}
		}
		estado = epadre;
	}
	
	public void visita(EX eventx){
		ArrayList<S> listaEstados;
		S epadre = estado;
		listaEstados = (ArrayList<S>) interprete.transitar(estado);
		Iterator it = listaEstados.iterator();
		boolean seguir = true;
		while (it.hasNext() && seguir){
			estado = (S) it.next();
			eventx.getFormula().accept(this);
			if(resParcial.equals(Resultado.COD_TRUE)){
				//hemos encontrado uno que nos vale
				seguir = false;
			}
		}
		if(seguir){
			// entonces, es que no hemos encontrado ninguno
			resParcial.setResultado(Resultado.COD_FALSE);
		}
		estado = epadre;
	}
	
	public void visita(Proposicion<S> p){
		if (p.esCierta(estado)) {
			resParcial.setResultado(Resultado.COD_TRUE);
		} else resParcial.setResultado(Resultado.COD_FALSE);
	}
        
        public void visita(AU au){
            S eanterior = estado;
            LinkedBlockingQueue<S> cola =
              new LinkedBlockingQueue<S>(interprete.transitar(estado));
            HashSet<S> visitados = new HashSet<S>();
            boolean seguir = true;
            while (seguir&&(!cola.isEmpty())){
                estado = cola.poll();                
                au.getExprDer().accept(this);
                if (!resParcial.equals(Resultado.COD_TRUE)){
                    au.getExprIzq().accept(this);
                    if (resParcial.equals(Resultado.COD_TRUE)){
                        visitados.add(estado);                
                        List<S> listaux = interprete.transitar(estado);
                        if (listaux.isEmpty() ||
                            this.algunEstadoComun(visitados,listaux)){
                            // por este camino o no hay mas hijos
                            // o hay un hijo q ya hemos visitado:
                            //nunca se cumple f2 en ese camino ciclico
                            // => falso
                            seguir = false;
                        } else cola.addAll(listaux);
                    } else {
                        seguir = false;
                    }
                }
            }
            if (seguir){
                resParcial.setResultado(Resultado.COD_TRUE);
            } else {
                resParcial.setResultado(Resultado.COD_FALSE);
            } 
            estado = eanterior;
        }
                
        
        public void visita(EU eu){
            S eanterior = estado;
            LinkedBlockingQueue<S> cola =
              new LinkedBlockingQueue<S>(interprete.transitar(estado));
            HashSet<S> visitados = new HashSet<S>();
            boolean seguir = true;
            boolean encontrado = false;
            while (seguir&&(!cola.isEmpty())){
                estado = cola.poll();
                eu.getExprDer().accept(this);
                if (resParcial.equals(Resultado.COD_TRUE)){
                    encontrado = true;
                    seguir = false;                    
                } else {
                    eu.getExprIzq().accept(this);
                    if (resParcial.equals(Resultado.COD_TRUE)){
                        visitados.add(estado);                
                        List<S> listaux = interprete.transitar(estado);
                        // quitamos los q ya hemos visitado
                        listaux.removeAll(visitados);
                        // aniadimos los q todavia no hemos visitado
                        cola.addAll(listaux);
                    } else {
                        seguir = false;
                    }
                }
            }
            if (encontrado){
                resParcial.setResultado(Resultado.COD_TRUE);
            } else {
                resParcial.setResultado(Resultado.COD_FALSE);
            } 
            estado = eanterior;
        }
        
        private boolean algunEstadoComun(Set<S> visitados, List<S> lista){
            LinkedBlockingQueue<S> l = new LinkedBlockingQueue<S>(lista);
            boolean encontrado = false;
            while (!encontrado && !l.isEmpty()){
                S s = l.poll();
                encontrado = visitados.contains(s);
            }
            return encontrado;
        }
}
