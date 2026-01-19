package com.senior_course.practice.map;

public class MyHashMap<K, V> implements MyMap<K, V> {
    static final int INITIAL_CAPACITY = 16;
    static final float SCALE_FACTOR = 0.75f;

    int currentCapacity = INITIAL_CAPACITY;
    int occupySlot = 0;

    MyNode<K, V>[] nodes = new MyNode[INITIAL_CAPACITY];

    @Override
    public boolean put(K key, V value) {
        // scale up Node capacity
        if (occupySlot >= currentCapacity * SCALE_FACTOR) {
            scaleCapacity();
        }

        int hashKey = getHashKey(key);
        MyNode<K, V> newNode = new MyNode<>(hashKey, key, value, null);

        // set a new Slot if no slot exist
        if (nodes[hashKey] == null) {
            nodes[hashKey] = newNode;
            occupySlot += 1;
            return true;
        }

        // same key => override value
        // set to last node if key not exist but has the same hash key
        nodes[hashKey].setValueOrNextNode(newNode);

        return false;
    }

    @Override
    public V get(K key) {
        int hashKey = getHashKey(key);
        return nodes[hashKey].getValue(key);
    }

    private int getHashKey(K key) {
        return key.hashCode() % currentCapacity;
    }

    private void scaleCapacity() {
        currentCapacity *= 2;
        occupySlot = 0;

        MyNode<K, V>[] oldNodes = nodes;
        nodes = new MyNode[currentCapacity];

        for (MyNode<K, V> oldNode : oldNodes) {
            if (oldNode == null)
                continue;

            MyNode<K, V> nextNode = oldNode;
            while (nextNode != null) {
                put(nextNode.key, nextNode.value);
                nextNode = nextNode.next;
            }
        }
    }

//    // Test purpose only
//    public MyNode<K, V> getNode(K key) {
//        return nodes[getHashKey(key)].getNodeByKey(key);
//    }

    // Visualize the map
    public void visualization(){
        for (MyNode<K, V> node : nodes) {
            if (node == null)
                continue;

            System.out.print("hashKey : " + node.hashKey + " : ");

            MyNode<K, V> nextNode = node;
            while (nextNode != null) {
                System.out.print(nextNode.key + "/" + nextNode.value + (nextNode.next != null ? " --> " : ""));
                nextNode = nextNode.next;
            }

            System.out.println();
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

        void setValueOrNextNode(MyNode<K, V> newNode) {
            if (this.key == newNode.key) { // same key => override value
                this.value = newNode.value;
            } else if (this.next == null) { // set to last node if key not exist but has the same hash key
                this.next = newNode;
            } else {
                this.next.setValueOrNextNode(newNode);
            }
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

//        // Test Purpose only
//        MyNode<K, V> getNodeByKey(K key) {
//            if (this.key == key || this.next == null) {
//                return this;
//            }
//
//            return this.next.getNodeByKey(key);
//        }
//
//        // Test purpose only
//        int getHashKey() {
//            return hashKey;
//        }
    }
}
