/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import ucm.si.adhoc.AdHoc;
import ucm.si.adhoc.Primo;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;

/**
 *
 * @author nico
 */
public class Arbol<S> {
    private S info;
    private List<Arbol<S>> hijos;

    public Arbol(S info) {
        this.info = info;
        this.hijos = new ArrayList<Arbol<S>>();
    }

    public Arbol(S info, List<Arbol<S>> hijos) {
        this.info = info;
        this.hijos = hijos;
    }
    
    public List<Arbol<S>> getHijos() {
        return hijos;
    }

    public void setHijos(List<Arbol<S>> hijos) {
        this.hijos = hijos;
    }

    public S getInfo() {
        return info;
    }

    public void setInfo(S info) {
        this.info = info;
    }
    
    public void aniadirHijo(Arbol<S> nuevoHijo){
        this.hijos.add(nuevoHijo);
    }
    
    public DefaultMutableTreeNode getArbolTreeNode(){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode();
        nodo.setUserObject(info.toString());        
        Iterator<Arbol<S>> it = hijos.iterator();
        while (it.hasNext()) {
            Arbol<S> arbol = it.next();
            nodo.add(arbol.getArbolTreeNode());
        }
        return nodo;
    }

    public static void main(String args[]){
        AdHoc a = new AdHoc("0", "0 1 4;1 1 4;4 1 4 8;6 4 2");
        ModelChecker<Integer> m = new DefaultModelChecker<Integer>(a);
        Primo p = new Primo();
        //InterpreteWrapper<Integer> w = new InterpreteWrapper<Integer>(a);
        Resultado res = m.chequear(new EX(new EX(p)));
        String r = res.getResultado();
        System.out.println("El resultado de Ex es: " + r);
        JFrame frame = new JFrame();        
        DefaultMutableTreeNode nodo;
        if (res.equals(Resultado.COD_TRUE)){
          nodo = res.getEjemplo().getArbolTreeNode();
        } else {
          nodo = res.getContraejemplo().getArbolTreeNode();  
        }
        System.out.println("tiene hijos: " + nodo.children().hasMoreElements());
        DefaultTreeModel modelo = new DefaultTreeModel(nodo);        
        frame.setContentPane(new JScrollPane(new JTree(modelo)));
        frame.setBounds(0, 0, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);        
    }
    
}
