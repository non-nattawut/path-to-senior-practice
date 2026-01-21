package com.senior_course.practice.list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MyListPerformanceTest {
    @Test
    void testNewAddRemovePerformance() throws InterruptedException {
        testStdListNewAddRemove(1, 1000);
        testMyListNewAddRemove(1, 1000);
        Thread.sleep(500);
        testStdListNewAddRemove(20, 100000);
        testMyListNewAddRemove(20, 100000);
    }

    @Test
    void testMapFilterPerformance() throws InterruptedException {
        testStdListMapFilter(1, 1000);
        testMyListMapFilter(1, 1000);
        Thread.sleep(500);
        testStdListMapFilter(100, 5000000);
        testMyListMapFilter(100, 5000000);

    }

    void testMyListMapFilter(int loop, int size) {
        MyList<Integer> lList = new MyList<>();
        for (int i = 0; i < size; i++) {
            lList.add(i);
        }
        long start = System.currentTimeMillis();
        MyList<String> list = null;
        for (int i = 0; i < loop; i++) {
            list = lList.filter(x -> x % 10 == 0).map(x -> "0" + x);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
//        System.out.println("Object size: " + GraphLayout.parseInstance(list).totalSize() + " bytes");
        System.out.println();
    }

    void testStdListMapFilter(int loop, int size) {
        List<Integer> lList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lList.add(i);
        }
        long start = System.currentTimeMillis();
        List<String> list = null;
        for (int i = 0; i < loop; i++) {
            list = lList.stream().filter(x -> x % 10 == 0).map(x -> "0" + x).toList();
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
//        System.out.println("Object size: " + GraphLayout.parseInstance(list).totalSize() + " bytes");
        System.out.println();
    }

    void testMyListNewAddRemove(int loop, int size) {
        List<Integer> lList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lList.add(i);
        }

        long start = System.currentTimeMillis();
        MyList<Integer> list = MyList.of(lList);
        for (int i = 0; i < loop; i++) {
             list = MyList.of(lList);
            for (int j = 0; j < size; j++) {
                list.add(j);
            }
            for (int j = 0; j < size; j++) {
                list.remove(size);
            }
            for (int j = 0; j < size; j++) {
                if (list.get(j) != j) {
                    System.out.println("Error: " + j);
                }
            }
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
//        System.out.println("Object size: " + GraphLayout.parseInstance(list).totalSize() + " bytes");
        System.out.println();
    }

    void testStdListNewAddRemove(int loop, int size) {
        List<Integer> lList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lList.add(i);
        }
        long start = System.currentTimeMillis();
        List<Integer> list;
        for (int i = 0; i < loop; i++) {
            list = new ArrayList<>(lList);
            for (int j = 0; j < size; j++) {
                list.add(j);
            }
            for (int j = 0; j < size; j++) {
                list.remove(size);
            }
            for (int j = 0; j < size; j++) {
                if (list.get(j) != j) {
                    System.out.println("Error: " + j);
                }
            }
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start));
//        System.out.println("Object size: " + GraphLayout.parseInstance(list).totalSize() + " bytes");
        System.out.println();
    }
}
