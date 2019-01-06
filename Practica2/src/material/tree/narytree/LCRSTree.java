package material.tree.narytree;

import material.Position;
import material.tree.iterators.BFSIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 * @param <E> the elements stored in the tree
 */
public class LCRSTree<E> implements NAryTree<E> {

	/**
     * Inner class which represents a node of the tree
     *
     * @param <T> the type of the elements stored in a node
     */
	private class LCRSNode<T> implements Position<T> {
		private T element; // The element stored in the position
        private LCRSNode<T> parent; // The parent of the node
		private LCRSNode<T> leftChild; // The left child of the node
        private LCRSNode<T> rightSibling; // The right sibling of the node
        private LCRSTree<T> myTree; // A reference to the tree where the node belongs
		
        @Override
		public T getElement() {
			// TODO Auto-generated method stub
			return element;
		}
        
        public LCRSNode(LCRSTree<T> t, T e, LCRSNode<T> p, LCRSNode<T> l, LCRSNode<T> r) {
        	this.myTree = t;
        	this.element = e;
        	this.parent = p;
        	this.leftChild = l;
        	this.rightSibling = r;
        }
        
		public LCRSNode<T> getParent() {
			return parent;
		}

		public void setParent(LCRSNode<T> parent) {
			this.parent = parent;
		}

		public LCRSNode<T> getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(LCRSNode<T> leftChild) {
			this.leftChild = leftChild;
		}

		public LCRSNode<T> getRightSibling() {
			return rightSibling;
		}

		public void setRightSibling(LCRSNode<T> rightSibling) {
			this.rightSibling = rightSibling;
		}

		public LCRSTree<T> getMyTree() {
			return myTree;
		}

		public void setMyTree(LCRSTree<T> myTree) {
			this.myTree = myTree;
		}

		public void setElement(T element) {
			this.element = element;
		}
        

	}

	private LCRSNode<E> root; // The root of the tree
    private int size; // The number of nodes in the tree
    
    public LCRSTree() {
    	this.root = null;
    	this.size = 0;
    }
    
    @Override
    public int size() {
    	return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Position<E> root() throws RuntimeException {
    	if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        if(this.root == node) {
        	throw new RuntimeException("The node has not parent");
        }
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        LCRSNode<E> parent = checkPosition(v);
        List<LCRSNode<E>> l = new ArrayList<>();
    	LCRSNode<E> node = parent.getLeftChild();
        if(node!=null) {
        	l.add(node);
        	while(node.getRightSibling()!=null) {
        		node = node.getRightSibling();
        		l.add(node);
        	}
        }
        return l;
    }

    @Override
    public boolean isInternal(Position<E> v) {
    	LCRSNode<E> node = checkPosition(v);
    	return !isLeaf(node);
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        List<LCRSNode<E>> children = (List<LCRSTree<E>.LCRSNode<E>>) this.children(node); 
        return (children.size() == 0);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return (node == this.root);
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
    	if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new LCRSNode<E>(this, e, null, null, null);
        return root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    @Override
    public E replace(Position<E> p, E e) {
    	LCRSNode<E> node = checkPosition(p);
    	E element = p.getElement();
    	node.setElement(e);
    	return element;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
    	LCRSNode<E> node1 = checkPosition(p1);
    	LCRSNode<E> node2 = checkPosition(p2);
    	E temp = node1.getElement();
    	node1.setElement(node2.getElement());
    	node2.setElement(temp);
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
    	LCRSNode<E> parent = checkPosition(p);
    	LCRSNode<E> node = new LCRSNode<>(this, element, parent, null, null);
    	if(parent.getLeftChild()==null) {
    		parent.setLeftChild(node);
    	}else {
    		List<LCRSNode<E>> l = (List<LCRSTree<E>.LCRSNode<E>>) children(p);
        	LCRSNode<E> sibling = null;
        	for(LCRSNode<E> e: l) {
        		if(e.getRightSibling() == null)
        			sibling = e;
        	}
        	sibling.setRightSibling(node);
    	}
    	this.size++;
    	return node;
    }

    @Override
    public void remove(Position<E> p) {
        LCRSNode<E> node = checkPosition(p);
        LCRSNode<E> parent = node.getParent();
        if(parent == null) {
        	this.root = null;
        	this.size = 0;
        }else {
        	LCRSNode<E> aux = parent.getLeftChild();
            Iterator<Position<E>> it = new BFSIterator<>(this, p);
	        if(aux == node)
	        	parent.setLeftChild(node.getRightSibling());
	        else {
	        	while(aux.getRightSibling()!=node) {
	        		aux = aux.getRightSibling();
	        	}
	        	LCRSNode<E> nodeRight = node.getRightSibling();
	        	aux.setRightSibling(nodeRight);
	        }
	        while(it.hasNext()) {
	        	LCRSNode<E> n = (LCRSTree<E>.LCRSNode<E>) it.next();
	        	n.setMyTree(null);
	        	this.size--;
	        }
	        node.setParent(null);  
        }      
    }
    
    private LCRSNode<E> checkPosition(Position<E> p)
            throws IllegalStateException {
        if (p == null || !(p instanceof LCRSNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        LCRSNode<E> aux = (LCRSNode<E>) p;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
    	LCRSNode<E> o = checkPosition(pOrig);
    	LCRSNode<E> d = checkPosition(pDest);
    	Iterator<Position<E>> it = this.iterator();
    	boolean found = false;
    	Position<E> auxPos;
    	while(it.hasNext()) {
    		auxPos = it.next();
    		if(auxPos.equals(o)) {
    			found = true;
    		}
    		if(found && auxPos.equals(d)) {
	        	throw new RuntimeException("The destination node cannot be a subtree of the origin node");
			}
    	}
    	LCRSNode<E> parent = o.getParent();
    	LCRSNode<E> aux = parent.getLeftChild();
    	if(aux == o) {
    		parent.setLeftChild(aux.getRightSibling());
    	}else {
    		while(aux.getRightSibling() != o) {
    			aux = aux.getRightSibling();
    		}
    		aux.setRightSibling(o.getRightSibling());
    	}
    	if(d.getLeftChild() == null) {
    		d.setLeftChild(o);
    	}else {
    		aux = d.getLeftChild();
    		while(aux.getRightSibling()!=null) {
    			aux = aux.getRightSibling();
    		}
    		aux.setRightSibling(o);
    	}
    	o.setParent(d);
		o.setRightSibling(null);
    }
}
