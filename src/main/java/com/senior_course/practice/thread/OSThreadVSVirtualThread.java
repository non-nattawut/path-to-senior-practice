package com.senior_course.practice.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadOldVsNewWay {
    static void main() {
        try {
            oldWay();
            newWay();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void oldWay() {
        Thread.ofPlatform().start(() ->
                log.info("OS Threading")
        );
    }

    public static void newWay() throws InterruptedException {
        Thread vThread = Thread.ofVirtual().start(() ->
                log.info("Virtual Threading")
        );

        // tell main thread to wait this thread to finish before continue
        vThread.join();
    }
}
