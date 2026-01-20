package com.senior_course.practice.list;

import java.util.function.Function;
import java.util.function.Predicate;

interface InterfaceMyList<T> {
    abstract void add(T data);

    abstract T get(int idx) ;

    abstract void remove(int idx);

    abstract <R> InterfaceMyList<R> map(Function<? super T, ? extends R> mapper);

    abstract InterfaceMyList<T> filter(Predicate<? super T> predicate);
}
