package com.senior_course.practice.map;

import java.util.HashMap;

public class MainMapPractice {
//    static void main(){
//        HashMap<String, String> hashMap = new HashMap<>();
//
//        hashMap.put("test", "eiei");
//        System.out.println(hashMap.get("test"));
//
//        hashMap.put("test", "eiei222");
//        System.out.println(hashMap.get("test"));
//    }

    static void main() {
        System.out.println("=============== key = String, value = int ===============");
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("A", 12);
        myHashMap.put("A", 89173464);
        myHashMap.put("A2", 2222); // same hash key as "A" if scale factor > 20%
        myHashMap.put("B", 9999);
        myHashMap.put("C", 3333);
        myHashMap.put("A11", 0); // same hash key as "A"


        myHashMap.visualization();
        printMyHashMap(myHashMap);

        System.out.println("=============== next round ===============");

        myHashMap.put("A", 7777);
        myHashMap.put("A11", 55555);
        myHashMap.put("D", 44444);
        myHashMap.put("T", 99); // scale up at this point if scale factor = 20%
        myHashMap.visualization();

        System.out.println(myHashMap.get("T"));

        System.out.println("=============== key = int, value = double ===============");
        MyHashMap<Integer, Double> newHashMap = new MyHashMap<>();
        newHashMap.put(20, 2.0);
        newHashMap.put(999, 123123.123);
        newHashMap.put(0, 123123.123);
        newHashMap.put(-1, 123123.123);
        newHashMap.put(-2, 123123.123);
        newHashMap.visualization();

        System.out.println("=============== key = Object, value = Object ===============");
        MyHashMap<Object, Object> newHashMap2 = new MyHashMap<>();
        newHashMap2.put(20, "za");
        newHashMap2.put(40, "Udong");
        newHashMap2.put("test", "testsss");
        newHashMap2.put(21, "eiei");
        newHashMap2.put(-2, 12.2);
        newHashMap2.put("TwoHundred", 15);
        newHashMap2.put("TwoHundred", 200);
        Character character = 'a';
        newHashMap2.put(character, 200);
        int i = 888;
        newHashMap2.put(i, i);
        newHashMap2.visualization();

        printNewHashMap2(newHashMap2, character);
    }

    private static void printNewHashMap2(MyHashMap<Object, Object> newHashMap2, Character character) {
        System.out.println(newHashMap2.get(20));
        System.out.println(newHashMap2.get(40));
        System.out.println(newHashMap2.get("test"));
        System.out.println(newHashMap2.get(21));
        System.out.println(newHashMap2.get(-2));
        System.out.println(newHashMap2.get("TwoHundred"));
        System.out.println(newHashMap2.get(character));
        System.out.println(newHashMap2.get(888));
    }

    private static void printMyHashMap(MyHashMap myHashMap){
        System.out.println("A : " + myHashMap.get("A"));
        System.out.println("A2 : " + myHashMap.get("A2"));
        System.out.println("A11 : " + myHashMap.get("A11"));
        System.out.println("B : " + myHashMap.get("B"));
        System.out.println("C : " + myHashMap.get("C"));
//        System.out.println("===== hash key =====");
//        System.out.println("A : " + myHashMap.getNode("A").getHashKey());
//        System.out.println("A2 : " + myHashMap.getNode("A2").getHashKey());
//        System.out.println("A11 : " + myHashMap.getNode("A11").getHashKey());
//        System.out.println("B : " + myHashMap.getNode("B").getHashKey());
//        System.out.println("C : " + myHashMap.getNode("C").getHashKey());
    }
}
