package com.senior_course.practice.map;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.HashMap;
import java.util.Map;

public class MyHashMapPerformanceTest {
    @Test
    void testMap1Times() throws InterruptedException {
        System.out.println("====== Standard Map ======");
        testStdMap(1, 1000);
        long elapsedTimeStdMap = testStdMap(100, 1000000);

        Thread.sleep(500);
        System.out.println("====== My Map ======");
        testMyMap(1, 1000);
        long elapsedTimeMyMap = testMyMap(100, 1000000);

        System.out.println("performance different compare to my map : " + (elapsedTimeMyMap - elapsedTimeStdMap)/((float) elapsedTimeStdMap)*100 + "%");
    }

    @Test
    void testMap10Times() throws InterruptedException {
        for (int i = 0; i < 10; i++){
            System.out.println("====== Standard Map ======");
            testStdMap(1, 1000);
            long elapsedTimeStdMap = testStdMap(100, 1000000);

            Thread.sleep(500);
            System.out.println("====== My Map ======");
            testMyMap(1, 1000);
            long elapsedTimeMyMap = testMyMap(100, 1000000);

            System.out.println("performance different compare to my map : " + (elapsedTimeMyMap - elapsedTimeStdMap)/((float) elapsedTimeStdMap)*100 + "%");
        }
    }

    private long testMyMap(int loop, int size) {
        long start = System.currentTimeMillis();
        MyHashMap<String, Integer> map = null;
        for (int i = 0; i < loop; i++) {
            map = new MyHashMap<>();
            for (int j = 0; j < size; j++) {
                map.put("" + j, j);
                if (map.get("" + j) != j) {
                    System.out.println("Error: " + j);
                }
            }
        }

        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time: " + elapsedTime + " ms");
//        System.out.println("Object size: " + GraphLayout.parseInstance(map).totalSize() + " bytes");
        System.out.println();

        return elapsedTime;
    }

    private long testStdMap(int loop, int size) {
        long start = System.currentTimeMillis();
        Map<String, Integer> map = null;
        for (int i = 0; i < loop; i++) {
            map = new HashMap<>(size * 2);
            for (int j = 0; j < size; j++) {
                map.put("" + j, j);
                if (map.get("" + j) != j) {
                    System.out.println("Error: " + j);
                }
            }
        }

        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time: " + elapsedTime + " ms");
//        System.out.println("Object size: " + GraphLayout.parseInstance(map).totalSize() + " bytes");
        System.out.println();

        return elapsedTime;
    }
}
