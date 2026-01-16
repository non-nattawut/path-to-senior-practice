package com.senior_course.practice.map;

import javax.swing.*;
import java.util.HashMap;

public class MainMapPractice {
    static void main() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("A", 12);
        myHashMap.put("A", 89173464);
        myHashMap.put("A2", 2222); // same hash key as "A" if scale factor > 20%
        myHashMap.put("B", 9999);
        myHashMap.put("C", 44444);
        myHashMap.put("A11", 0); // same hash key as "A"

        print(myHashMap);

        System.out.println("=============== next round ===============");

        myHashMap.put("A", 7777);
        myHashMap.put("A11", 55555);
        print(myHashMap);
    }

    private static void print(MyHashMap myHashMap){
        System.out.println("A : " + myHashMap.get("A"));
        System.out.println("A2 : " + myHashMap.get("A2"));
        System.out.println("A11 : " + myHashMap.get("A11"));
        System.out.println("B : " + myHashMap.get("B"));
        System.out.println("C : " + myHashMap.get("C"));
        System.out.println("===== hash key =====");
        System.out.println("A : " + myHashMap.getNode("A").getHashKey());
        System.out.println("A2 : " + myHashMap.getNode("A2").getHashKey());
        System.out.println("A11 : " + myHashMap.getNode("A11").getHashKey());
        System.out.println("B : " + myHashMap.getNode("B").getHashKey());
        System.out.println("C : " + myHashMap.getNode("C").getHashKey());
    }
}
