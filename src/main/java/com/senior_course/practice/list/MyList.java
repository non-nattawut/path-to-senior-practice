package com.senior_course.practice.list;

import java.util.Collection;

public class MyList<T> implements InterfaceMyList<T> {
    private final int INITIAL_SIZE = 16;
    private final int RESIZE_MULTIPLIER = 2;

    private int lastIndex = 0;
    private T[] data;

    public MyList() {
        initSize(INITIAL_SIZE);
    }

    public MyList(T[] data) {
        int size = (data.length/INITIAL_SIZE + 1) * INITIAL_SIZE;
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
    public MyList<T> map() {
        return null;
    }

    @Override
    public MyList<T> filter() {
        return null;
    }

    static <T> MyList<T> of(T... data) {
        return new MyList<>(data);
    }

    static <T> MyList<T> of(Collection<T> data) {
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
