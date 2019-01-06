package material.tree.binarytree;

import material.Position;
import material.tree.iterators.InorderBinaryTreeIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
	
	private class ABTPos<T> implements Position<T>{
		private int index;
		private T element;
		
                public ABTPos(int i, T e){
                    setIndex(i);
                    setElement(e);
                }

                @Override
		public T getElement() {
			return element;
		}
		
		public int getIndex() {
			return index;
		}
		
		public void setElement(T element) {
			this.element = element;
		}
		
		public void setIndex(int index) {
			this.index = index;
		}
	}
        
    private ABTPos<E> tree[];
    private int size = 0;
    private int capacity = 23;
    public ArrayBinaryTree(){
        tree = new ABTPos[this.capacity];
    }
        
    public ArrayBinaryTree(int cap){
        tree = new ABTPos[cap];
    }
    
    @Override
	public int size() {
        return this.size;
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public Position<E> root() throws RuntimeException {
		if (size == 0) {
            throw new RuntimeException("The tree is empty");
        }
        return this.tree[0];
	}

	@Override
	public Position<E> parent(Position<E> v) throws RuntimeException {
		if(!isRoot(v)){
            ABTPos<E> pos = checkPosition(v);
            int index = pos.getIndex() / 2;
            ABTPos<E> parent = this.tree[index];
            return parent;
        }else{
            throw new RuntimeException("No parent");
        }
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) {
		ABTPos<E> p = checkPosition(v);
        List<ABTPos<E>> childAux = new ArrayList<>();
        int index = p.getIndex() * 2;
        if(hasLeft(v)){
            childAux.add(this.tree[index + 1]);
        }
        if(hasRight(v)){
            childAux.add(this.tree[index + 2]);
        }
        return childAux;
	}

	@Override
	public boolean isInternal(Position<E> v) {
		checkPosition(v);
        return (hasLeft(v) || hasRight(v));
	}

	@Override
	public boolean isLeaf(Position<E> v) throws RuntimeException {
		checkPosition(v);
        return !isInternal(v);
	}

	@Override
	public boolean isRoot(Position<E> v) {
		ABTPos<E> pos = checkPosition(v);
        return (pos == root());
	}

	@Override
	public Position<E> addRoot(E e) throws RuntimeException {
		if(!isEmpty()){
            throw new RuntimeException("Tree already has a root");
        }
        this.tree[0] = new ABTPos<E>(0,e);
        this.size++;
        return this.tree[0];
	}

	@Override
	public Iterator<Position<E>> iterator() {
		return new InorderBinaryTreeIterator<>(this, tree[0]);
	}
    
    public Position<E> left(Position<E> v) throws RuntimeException {
    	ABTPos<E> p = checkPosition(v);
        if(hasLeft(v)){
            int index = p.getIndex() * 2 + 1;
            return this.tree[index];
        }else{
            throw new RuntimeException("No left child");
        }
	}

    /**
     * Returns the right child of a node.
     *
     * @param v
     * @return
     */
    public Position<E> right(Position<E> v) throws RuntimeException {
    	ABTPos<E> p = checkPosition(v);
        if(hasRight(v)){
            int index = p.getIndex() * 2 + 2;
            return this.tree[index];
        }else{
            throw new RuntimeException("No right child");
        }
	}

    /**
     * Returns whether a node has a left child.
     *
     * @param v
     * @return
     */
    public boolean hasLeft(Position<E> v) {
    	ABTPos<E> p = checkPosition(v);
        int index = p.getIndex() * 2 + 1;
        if (index >= this.capacity)
        	return false;
        return (this.tree[index] != null);
	}

    /**
     * Returns whether a node has a right child.
     *
     * @param v
     * @return
     */
    public boolean hasRight(Position<E> v) {
    	ABTPos<E> p = checkPosition(v);
        int index = p.getIndex() * 2 + 2;
        if (index >= this.capacity)
        	return false;
        return (this.tree[index] != null);
	}

    /**
     * Replaces the content of position p
     * @param p
     * @param e
     * @return the element previously contained in position p
     */
    public E replace(Position<E> p, E e) {
    	ABTPos<E> pAux = checkPosition(p);
        E temp = pAux.getElement();
        pAux.setElement(e);
        return temp;
	}

    /**
     * Return the sibling of a node
     *
     * @param p
     * @return
     */
    public Position<E> sibling(Position<E> p) throws RuntimeException {
    	Position parent = this.parent(p);
        ABTPos<E> sibling = null;
        int index = 0;
        if(parent != null){
            ABTPos<E> aux = checkPosition(parent);
            if(p == this.left(parent)){ //if our node is the left child
                if(this.hasRight(parent)){ //if our node has a right sibling
                    index = aux.getIndex() * 2 + 2; //index of the right brother
                    sibling = this.tree[index];
                }
            }     
            else{
                if(p == this.right(parent)){
                    if(this.hasLeft(parent)){ 
                        index = aux.getIndex() * 2 + 1; //index of the left brother
                        sibling = this.tree[index];
                    }
                }
                    
            }
            return sibling;
        }
        
        throw new RuntimeException("No sibling");
	}


    /**
     * Inserts a left child at a given node.
     *
     * @param p
     * @param e
     * @return
     */
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
    	ABTPos<E> pos = checkPosition(p);
        if(this.hasLeft(p)){
            throw new RuntimeException("Node already has a left child");
        }
        int index = pos.getIndex() * 2 + 1; //index of the new left node
        ABTPos<E> left = new ABTPos<E>(index,e);
        if (index >= this.capacity)
        	return null;
        this.tree[index] = left;
        this.size++;
        return left;
	}

    /**
     * Inserts a right child at a given node.
     *
     * @param p
     * @param e
     * @return
     */
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
    	ABTPos<E> pos = checkPosition(p);
        if(this.hasRight(p)){
            throw new RuntimeException("Node already has a right child");
        }
        int index = pos.getIndex() * 2 + 2; //index of the new right node
        ABTPos<E> right = new ABTPos<E>(index,e);
        if (index >= this.capacity)
        	return null;
        this.tree[index] = right;
        this.size++;
        return right;
	}

    /**
     * Removes a node with zero or one child.
     *
     * @param p
     * @return
     */
    public E remove(Position<E> p) throws RuntimeException {
    	ABTPos<E> node = checkPosition(p);
    	int index = 0;
        ABTPos<E> pos;
        if(this.hasLeft(p) && this.hasRight(p)){ //if the node has two children
            throw new RuntimeException("Cannot remove node with two children");
        }
        else{               	
            if(this.hasLeft(p)){ //if the node has only the left child
            	index = node.getIndex();
                pos = this.tree[index * 2 + 1];                     
            }else if(this.hasRight(p)){ //if the node has only de right child
            	index = node.getIndex();
            	pos = this.tree[index * 2 + 2];
            }else //if the node is a leaf
                pos = null;
        }
        E temp = node.getElement();
        if(pos != null){   
            this.tree[node.getIndex()] = pos;
            index = node.getIndex();
            if(this.hasRight(p) && this.right(p) == pos){                   	
            	this.tree[index * 2 + 2] = null;
            }else{
                this.tree[index * 2 + 1] = null;
            }
            this.size--;
        }else{
            this.tree[node.getIndex()] = null;
            this.size--;
        }             
        return temp;
	}
    
    /**
     * Swap the elements at two nodes
     *
     * @param p1
     * @param p2
     */
    public void swap(Position<E> p1, Position<E> p2) {
    	ABTPos<E> node1 = checkPosition(p1);
        ABTPos<E> node2 = checkPosition(p2);
        int node1Index = node1.getIndex();
        this.tree[node1.getIndex()] = node2;       
        this.tree[node2.getIndex()] = node1;
        node1.setIndex(node2.getIndex());
        node2.setIndex(node1Index);	
    }
    
    private ABTPos<E> checkPosition(Position<E> p) {
        if (p == null || !(p instanceof ABTPos)) {
            throw new RuntimeException("The position is invalid");
        }
        return (ABTPos<E>) p;
    }
    
    /**
     * Create un new tree from node v.
     *
     * @param v new root node
     * @return  The new tree.
     */
    public BinaryTree<E> subTree(Position<E> v) {
    	ABTPos<E> newRoot = checkPosition(v);
        ArrayBinaryTree<E> newTree = new ArrayBinaryTree<>();
        newTree.addRoot(newRoot.getElement());
        InorderBinaryTreeIterator<E> a = new InorderBinaryTreeIterator<>(this, tree[newRoot.getIndex()]);
        while(a.hasNext()){
            Position<E> aux = a.next();
            if(!(aux==newRoot)){
            	Position<E> parent = this.parent(aux);
                if(parent != null && this.hasLeft(parent) && this.left(parent) == aux){ //if the node is a left child
                    newTree.insertLeft(parent, aux.getElement());
                }else if(parent != null && this.hasRight(parent) && this.right(parent) == aux){
                    newTree.insertRight(parent, aux.getElement());
                }
            }                
            this.remove(aux);
            this.size--;
        }
        return newTree;
	}
    
    /**
     * Attach tree t as left children of node p
     * @param p
     * @param tree
     */
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
    	ABTPos<E> node = checkPosition(p);
        ArrayBinaryTree<E> treeAux = (ArrayBinaryTree<E>) tree;
        
        if(treeAux == this){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        
        if(treeAux != null && !treeAux.isEmpty()){
            ABTPos<E> aux = checkPosition(treeAux.root());
            this.tree[node.getIndex() * 2 + 1] = aux;
            aux.setIndex(node.getIndex()*2+1);
            this.size += treeAux.size;
            treeAux.tree[0] = null;
        }
	}
    
    /**
     * Attach tree t as right children of node p
     * @param p
     * @param tree
     */
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
    	ABTPos<E> node = checkPosition(p);
        ArrayBinaryTree<E> treeAux = (ArrayBinaryTree<E>) tree;
        
        if(treeAux == this){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        
        if(treeAux != null && !treeAux.isEmpty()){
            ABTPos<E> aux = checkPosition(treeAux.root());
            this.tree[node.getIndex() * 2 + 2] = aux;
            aux.setIndex(node.getIndex()*2+2);
            this.size += treeAux.size;
            treeAux.tree[0] = null;
        }
	}

    /**
     * Checks whether the binary tree is complete or not (every node has two children except leaves)
     * @return true if the tree is complete, false otherwise
     */
    public boolean isComplete() {
    	if(!this.isEmpty()) {
    		Iterator it = this.iterator();
    		while(it.hasNext()) {
    			Position<E> aux = (Position<E>) it.next();
    			if(this.isInternal(aux) || this.isRoot(aux)) {
    				if(!(this.hasLeft(aux) && this.hasRight(aux))) {
    					return false;
    				}
    			}
    		}
    		return true;
    	}else {
    		return false;
    	}
	}
   
}
