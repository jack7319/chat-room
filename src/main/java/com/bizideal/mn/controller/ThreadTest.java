package com.bizideal.mn.controller;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/22 15:48
 * @version: 1.0
 * @Description:
 */
public class ThreadTest {
    private static Object LOCK = new Object();
    private static int i = 0;

    public static void main(String[] args) {

        Thread thread1 = new Thread() {
            public void run() {
                while (i <= 10) {
                    synchronized (LOCK) {
                        if (i % 2 == 0) {
                            System.out.println("Thread1: " + i++);
                        } else {
                            LOCK.notifyAll();
                            try {
                                LOCK.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                while (i <= 10) {
                    synchronized (LOCK) {
                        if (i % 2 != 0) {
                            System.out.println("Thread2: " + i++);
                        } else {
                            LOCK.notifyAll();
                            try {
                                LOCK.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
