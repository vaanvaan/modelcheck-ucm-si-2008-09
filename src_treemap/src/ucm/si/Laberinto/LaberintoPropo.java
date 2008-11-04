package ucm.si.Laberinto;

import ucm.si.Checker.Interprete;
import ucm.si.basico.ecuaciones.Proposicion;

public class LaberintoPropo extends Proposicion<Posicion>{
	private Laberinto lab;
	private Posicion p;
	
	public LaberintoPropo(Posicion p){
		this.p = p;
	}
	
	public Laberinto getLab() {
		return lab;
	}
	public void setLab(Interprete<Posicion> lab2) {
		this.lab = (Laberinto) lab2;
	}
	@Override
	public boolean esCierta(Posicion s) {
		return lab.checkPos(s);
	}

}
