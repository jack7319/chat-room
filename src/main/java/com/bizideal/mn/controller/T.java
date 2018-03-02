package com.bizideal.mn.controller;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/22 13:54
 * @version: 1.0
 * @Description:
 */
public class T {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 23");
            }
        };
        Thread thread1 = new Thread(runnable, "thread1");
        Thread thread2 = new Thread(runnable, "thread2");
        thread1.setPriority(10);
        System.out.println(thread1.getState());
        thread1.start();
        System.out.println(thread1.getState());
        thread2.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(thread1.getState());
        thread1.start();

        Thread thread = thread1.currentThread();
        thread.wait(0);


        Vector<Object> objects = new Vector<>();
        objects.add(1);
        objects.remove(1);
        objects.size();
        thread1.interrupt();
        boolean interrupted = thread1.isInterrupted();

        String s = "1221";
        s.substring(1);
    }
}

