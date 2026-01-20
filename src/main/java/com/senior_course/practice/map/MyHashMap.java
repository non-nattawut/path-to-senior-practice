package com.senior_course.practice.map;

import com.senior_course.practice.exception.map.KeyNotFoundException;

public class MyHashMap<K, V> implements MyMap<K, V> {
    static final int INITIAL_CAPACITY = 1 << 4; // 16
    static final float SCALE_FACTOR = 0.5f; // 0.75f is slower in my code

    int currentCapacity = INITIAL_CAPACITY;
    int occupySlot = 0;

    MyNode<K, V>[] nodes = new MyNode[INITIAL_CAPACITY];

    @Override
    public void put(K key, V value) {
        int hashKey = getHashKey(key);
        MyNode<K, V> newNode = new MyNode<>(key, value, null);

        // set a new Slot if no slot exist
        if (nodes[hashKey] == null) {
            nodes[hashKey] = newNode;
            ++occupySlot;

            // scale up Node capacity
            if (occupySlot >= currentCapacity * SCALE_FACTOR) {
                scaleCapacity();
            }

            return;
        }

        // same key => override value
        // set to last node if key not exist but has the same hash key
        nodes[hashKey].setValueOrNextNode(newNode);
    }

    @Override
    public V get(K key) {
        int hashKey = getHashKey(key);

        if (nodes[hashKey] == null)
            throw new KeyNotFoundException(key);

        // return MyNode give better performance than return value directly
        return nodes[hashKey].getNodeByKey(key).value;
    }

    private int getHashKey(K key) {
        // better performance than return Math.abs(key.hashCode()) % currentCapacity;
        return key.hashCode() & (currentCapacity - 1);
    }

    private void scaleCapacity() {
        currentCapacity = currentCapacity << 2; // better performance than currentCapacity *= 2
        occupySlot = 0;

        MyNode<K, V>[] oldNodes = nodes;
        nodes = new MyNode[currentCapacity];

        for (MyNode<K, V> oldNode : oldNodes) {
            MyNode<K, V> currentOldNode = oldNode;
            while (currentOldNode != null) {
                MyNode<K, V> nextNodeOld = currentOldNode.next;
                int newHashKey = getHashKey(currentOldNode.key);

                if (nodes[newHashKey] == null) {
                    ++occupySlot;
                }

                currentOldNode.next = nodes[newHashKey];
                nodes[newHashKey] = currentOldNode;

                currentOldNode = nextNodeOld;
            }
        }
    }

    // Visualize the map
    public void visualization() {
        for (MyNode<K, V> node : nodes) {
            if (node == null)
                continue;

            System.out.printf("hashKey : %5s : ", getHashKey(node.key));

            MyNode<K, V> nextNode = node;
            while (nextNode != null) {
                System.out.print(nextNode.key + "/" + nextNode.value + (nextNode.next != null ? " --> " : ""));
                nextNode = nextNode.next;
            }

            System.out.println();
        }
    }

    public static class MyNode<K, V> {
        K key;
        V value;
        MyNode<K, V> next;

        public MyNode(K key, V value, MyNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        void setValueOrNextNode(MyNode<K, V> newNode) {
            MyNode<K, V> currentNode = this;
            K key = newNode.key;

            while (true) {
                if (currentNode.key.equals(key)) { // same key => override value
                    currentNode.value = newNode.value;
                    break;
                } else if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else { // set to last node if key not exist but has the same hash key
                    newNode.next = this.next;
                    this.next = newNode;
                    break;
                }
            }
        }

        MyNode<K, V> getNodeByKey(K key) {
            MyNode<K, V> currentNode = this;

            while (currentNode != null) {
                if (currentNode.key.equals(key))
                    return currentNode;

                currentNode = currentNode.next;
            }

            throw new KeyNotFoundException(key);
        }
    }
}
