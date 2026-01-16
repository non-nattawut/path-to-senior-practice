package com.senior_course.practice.map;

public class MyHashMap<K, V> implements MyMap<K, V> {
    static final int INITIAL_CAPACITY = 16;
    static final float SCALE_FACTOR = 0.15f;

    int currentCapacity = INITIAL_CAPACITY;
    int occupyNode = 0;

    MyNode<K, V>[] myNode = new MyNode[INITIAL_CAPACITY];

    @Override
    public boolean put(K key, V value) {
        // scale up Node capacity
        if (occupyNode >= currentCapacity * SCALE_FACTOR) {
            scaleCapacity();
        }

        int hashKey = getHashKey(key);
        MyNode<K, V> newNode = new MyNode<>(hashKey, key, value, null);

        // set a new Node if no node exist
        if (myNode[hashKey] == null) {
            myNode[hashKey] = newNode;
            occupyNode += 1;
            return true;
        }

        MyNode<K, V> existNode = myNode[hashKey].getNodeByKey(key);
        if (existNode != null) {
            if (existNode.key == newNode.key) {
                // same key => override value
                existNode.setValue(value);
            } else {
                // set to last node if key not exist but has the same hash key
                existNode.setNext(newNode);
            }

            return true;
        }

        return false;
    }

    @Override
    public V get(K key) {
        int hashKey = getHashKey(key);
        return myNode[hashKey].getValue(key);
    }

    // Test purpose only
    public MyNode<K, V> getNode(K key) {
        return myNode[getHashKey(key)].getNodeByKey(key);
    }

    private int getHashKey(K key) {
        return key.hashCode() % currentCapacity;
    }

    private void scaleCapacity() {
        currentCapacity *= 2;
        occupyNode = 0;

        MyNode<K, V>[] oldNodes = myNode;
        myNode = new MyNode[currentCapacity];

        for (MyNode<K, V> oldNode : oldNodes) {
            if (oldNode == null)
                continue;

            MyNode<K, V> nextNode = oldNode;
            while (nextNode != null){
                put(nextNode.key, nextNode.value);
                nextNode = nextNode.next;
            }
        }
    }

    public class MyNode<K, V> {
        final int hashKey;
        K key;
        V value;
        MyNode<K, V> next;

        public MyNode(int hashKey, K key, V value, MyNode<K, V> next) {
            this.hashKey = hashKey;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        // Test purpose only
        int getHashKey() {
            return hashKey;
        }

        void setValue(V value) {
            this.value = value;
        }

        V getValue(K key) {
            if (this.key == key) {
                return value;
            }

            if (next != null) {
                return next.getValue(key);
            }

            throw new RuntimeException("Key not found : " + key);
        }

        MyNode<K, V> getNodeByKey(K key) {
            if (this.key == key || this.next == null) {
                return this;
            }

            return this.next.getNodeByKey(key);
        }

        void setNext(MyNode<K, V> next) {
            this.next = next;
        }
    }
}
