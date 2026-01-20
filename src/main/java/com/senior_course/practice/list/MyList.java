package com.senior_course.practice.list;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

public class MyList<T> implements InterfaceMyList<T> {
    private final int INITIAL_SIZE = 16;
    private final int RESIZE_MULTIPLIER = 2;

    private int lastIndex = 0;
    private T[] data;

    public MyList() {
        initSize(INITIAL_SIZE);
    }

    public MyList(T[] data) {
        int size = (data.length / INITIAL_SIZE + 1) * INITIAL_SIZE;
        initSize(size);
        lastIndex = data.length;

        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    @Override
    public void add(T data) {

        if (lastIndex + 1 == this.data.length) {
            resize();
        }

        this.data[lastIndex] = data;
        ++lastIndex;
    }

    @Override
    public T get(int idx) {
        return data[idx];
    }

    @Override
    public void remove(int idx) {
        T[] oldData = data;
        System.arraycopy(oldData, idx + 1, data, idx, oldData.length - idx - 1);
    }

    @Override
    public <R> MyList<R> map(Function<? super T, ? extends R> mapper) {
        MyList<R> newList = new MyList<>();

        for (T d : data) {
            if (d != null){
                R convertedData = mapper.apply(d);
                newList.add(convertedData);
            }
        }

        return newList;
    }

    @Override
    public MyList<T> filter(Predicate<? super T> predicate) {
        MyList<T> newList = new MyList<>();

        for (T d : data) {
            if (d != null && predicate.test(d))
                newList.add(d);
        }

        return newList;
    }

    public static <T> MyList<T> of(T... data) {
        return new MyList<>(data);
    }

    public static <T> MyList<T> of(Collection<T> data) {
        MyList<T> myList = new MyList<>();
        for (T d : data) {
            myList.add(d);
        }

        return myList;
    }

    private void initSize(int size) {
        data = (T[]) new Object[size];
    }

    private void resize() {
        T[] oldData = data;
        data = (T[]) new Object[data.length * RESIZE_MULTIPLIER];
        System.arraycopy(oldData, 0, data, 0, oldData.length);
    }

    // Test only
    public void print() {
        System.out.print("Whole list : ");

        for (T d : data) {
            System.out.print(d + " ");
        }

        System.out.println();
    }
}
