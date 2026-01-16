package com.senior_course.practice.map;

public interface MyMap<K, V> {
    boolean put(K key, V value);

    V get(K key);
}
