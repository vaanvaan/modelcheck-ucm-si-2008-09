/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.Laberinto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import ucm.si.Checker.DefaultModelChecker;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.InterpreteWrapper;
import ucm.si.Checker.ModelChecker;
import ucm.si.Checker.Resultado;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Proposicion;

/**
 *
 * @author Pilar
 */
public class Laberinto implements Interprete<Posicion> {

    // supongamos siempre un laberinto en el cual la salida 
    // se realiza en la posicion 0,0
    private boolean[][] laberinto;
    private int dim;

    public Laberinto(int dim) {
        this.dim = dim;
        Random r=  new Random(122334234);
        laberinto = new boolean[dim][dim];
        for (int j = 0; j < dim; j++) {
            for (int i = 0; i < dim; i++) {
                laberinto[i][j] = r.nextBoolean();
            }
        }
        boolean seguir = true;
        Posicion p = new Posicion(1, 1);
        while (seguir){
            boolean dir = r.nextBoolean();
            if (dir){
                if (p.posX<this.dim-1){
                    p.posX++;                    
                }else p.posY++;
            }else if (p.posY<this.dim-1){
                    p.posY++;                    
                }else p.posX++;
            laberinto[p.posX][p.posY] = true;
            seguir = p.posX!=dim-1 || p.posY!=dim-1;
        }
        /*laberinto[2][1] = false;
        laberinto[1][2] = false;
        laberinto[3][3] = false;
        laberinto[3][1] = false;
        laberinto[1][3] = false;
        laberinto[3][2] = false;
        laberinto[0][4] = false;*/
        //laberinto[3][0] = false;
//        laberinto[dim-1][dim-1] = false;
        

    }

    public int getDim() {
        return dim;
    }

    public boolean posible(String s, Posicion p) {

        if (s.equals("UP")) {
            if (p.posY != 0) {
                if (!this.laberinto[p.posX][p.posY - 1]) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (s.equals("DOWN")) {
            if (p.posY != (dim - 1)) {
                if ((!this.laberinto[p.posX][p.posY + 1])) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (s.equals("LEFT")) {
            if (p.posX != 0) {
                if (!this.laberinto[p.posX - 1][p.posY]) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (s.equals("RIGHT")) {
            if (p.posX != (this.dim - 1)) {
                if (!this.laberinto[p.posX + 1][p.posY]) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;

    }

    public void up(Posicion p) {
        p.posY = p.posY - 1;
    }

    public void down(Posicion p) {
        p.posY = p.posY + 1;
    }

    public void left(Posicion p) {
        p.posX = p.posX - 1;
    }

    public void right(Posicion p) {
        p.posX = p.posX + 1;
    }

    public List<Posicion> iniciales() {
        ArrayList<Posicion> l = new ArrayList<Posicion>();
        l.add(new Posicion(0, 0));
        return l;
    }

    public Posicion copyOf(Posicion p) {
        return new Posicion(p.getPosX(), p.getPosY());
    }

    public List<Posicion> transitar(Posicion state) { 
        List<Posicion> lista = new ArrayList<Posicion>();
        Posicion lab =  state;
        if (posible("DOWN", lab)) {
            Posicion l = copyOf(lab);
            down(l);
            lista.add(l);
        }
        if (posible("RIGHT", lab)) {
            Posicion l = copyOf(lab);
            right(l);
            lista.add(l);
        }
        /*if (posible("LEFT", lab)) {
            Posicion l = copyOf(lab);
            left(l);
            lista.add(l);
        }
        if (posible("UP", lab)) {
            Posicion l = copyOf(lab);
            up(l);
            lista.add(l);
        }*/


        return lista;

    }
    
    public boolean checkPos(Posicion p){
    	boolean res = laberinto[p.posX][p.posY];
    	return res;
    }
    
    public static void main(){
        Interprete<Posicion> lab = new Laberinto(10);
        ModelChecker<Posicion> m = new DefaultModelChecker<Posicion>();        
        Formula formula = new AU(new Not(new Proposicion() {

            @Override
            public boolean esCierta(Object s) {
                return false;
            }
        }), 
          new Proposicion() {

            @Override
            public boolean esCierta(Object s) {
                return true;
            }
        } );
        System.out.println(m.chequear(formula));
    }
    
}
    
    
