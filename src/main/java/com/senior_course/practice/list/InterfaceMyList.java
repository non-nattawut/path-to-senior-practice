package com.senior_course.practice.list;

interface InterfaceMyList<T> {
    abstract void add(T data);

    abstract T get(int idx) ;

    abstract void remove(int idx);

    abstract InterfaceMyList<T> map();

    abstract InterfaceMyList<T> filter();
}
