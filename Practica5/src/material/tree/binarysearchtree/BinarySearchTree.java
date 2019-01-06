/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import java.util.Iterator;

import material.Position;

/**
 *
 * @author A. Duarte, J. Vélez
 * 
 */
public interface BinarySearchTree<E> extends Iterable<Position<E>> {

    /**
     * Returns an entry containing the given key. Returns null if no such entry
     * exists.
     */
    Position<E> find(E value);

    /**
     * Returns an iterable collection of all the entries containing the given
     * key.
     */
    Iterable<? extends Position<E>> findAll(E value);

    /**
     * Inserts an entry into the tree and returns the newly created entry.
     */
    Position<E> insert(E value);

    /**
     * Returns whether the tree is empty.
     */
    boolean isEmpty();


    /**
     * Removes and returns a given entry.
     */
    E remove(Position<E> pos) throws RuntimeException;

    /**
     * Returns the number of entries in the tree.
     */
    int size();

    /*
     * Ejercicio 1: findRange
     */
    /**Find range in binary search trees. */
    public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException;

    /**
     * Ejercicio 2: first, last, successors, predecessors
     */
    public Position<E> first() throws RuntimeException;
    public Position<E> last() throws RuntimeException;
    public Iterable<Position<E>> successors(Position<E> pos);
    public Iterable<Position<E>> predecessors(Position<E> pos);

}
