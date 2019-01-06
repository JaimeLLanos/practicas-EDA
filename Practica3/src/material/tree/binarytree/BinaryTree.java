package material.tree.binarytree;

import material.Position;
import material.tree.*;

/**
 * An interface for a binary tree, where each node can have zero, one, or two
 * children.
 *
 * @author A. Duarte, J. Vélez
 * @param <E>
 */
public interface BinaryTree<E> extends Tree<E>, Iterable<Position<E>> {

    /**
     * Returns the left child of a node.
     *
     * @param v
     * @return
     */
    public Position<E> left(Position<E> v) throws RuntimeException;

    /**
     * Returns the right child of a node.
     *
     * @param v
     * @return
     */
    public Position<E> right(Position<E> v) throws RuntimeException;

    /**
     * Returns whether a node has a left child.
     *
     * @param v
     * @return
     */
    public boolean hasLeft(Position<E> v);

    /**
     * Returns whether a node has a right child.
     *
     * @param v
     * @return
     */
    public boolean hasRight(Position<E> v);

    /**
     * Replaces the content of position p
     * @param p
     * @param e
     * @return the element previously contained in position p
     */
    public E replace(Position<E> p, E e);

    /**
     * Return the sibling of a node
     *
     * @param p
     * @return
     */
    public Position<E> sibling(Position<E> p) throws RuntimeException;


    /**
     * Inserts a left child at a given node.
     *
     * @param p
     * @param e
     * @return
     */
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException;

    /**
     * Inserts a right child at a given node.
     *
     * @param p
     * @param e
     * @return
     */
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException;

    /**
     * Removes a node with zero or one child.
     *
     * @param p
     * @return
     */
    public E remove(Position<E> p) throws RuntimeException;
    
    /**
     * Swap the elements at two nodes
     *
     * @param p1
     * @param p2
     */
    public void swap(Position<E> p1, Position<E> p2);
    
    /**
     * Create un new tree from node v.
     *
     * @param v new root node
     * @return  The new tree.
     */
    public BinaryTree<E> subTree(Position<E> v);
    
    /**
     * Attach tree t as left children of node p
     * @param p
     * @param tree
     */
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException;
    
    /**
     * Attach tree t as right children of node p
     * @param p
     * @param tree
     */
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException;

    /**
     * Checks whether the binary tree is complete or not (every node has two children except leaves)
     * @return true if the tree is complete, false otherwise
     */
    public boolean isComplete();
    
    

}
