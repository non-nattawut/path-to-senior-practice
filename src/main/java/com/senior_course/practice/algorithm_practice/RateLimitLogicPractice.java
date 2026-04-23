package com.senior_course.practice.algorithm_practice;

import java.util.*;

public class RateLimitLogicPractice {
    public static void main(String[] args) {
        List<Request> requests = List.of(
                new Request("udong.io", "10.0.0.10"),
                new Request("udong.io", "10.0.0.10"),
                new Request("udong.io", "10.0.0.10"),
                new Request("webai.io", "10.0.0.10"),
                new Request("udong.io", "10.0.0.11"),
                new Request("webai.io", "10.0.0.11"),
                new Request("udong.io", "10.0.0.11"),
                new Request("webai.io", "10.0.0.10"),
                new Request("udong.io", "10.0.0.10"),
                new Request("webai.io", "10.0.0.12"),
                new Request("udong.io", "10.0.0.12"),
                new Request("udong.io", "10.0.0.12")
        );

        System.out.println(rateLimitFixedWindow(requests, 1, 2));
        System.out.println(rateLimitSlidingWindow(requests, 1, 5));
    }


    // Start 10:30
    // Finish 10:54 = 24min
    static List<Status> rateLimitFixedWindow(List<Request> requests, Integer limit, Integer window) {
        List<Status> results = new ArrayList<>();
        int currentWindowNumber = 0;
        Map<Request, Integer> contextWindow = new HashMap<>();

        for (Request request : requests) {
            if (contextWindow.containsKey(request) && contextWindow.get(request) >= limit) {
                results.add(Status.BLOCK);
            } else {
                results.add(Status.ALLOW);
            }

            Integer count = contextWindow.get(request);
            contextWindow.put(request, count != null ? count + 1 : 1);
            currentWindowNumber++;

//            System.out.println(i + " : " + contextWindow);

            if (currentWindowNumber == window) { // go forward after full
                contextWindow = new HashMap<>();
                currentWindowNumber = 0;
            }
        }

        return results;
    }

    // Start 10:54
    // Finish 11:20 = 36min
    static List<Status> rateLimitSlidingWindow(List<Request> requests, Integer limit, Integer window) {
        List<Status> results = new ArrayList<>();
        Map<Request, Integer> contextWindowRequestCount = new HashMap<>();
        Queue<Request> contextWindow = new ArrayDeque<>();

        for (Request request : requests) {
            if (contextWindowRequestCount.containsKey(request) && contextWindowRequestCount.get(request) >= limit) {
                results.add(Status.BLOCK);
            } else {
                results.add(Status.ALLOW);
            }

            Integer count = contextWindowRequestCount.get(request);
            contextWindowRequestCount.put(request, count != null ? count + 1 : 1);
            contextWindow.add(request);

//            System.out.println(i + " : " + contextWindow + " size : " + contextWindow.size());
//            System.out.println(i + " : " + contextWindowRequestCount);

            if (contextWindow.size() >= window) { // if full then slide (due to has no sliding interval so slide 1 after full)
                Request firstRequest = contextWindow.remove();
                contextWindowRequestCount.put(firstRequest, contextWindowRequestCount.get(firstRequest) - 1);
            }
        }

        return results;
    }
}

enum Status {
    ALLOW,
    BLOCK
}

record Request(String url, String srcIp) {
}
