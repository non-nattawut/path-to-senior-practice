package com.senior_course.practice.list;

import java.util.ArrayList;
import java.util.List;

public class MainListPractice {
    static void main() {
        MyList<Integer> myList = MyList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        myList.print();

        myList.remove(0);
        myList.remove(1);
        myList.print();

        myList = MyList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        myList.remove(3);
        myList.add(12);
        myList.print();

        MyList<String> listStr = myList.filter(m -> (m % 2) == 0).map(Integer::toHexString);
        listStr.print();

        System.out.println(myList.get(0));
    }
}
