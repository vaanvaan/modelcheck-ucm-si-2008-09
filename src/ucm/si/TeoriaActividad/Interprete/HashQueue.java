/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.TeoriaActividad.Interprete;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author José Antonio
 */
public class HashQueue<T> implements Queue<T>
{
    private HashMap<T,T> map;
    private Queue<T> queue;

    public HashQueue()
    {
        this.map = new HashMap<T, T>();
        this.queue = new ArrayDeque<T>();
    }

    public boolean add(T e) {
        //throw new UnsupportedOperationException("Not supported yet.");

        this.map.put(e, e);
        return this.queue.add(e);
    }

    public boolean offer(T e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.queue.offer(e);
    }

    public T remove() {
        //throw new UnsupportedOperationException("Not supported yet.");

        T e = this.queue.remove();
        this.map.remove(e);
        return e;
    }

    public T poll() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.queue.poll();
    }



    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    public Object[] toArray() {
        return queue.toArray();
    }

    public int size() {
        return queue.size();
    }

    public Iterator<T> iterator() {
        return queue.iterator();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public T element()
    {
        return this.queue.element();

    }

    public T peek()
    {
        return this.queue.peek();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o)
    {

        //throw new UnsupportedOperationException("Not supported yet.");
        return this.map.containsValue(o);
    }

    public boolean remove(Object o) {
        //throw new UnsupportedOperationException("Not supported yet.");
        this.map.remove(o);
        return this.queue.remove(o) ;
    }

    public boolean containsAll(Collection<?> c) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.queue.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        this.queue.clear();
        this.map.clear();
        //throw new UnsupportedOperationException("Not supported yet.");
    }



}
