package com.bizideal.mn.controller;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/23 9:55
 * @version: 1.0
 * @Description:
 */
public class ClassReordering {

    int x = 0, y = 0;

    public void writer() {
        x = 1;
        y = 2;
    }

    public void reader() {
//        int r1 = y;
        System.out.println("y = " + y);
        System.out.println("x = " + x);
//        int r2 = x;
    }

    public static void main(String[] args) {
        final ClassReordering classReordering = new ClassReordering();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                classReordering.writer();
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                classReordering.reader();
            }
        };
        t2.start();
        t1.start();

        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.set(1, "t");
        Object o = list.get(2);
    }

}
