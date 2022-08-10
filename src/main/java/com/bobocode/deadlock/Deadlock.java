package com.bobocode.deadlock;

import lombok.SneakyThrows;

public class Deadlock {

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        var thread_1 = new Thread(Deadlock::run_first_lock);
        var thread_2 = new Thread(Deadlock::run_second_lock);
        var thread_3 = new Thread(() -> log_thread_status(thread_1, thread_2));

        thread_1.start();
        thread_2.start();
        thread_3.start();

    }

    @SneakyThrows
    private static void run_first_lock() {
        synchronized (LOCK1) {
            System.out.println("Lock 1 acquired by thread 1");
            Thread.sleep(1000);
            synchronized (LOCK2) {
                System.out.println("Lock 2 acquired by thread 1");
            }
            System.out.println("thread 1 executions finished");
        }
    }

    @SneakyThrows
    private static void run_second_lock() {
        synchronized (LOCK2) {
            System.out.println("Lock 2 acquired by thread 2");
            Thread.sleep(1000);
            synchronized (LOCK1) {
                System.out.println("Lock 1 acquired by thread 2");
            }

        }
        System.out.println("thread 2 executions finished");
    }

    @SneakyThrows
    private static void log_thread_status(Thread t1, Thread t2) {
        while (true) {
            System.out.println("Thread 1 state: " + t1.getState());
            System.out.println("Thread 2 state: " + t2.getState());
            Thread.sleep(5000);
        }
    }
}
