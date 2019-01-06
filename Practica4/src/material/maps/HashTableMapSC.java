package material.maps;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public class HashTableMapSC<K, V> implements Map<K, V> {

	private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

	private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
        private int pos = 0;
        private ArrayList<HashEntry<T, U>>[] bucket;
        private Deque<HashEntry<T,U>> nodeQueue;
        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] map, int numElems) {
            this.bucket = map;
            this.nodeQueue = new LinkedList<>();
            if (numElems == 0) {
                this.pos = this.bucket.length;
            } else {
            	goToNextElement(0);
                while(this.pos < this.bucket.length){
                	//if(this.bucket[this.pos] != null) {
	                for(HashEntry<T,U> e: this.bucket[this.pos]){
	                	this.nodeQueue.add(e);
	                }
                	//}
                    goToNextElement(this.pos+1); //with this method, we will have the integer called "pos" with the value of the first occupated slot in the table
                }
            this.pos = 0;
            }
        }

        private void goToNextElement(int start) {
            final int n = bucket.length;
            this.pos = start;
            while ((this.pos < n) && (this.bucket[this.pos].size()==0)) {
                this.pos++;
            }
        }

        @Override
        public boolean hasNext() {
            return (!this.nodeQueue.isEmpty());
        }

        @Override
        public Entry<T, U> next() {
        	if (hasNext()) {
                //goToNextElement(this.pos + 1);
                return this.nodeQueue.pollFirst();          
            } else {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapIterator<T, U> it;
    	
        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
        	this.it = it;
        }

        @Override
        public T next() {
        	return this.it.next().getKey();
        }

        @Override
        public boolean hasNext() {
        	return this.it.hasNext();
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapIterator<T, U> it;
    	
        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
        	this.it = it;
        }

        @Override
        public U next() {
        	return this.it.next().getValue();
        }

        @Override
        public boolean hasNext() {
        	return this.it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /**
     * Creates a hash table
     */
    
    protected int n; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected ArrayList<HashEntry<K, V>>[] bucket;// bucket array

    public HashTableMapSC() {
        this(109345121, 1000); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        this(109345121, cap); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
    	this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];// safe cast
        for(int i = 0; i<cap; i++) {
        	this.bucket[i] = new ArrayList<>();
        }
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
    	return n;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
    	return (n == 0);
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws IllegalStateException {
    	checkKey(key);
        int HKey = hashValue(key);
        ArrayList<HashEntry<K, V>> aux = this.bucket[HKey];
        if(this.bucket[HKey] == null){
            return null;
        }else{
            for(HashEntry<K, V> e: aux){
                if(e.getKey().equals(key)){
                    return e.getValue();
                }
            }
            return null;
        }
    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
        checkKey(key);
        if(this.n> this.capacity*0.75){
            rehash();
    	}
    	int HKey = this.hashValue(key);
    	HashEntry<K,V> e = new HashEntry(key, value);
    	if(this.bucket[HKey] == null) {
    		ArrayList<HashEntry<K,V>> aux = new ArrayList<>();
    		aux.add(e);
    		this.bucket[HKey] = aux;
    	}else {
    		for(HashEntry<K, V> x: this.bucket[HKey]){
                if(x.getKey().equals(key)){ 
                    V oldValue = x.getValue();
                    x.setValue(value);
                    return oldValue;
                }
            }
    		this.bucket[HKey].add(e);
    	}
    	this.n++;
    	return null;
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {
        checkKey(key);
        int HKey = this.hashValue(key);
        if(this.bucket[HKey] != null) {
        	for(HashEntry<K, V> e: this.bucket[HKey]){
                if(e.getKey().equals(key)){
                    V oldValue = e.getValue();
                    this.bucket[HKey].remove(e);
                    this.n--;
                    return oldValue;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<>(this.bucket,this.n);
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
    	return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapKeyIterator<K,V>(new HashTableMapIterator<>(bucket, n));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
    	return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapValueIterator<K,V>(new HashTableMapIterator<>(bucket, n));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K, V>>() {
            public Iterator<Entry<K, V>> iterator() {
                return new HashTableMapIterator<K, V>(bucket, n);
            }
        };
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        // We cannot check the second test (i.e., k instanceof K) since we do not know the class K
        if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    
    
    
    
    protected void rehash() {
        ArrayList<HashEntry<K, V>>[] old = bucket;
    	capacity = capacity * 2;
        // new bucket is twice as big
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        java.util.Random rand = new java.util.Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (ArrayList<HashEntry<K, V>> e : old) {
            if (e != null) { // a valid entry
                for(HashEntry<K, V> aux: e){
                    K key = aux.getKey();
                    V value = aux.getValue();
                    this.put(key, value);
                }
            }
        }
    }
}
