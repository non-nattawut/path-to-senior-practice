package com.senior_course.practice.exception.map;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(Object key) {
        super("Key not found : " + key);
    }
}
