package com.bizideal.mn.controller;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/22 15:18
 * @version: 1.0
 * @Description: 两个线程交替打印奇偶数的demo
 */
public class Test {

    private static int i = 1;

    public static void main(String[] args) throws InterruptedException {

        final Object obj = new Object();

        // 打印偶数
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (; i <= 100; ) {
                        if (i % 2 == 0) {
                            System.out.println(i++); // 不能使用在for循环中i++的方式。可能会造成两个线程都阻塞的情况。
                        } else {
                            obj.notify(); // 唤醒正在等待对象锁obj的线程，如果有多个，则随机唤起一个。notifyAll是唤起所有等待的线程
                            try {
                                obj.wait(); // wait方法会使当前线程进入等待状态。然后释放占有的对象锁obj
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        // 打印奇数
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (; i <= 100; ) {
                        if (i % 2 != 0) {
                            System.out.println(i++);
                        } else {
                            obj.notify();
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();

        t1.sleep(1000);
        t1.wait(1000);

        Thread.yield();

        // sleep和wait方法的区别
        // 1.sleep是Thread的方法，wait是Object的方法
        // 1.sleep可以使线程休眠指定的时间，但是不会释放占有的锁；wait方法在休眠的同时也会释放占有的锁

        // yield方法是停止当前线程，让同等优先级的线程运行，如果没有同等优先级的线程，那么yield()方法不会起作用
        // join()方法使当前线程停下来等待，直至另一个调用join方法的线程终止。
        // 线程的在被激活后不一定马上就运行，而是进入到可运行线程的队列中
    }
}


