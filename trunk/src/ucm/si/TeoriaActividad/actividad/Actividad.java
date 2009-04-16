/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucm.si.TeoriaActividad.actividad;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import ucm.si.TeoriaActividad.item.Item;

/**
 *
 * @author José Antonio
 */
public class Actividad implements Comparable<Actividad> {

    private Item[] Subjects;
    private Item[] Objects;
    private Item[] Objetives;
    private Item[] Tools;
    private Item[] Outcomes;
    private Conditions[] condiciones;
    private Actividad padre = null;
    private TreeSet<Actividad> actividadesHijas = new TreeSet<Actividad>();
    private String nombre;
    private Item[] itemNecesarios;
    private Item[] itemToDispose;
    private Item[] itemToGenerate;

    public Actividad(String nombre,Item[] Subjects, Item[] Objects, Item[] Objetives, Item[] Tools, Item[] Outcomes, Item[] itemToDispose,Item[] itemToGenerate, Conditions[] condiciones) {
        this.Subjects = Subjects;
        this.Objects = Objects;
        this.Objetives = Objetives;
        this.Tools = Tools;
        this.Outcomes = Outcomes;
        this.condiciones = condiciones;
        this.nombre = nombre;
        this.itemToDispose = itemToDispose;
        this.itemToGenerate = itemToGenerate;
        Set<Item> setaux = new TreeSet<Item>();
        for (int i = 0; i < Subjects.length; i++) {
            Item item = Subjects[i];
            setaux.add(item);
        }
        for (int i = 0; i < Objects.length; i++) {
            Item item = Objects[i];
            setaux.add(item);
        }
        for (int i = 0; i < Tools.length; i++) {
            Item item = Tools[i];
            setaux.add(item);
        }
        for (int i = 0; i < itemToDispose.length; i++) {
            Item item = itemToDispose[i];
            setaux.add(item);
        }
        this.itemNecesarios = setaux.toArray(new Item[0]);
    }



    public Set<String> getAntecesores() {
        Set<String> saux;
        if (padre == null) {
            saux = new TreeSet<String>();
        } else {
            saux = padre.getAntecesores();
            saux.add(padre.getNombre());
        }
        return saux;
    }

    public Conditions[] getCondiciones() {
        return condiciones;
    }

    public boolean CondicionesSatisfy(Contexto contx) {
        boolean b = true;
        int cont = 0;
        int max = this.condiciones.length;
        Conditions c;
        while (b && (cont < max)) {
            c = this.condiciones[cont];
            b = b && c.Cumple(contx);
            cont++;
        }
        return b;
    }

    public void setCondiciones(Conditions[] condiciones) {
        this.condiciones = condiciones;
    }

    public Item[] getItemNecesarios() {
        return itemNecesarios;
    }

    public Item[] getItemToDispose() {
        return itemToDispose;
    }

    public void setItemToDispose(Item[] itemToDispose) {
        this.itemToDispose = itemToDispose;
    }

    public Item[] getItemToGenerate() {
        return itemToGenerate;
    }

    public void setItemToGenerate(Item[] itemToGenerate) {
        this.itemToGenerate = itemToGenerate;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean necesita(Item item) {
        int i = 0;
        boolean encontrado = false;
        while ((i < itemNecesarios.length) && (!encontrado)) {
            encontrado = itemNecesarios[i].equals(item);
            i++;
        }
        return encontrado;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actividad other = (Actividad) obj;
        if (this.Subjects != other.Subjects && (this.Subjects == null || !Arrays.equals(this.Subjects,other.Subjects))) {
            return false;
        }
        if (this.Objects != other.Objects && (this.Objects == null || !Arrays.equals(this.Objects,other.Objects))) {
            return false;
        }
        if (this.Objetives != other.Objetives && (this.Objetives == null || !Arrays.equals(this.Objetives,other.Objetives))) {
            return false;
        }
        if (this.Tools != other.Tools && (this.Tools == null || !Arrays.equals(this.Tools,other.Tools))) {
            return false;
        }
        if (this.Outcomes != other.Outcomes && (this.Outcomes == null || !Arrays.equals(this.Outcomes,other.Outcomes))) {
            return false;
        }
        if (this.condiciones != other.condiciones && (this.condiciones == null || !Arrays.equals(this.condiciones,other.condiciones))) {
            return false;
        }
        if (this.padre != other.padre && (this.padre == null || !this.padre.equals(other.padre))) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.itemNecesarios != other.itemNecesarios && (this.itemNecesarios == null || !Arrays.equals(this.itemNecesarios,other.itemNecesarios))) {
            return false;
        }
        if (this.itemToDispose != other.itemToDispose && (this.itemToDispose == null || !Arrays.equals(this.itemToDispose,other.itemToDispose))) {
            return false;
        }
        if (this.itemToGenerate != other.itemToGenerate && (this.itemToGenerate == null || !Arrays.equals(this.itemToGenerate,other.itemToGenerate))) {
            return false;
        }
        return true;
    }

  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.itemNecesarios != null ? this.itemNecesarios.hashCode() : 0);
        hash = 37 * hash + (this.itemToDispose != null ? this.itemToDispose.hashCode() : 0);
        hash = 37 * hash + (this.itemToGenerate != null ? this.itemToGenerate.hashCode() : 0);
        hash = 37 * hash + (this.condiciones != null ? this.condiciones.hashCode() : 0);
        return hash;
    }

    public int compareTo(Actividad arg0) {
        return this.getNombre().compareTo(arg0.getNombre());
    }

    @Override
    public String toString() {
        return this.getNombre();
    }

    public Set<Actividad> getActividadesHijas() {
        return actividadesHijas;
    }

    public void addActividadHija(Actividad actividad) {
        this.actividadesHijas.add(actividad);
        actividad.padre = this;
    }

    public Actividad getPadre() {
        return padre;
    }

    public Item[] getObjects() {
        return Objects;
    }

    public Item[] getObjetives() {
        return Objetives;
    }

    public Item[] getOutcomes() {
        return Outcomes;
    }

    public Item[] getSubjects() {
        return Subjects;
    }

    public Item[] getTools() {
        return Tools;
    }
}
