package material.tree.iterators;

import material.Position;
import material.tree.Tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class PreorderIterator<E> implements Iterator<Position<E>> {

	private final LinkedList<Position<E>> nodeQueue;
    private final Tree<E> tree;
    private Predicate<E> predicate = null;

    public PreorderIterator(Tree<E> tree, Position<E> start) {
    	nodeQueue = new LinkedList<>();
        this.tree = tree;
        nodeQueue.addFirst(start);
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<E> predicate) {
    	nodeQueue = new LinkedList<>();
        this.tree = tree;
        nodeQueue.addFirst(start);
        this.predicate = predicate;
    }

    public PreorderIterator(Tree<E> tree) {
    	nodeQueue = new LinkedList<>();
        this.tree = tree;
        nodeQueue.addFirst(tree.root());    
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    @Override
    public Position<E> next() {
    	if(!this.hasNext())
    		throw new NoSuchElementException();
		Position<E> aux = nodeQueue.pollFirst();
        LinkedList<Position<E>> children = new LinkedList<>();        
        for (Position<E> node : tree.children(aux)) {
        	children.addFirst(node);
        }
        for (Position<E> node : children) {
        	nodeQueue.addFirst(node);
        }
        if(this.predicate != null) {
        	while(!predicate.test(aux.getElement())) {
        		children = new LinkedList<>();
	            aux = nodeQueue.pollFirst();
        		for (Position<E> node : tree.children(aux)) {
	                children.addFirst(node);
	            }
	            for (Position<E> node : children) {
	                nodeQueue.addFirst(node);
	            }
        	}
        	
        }       
        return aux;	    
    }

}
