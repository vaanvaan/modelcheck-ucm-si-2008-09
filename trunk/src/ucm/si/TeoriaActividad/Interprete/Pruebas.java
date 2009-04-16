/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.Interprete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import ucm.si.Checker.Interprete;
import ucm.si.Checker.util.StateLabeledList;
import ucm.si.TeoriaActividad.GUI.DrawerActividad;
import ucm.si.TeoriaActividad.actividad.*;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.*;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.*;

/**
 *
 * @author José Antonio
 */
public class Pruebas implements Interprete<EstadoTA>, IInterprete {

    public ItemGenerator itemGen;
    public ActividadGenerator activGen;
    public TreeMap<Integer, Set<String>> conjuntosItems;
    public TreeMap<Integer, Set<String>> conjuntosActividades;
    public String[] actividades;
    public String[] items;
    private ArrayList<String> l;
    private LinkedList<String> actividadesOrdenadas; // ordenadas en pre-orden

    public Pruebas() {
        Item item1 = new Item("1");
        Item item2 = new Item("2");
        Item item3 = new Item("3");
        Item item4 = new Item("4");
        Item item5 = new Item("5");
        Item item6 = new Item("6");

        itemGen = ItemGenerator.getReference();
        Item[] listaItem1 = {item1, item2, item3};
        Item[] listaItem2 = {item1, item2, item4};
        Item[] listaItem3 = {item3, item4, item5};
        Item[] listaItem4 = {item1, item6};

        Actividad actividad1 = new Actividad("A1", listaItem1, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);
        Actividad actividad2 = new Actividad("A2", listaItem2, new Item[0], new Item[0], new Item[0], new Item[0], new Item[]{item4}, new Item[0], new Conditions[0]);
        Actividad actividad3 = new Actividad("A3", listaItem3, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);
        Actividad actividad4 = new Actividad("A4", listaItem4, new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Item[0], new Conditions[0]);
        // Aqui construir el arbol de actividades
        actividad1.addActividadHija(actividad3);
        actividad1.addActividadHija(actividad4);

        activGen = ActividadGenerator.getReference();
        try {
            itemGen.addItem(item1);
            itemGen.addItem(item2);
            itemGen.addItem(item3);
            itemGen.addItem(item4);
            itemGen.addItem(item5);
            itemGen.addItem(item6);
            activGen.addActividad(actividad1);
            activGen.addActividad(actividad2);
            activGen.addActividad(actividad3);
            activGen.addActividad(actividad4);
            actividades = activGen.getConjunto().keySet().toArray(new String[0]);
            items = itemGen.getItems();

            //Ahora creamos el recorrido preorden:
            actividadesOrdenadas = new LinkedList<String>();
            for (int i = 0; i < actividades.length; i++) {
                Actividad a = activGen.getItem(actividades[i]);
                if (a.getPadre() == null) {
                    actividadesOrdenadas.add(a.getNombre());
                }
            }
            int a = 0;
            while (actividadesOrdenadas.size() < actividades.length) {
                Actividad act = activGen.getItem(actividadesOrdenadas.get(a));
                Set<Actividad> setaux = act.getActividadesHijas();
                if (!setaux.isEmpty()) {
                    for (Iterator<Actividad> it = setaux.iterator(); it.hasNext();) {
                        actividadesOrdenadas.addLast(it.next().getNombre());
                    }
                }
                a++;
            }

            // Creamos la tabla para particionar los items en subconjuntos
            // por ahora hacemos para 32 actividades, pero es facil hacerlo generico

            String[] lItems = itemGen.getItems();
            int[] lClaves = new int[lItems.length];
            Actividad[] lacts = activGen.getConjunto().values().
                    toArray(new Actividad[0]);
            for (int j = 0; j < lItems.length; j++) {
                if (lacts[0].necesita(itemGen.getItem(lItems[j]))) {
                    lClaves[j] = 1;
                } else {
                    lClaves[j] = 0;
                }
            }
            int pot2 = 2;
            for (int i = 1; i < lacts.length; i++) {
                for (int j = 0; j < lItems.length; j++) {
                    if (lacts[i].necesita(itemGen.getItem(lItems[j]))) {
                        lClaves[j] = lClaves[j] + pot2;
                    }
                }
                pot2 = pot2 << 1;
            }
            conjuntosItems = new TreeMap<Integer, Set<String>>();
            for (int i = 0; i < lClaves.length; i++) {
                if (!conjuntosItems.containsKey(new Integer(lClaves[i]))) {
                    TreeSet<String> saux = new TreeSet<String>();
                    saux.add(lItems[i]);
                    conjuntosItems.put(new Integer(lClaves[i]), saux);
                } else {
                    conjuntosItems.get(lClaves[i]).add(lItems[i]);
                }
            }

            // Ahora nos quedamos solo con los conjuntos conflictivos
            if (conjuntosItems.containsKey(new Integer(0))) {
                conjuntosItems.remove(new Integer(0));
            }

            // Ahora generamos, para cada conjunto conflictivo, el conjunto 
            // de actividades que lo necesitan.
            conjuntosActividades = new TreeMap<Integer, Set<String>>();
            for (Iterator<Integer> it = conjuntosItems.keySet().iterator(); it.hasNext();) {
                Integer i = it.next();
                //String[] lString = conjuntosItems.get(i).toArray(new String[0]);            
                pot2 = 1;
                for (int j = 1; j <= lacts.length; j++) {
                    if ((pot2 & i) >= 1) {
                        if (!conjuntosActividades.containsKey(i)) {
                            TreeSet<String> hashaux = new TreeSet<String>();
                            hashaux.add(lacts[j - 1].getNombre());
                            conjuntosActividades.put(i, hashaux);
                        } else {
                            conjuntosActividades.get(i).add(lacts[j - 1].getNombre());
                        }
                    }
                    pot2 = pot2 << 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<EstadoTA> iniciales() {
        ListaEstadosActividades lEstAct = new ListaEstadosActividades();
        for (int i = 0; i < actividades.length; i++) {
            lEstAct.addEstado(actividades[i], EstadoActividad.Waiting);
        }
        ListaEstadosItems lEstItems = new ListaEstadosItems();
        for (int i = 0; i < items.length; i++) {
            lEstItems.addEstado(items[i], EstadoItem.FREE);
        }
        EstadoTA estadoIni = new EstadoTA(lEstItems, lEstAct, new TreeMap<String, Set<String>>());
        List<EstadoTA> laux = new ArrayList<EstadoTA>();
        laux.add(estadoIni);
        return laux;
    }

    public List<EstadoTA> transitar(EstadoTA state) {
        EstadoTA estadoini = new EstadoTA(state);
        //terminamos actividades ejecutadas
        for (Iterator<String> it = actividadesOrdenadas.iterator(); it.hasNext();) {
            String a = it.next();
            if (actividadEjecutada(a, estadoini)) {
                estadoini.actividades.setEstado(a, EstadoActividad.Finalized);
                for (Iterator<String> it2 = estadoini.propietarias.get(a).iterator(); it2.hasNext();) {
                    String item = it2.next();
                    if (!itemHeredado(a, item, estadoini)) {
                        estadoini.items.setEstado(item, EstadoItem.FREE);
                    }
                }
                estadoini.propietarias.remove(a);
                Item[] itemsToDispose = activGen.getItem(a).getItemToDispose();
                for (int i = 0; i < itemsToDispose.length; i++) {
                    String item = itemsToDispose[i].getClave();
                    if (!itemHeredado(a, item, estadoini)) {
                        estadoini.items.setEstado(item, EstadoItem.DISPOSED);
                    }
                }
            }
        }
        List<EstadoTA> laux = backtracking(estadoini);
        if (laux.contains(state)) {
            laux.remove(state);
        }
        return laux;
    }

    public StateLabeledList<EstadoTA> transitarConEtiqueta(EstadoTA state) {
        List<EstadoTA> laux = this.transitar(state);
        ArrayList<String> laux2 = new ArrayList<String>();
        for (Iterator<EstadoTA> it = laux.iterator(); it.hasNext();) {
            EstadoTA e = it.next();
            laux2.add(nombreTransicion(state, e));
        }
        return new StateLabeledList<EstadoTA>(laux, laux2);
    }

    public List<String> dameTransiciones() {
        if (l == null) {
            Queue<EstadoTA[]> q = new LinkedBlockingQueue<EstadoTA[]>();
            TreeSet<EstadoTA[]> ts = new TreeSet<EstadoTA[]>(new Comparator() {

                public int compare(Object arg0, Object arg1) {
                    EstadoTA[] c1 = (EstadoTA[]) arg0;
                    EstadoTA[] c2 = (EstadoTA[]) arg1;
                    int a = c1[0].compareTo(c2[0]);
                    if (a != 0) {
                        return a;
                    } else {
                        return c1[1].compareTo(c2[1]);
                    }
                }
            });
            for (EstadoTA e : this.iniciales()) {
                q.add(new EstadoTA[]{null, e});
            }
            l = new ArrayList<String>();
            while (!q.isEmpty()) {
                EstadoTA[] arraye = q.poll();
                EstadoTA epadre = arraye[0];
                EstadoTA ehijo = arraye[1];
                if (epadre == null) {
                    //ts.add(ehijo);
                    for (EstadoTA eaux : transitar(ehijo)) {
                        q.add(new EstadoTA[]{ehijo, eaux});
                    }
                } else {
                    EstadoTA[] trans = new EstadoTA[]{epadre, ehijo};
                    if (!ts.contains(trans)) {
                        String s = nombreTransicion(epadre, ehijo);
                        if (!l.contains(s)) {
                            l.add(s);
                        }
                        ts.add(trans);
                        for (EstadoTA eaux : transitar(ehijo)) {
                            q.add(new EstadoTA[]{ehijo, eaux});
                        }
                    }
                }
            }
        }
        return l;
    }

    public List<EstadoTA> backtracking(EstadoTA estado) {
        TreeMap<String, Set<String>> propietarias = copiaPropietarias(estado.propietarias);
        Integer[] conjItemsConflictivos =
                conjuntosItems.keySet().toArray(new Integer[0]);
        ArrayList<EstadoTA> laux = new ArrayList<EstadoTA>();
        backtracking2(estado, 0, propietarias, conjItemsConflictivos, laux);
        return laux;
    }

    private boolean actividadEjecutada(String a, EstadoTA estadoini) {
        if (estadoini.actividades.getEstado(a).equals(EstadoActividad.Executing)) {
            Set<Actividad> setaux = activGen.getItem(a).getActividadesHijas();
            if (!setaux.isEmpty()) {
                boolean finalizada = true;
                Iterator<Actividad> it = setaux.iterator();
                while (finalizada && it.hasNext()) {
                    Actividad act = it.next();
                    finalizada = estadoini.actividades.getEstado(act.getNombre()).equals(EstadoActividad.Finalized);
                }
                return finalizada;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void backtracking2(EstadoTA eini, int i, TreeMap<String, Set<String>> propietarias,
            Integer[] conjItemsConflictivos, ArrayList<EstadoTA> laux) {
        Integer claveConj = conjItemsConflictivos[i];
        boolean ningunoDisposed = true;
        for (String straux : conjuntosItems.get(claveConj)) {
            if (eini.items.getEstado(straux).equals(EstadoItem.DISPOSED)) {
                ningunoDisposed = false;
            }
        }
        if (ningunoDisposed) {
            String[] conjActs = conjuntosActividades.get(claveConj).toArray(new String[0]);
            boolean adjudicado = false;
            for (int j = 0; j < conjActs.length; j++) {
                String a = conjActs[j];
                TreeSet<String> propaux = new TreeSet<String>(conjuntosItems.get(claveConj));
                Actividad padre = activGen.getItem(a).getPadre();
                boolean padreActivo = false;
                if ((padre == null) ||
                        (eini.getEstadoActividad(padre.getNombre()).equals(EstadoActividad.Executing))) {
                    padreActivo = true;
                }
                if (padreActivo &&
                        (eini.actividades.getEstado(a).equals(EstadoActividad.Waiting))
                        &&puedeUsarlos(a, propaux, eini)) {
                    if (propietarias.containsKey(a)) {
                        propietarias.get(a).addAll(propaux);
                    } else {
                        propietarias.put(a, propaux);
                    }
                    if (i == conjItemsConflictivos.length - 1) { // ya hemos asignado el ultimo
                        EstadoTA estadoaux = new EstadoTA(eini);
                        estadoaux.propietarias = copiaPropietarias(propietarias);
                        estadoaux.lanzarPosibles(this);
                        laux.add(estadoaux);
                    } else {
                        backtracking2(eini, i + 1, propietarias, conjItemsConflictivos, laux);
                    }
                    propietarias.get(a).removeAll(propaux);
                    if (propietarias.get(a).isEmpty()) {
                        propietarias.remove(a);
                    }
                    adjudicado = true;
                }
            }
            if (!adjudicado) {
                //este conjunto ya no es conflictivo porque nadie lo necesita
                if (i == conjItemsConflictivos.length - 1) { // ya hemos asignado el ultimo
                    EstadoTA estadoaux = new EstadoTA(eini);
                    estadoaux.propietarias = copiaPropietarias(propietarias);
                    estadoaux.lanzarPosibles(this);
                    laux.add(estadoaux);
                } else {
                    backtracking2(eini, i + 1, propietarias, conjItemsConflictivos, laux);
                }
            }
        } else {
            if (i == conjItemsConflictivos.length - 1) { // ya hemos asignado el ultimo
                EstadoTA estadoaux = new EstadoTA(eini);
                estadoaux.propietarias = copiaPropietarias(propietarias);
                estadoaux.lanzarPosibles(this);
                laux.add(estadoaux);
            } else {
                backtracking2(eini, i + 1, propietarias, conjItemsConflictivos, laux);
            }
        }
    }

    public static void main(String[] args) {
        /*Pruebas p = new Pruebas();
        List<EstadoTA> l = p.transitar(p.iniciales().iterator().next());
        for (Iterator<EstadoTA> it = l.iterator(); it.hasNext();) {
        EstadoTA e = it.next();
        System.out.println(e);
        }*/
        Pruebas p = new Pruebas();
        Proposicion<EstadoTA> nofin = new Proposicion<EstadoTA>() {

            @Override
            public boolean esCierta(EstadoTA s) {
                return true;//s.actividades.getEstado("A2").equals(EstadoActividad.Waiting);
            }
        };
        Proposicion<EstadoTA> fin = new Proposicion<EstadoTA>() {

            @Override
            public boolean esCierta(EstadoTA s) {
                return false;//s.actividades.getEstado("A2").equals(EstadoActividad.Finalized);
            }
        };

        Formula haycamino = new EU(nofin, fin);
        Formula formula = new Not(haycamino);

        Launcher<EstadoTA> launcher = new Launcher<EstadoTA>(new Contexto(), p, formula);

        launcher.runCheck();

        Drawer dw = new DrawerActividad();
        launcher.launchGrafico(dw);


    }

    private TreeMap<String, Set<String>> copiaPropietarias(TreeMap<String, Set<String>> propietarias) {
        TreeMap<String, Set<String>> propaux = new TreeMap<String, Set<String>>();
        for (Iterator<String> it = propietarias.keySet().iterator(); it.hasNext();) {
            String a = it.next();
            propaux.put(a, new TreeSet<String>(propietarias.get(a)));
        }
        return propaux;
    }

    public String[] getItemsNombre() {
        return items;
    }

    public String[] getActividadesNombre() {
        return actividades;
    }

    public String[] getActividadesHijas(String actividad) {
        TreeSet<String> laux = new TreeSet<String>();
        for (Iterator<Actividad> it = this.activGen.getItem(actividad).
                getActividadesHijas().iterator(); it.hasNext();) {
            Actividad a = it.next();
            laux.add(a.getNombre());
        }
        return laux.toArray(new String[0]);
    }

    private String nombreTransicion(EstadoTA eini, EstadoTA efin) {
        StringBuffer strbuf = new StringBuffer();
        StringBuffer strbuf2 = new StringBuffer();
        for (int a = 0; a < actividades.length; a++) {
            String s = actividades[a];
            if (!eini.getEstadoActividad(s).equals(efin.getEstadoActividad(s))) {
                strbuf.append(s + "->" + efin.getEstadoActividad(s).toString() + ", ");
            }
            if ((eini.getItemsPoseidos(s) == null) ||
                    (efin.getItemsPoseidos(s) == null) ||
                    (!Arrays.equals(eini.getItemsPoseidos(s), efin.getItemsPoseidos(s)))) {
                String[] itemsp = efin.getItemsPoseidos(s);
                if (itemsp != null) {
                    java.util.Arrays.sort(itemsp);
                    strbuf2.append(s + " posee ");
                    for (int i = 0; i < itemsp.length; i++) {
                        strbuf2.append(itemsp[i] + ", ");
                    }
                } else {
                    itemsp = eini.getItemsPoseidos(s);
                    if (itemsp != null) {
                        java.util.Arrays.sort(itemsp);
                        strbuf2.append(s + " suelta ");
                        for (int i = 0; i < itemsp.length; i++) {
                            strbuf2.append(itemsp[i] + ", ");
                        }
                    }

                }
            }
        }
        if ((strbuf.length() == 0) && (strbuf2.length() == 0)) {
            return efin.toString();
        } else {
            return strbuf.append(strbuf2).toString();
        }
    }

    private boolean puedeUsarlos(String a, TreeSet<String> propaux, EstadoTA eini) {
        boolean puede = true;
        for (Iterator<String> it = propaux.iterator(); puede && it.hasNext();) {
            String item = it.next();
            if (eini.items.getEstado(item).equals(EstadoItem.BUSY)) {
                puede = itemHeredado(a, item, eini); //lo tiene un padre
            }
        }
        return puede;
    }

    private boolean itemHeredado(String a, String item, EstadoTA estadoini) {
        Set<String> antecesores = activGen.getItem(a).getAntecesores();
        if (!antecesores.isEmpty()) {
            Iterator<String> it = antecesores.iterator();
            boolean unica = true;
            while (unica && it.hasNext()) {
                String s = it.next();
                if (estadoini.propietarias.containsKey(s)) {
                    unica = !estadoini.propietarias.get(s).contains(item);
                }
            }
            return !unica;
        } else {
            return false;
        }

    }
}
