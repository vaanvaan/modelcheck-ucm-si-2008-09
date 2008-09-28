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
import ucm.si.Checker.Estado;
import ucm.si.Checker.Interprete;

/**
 *
 * @author Pilar
 */
public class Laberinto implements Interprete {

    // supongamos siempre un laberinto en el cual la salida 
    // se realiza en la posicion 0,0
    private boolean[][] laberinto;
    private int dim;

    Laberinto() {
        laberinto = new boolean[7][7];
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 7; i++) {
                laberinto[i][j] = true;
            }
        }
        laberinto[2][1] = false;
        laberinto[1][2] = false;
        laberinto[5][2] = false;
        laberinto[1][6] = false;

        dim = 7;

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
        } else if (s.equals("RIGTH")) {
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

    public void rigth(Posicion p) {
        p.posX = p.posX + 1;
    }

    public List<Estado> iniciales() {
        ArrayList<Estado> l = new ArrayList<Estado>();
        l.add(new Posicion(0, 0));
        return l;
    }

    public Posicion copyOf(Posicion p) {
        return new Posicion(p.getPosX(), p.getPosY());
    }

    public List<Estado> transitar(Estado state) {
        List<Estado> lista = new ArrayList<Estado>();
        Posicion lab = (Posicion) state;
        if (posible("DOWN", lab)) {
            Posicion l = copyOf(lab);
            down(l);
            lista.add(l);
        }
        if (posible("RIGTH", lab)) {
            Posicion l = copyOf(lab);
            rigth(l);
            lista.add(l);
        }
        if (posible("LEFT", lab)) {
            Posicion l = copyOf(lab);
            left(l);
            lista.add(l);
        }
        if (posible("UP", lab)) {
            Posicion l = copyOf(lab);
            up(l);
            lista.add(l);
        }


        return lista;

    }
}
