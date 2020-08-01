package com.github.yangguang19.juc.volatiles;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2.验证volatile不保证原子性
 * 2.1 原子性指的是什么
 * 不可分割、完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整，要么同时成功，要么同时失败
 * 2.2 如何解决原子性
 * 2.2.1 方法加synchronized
 * 2.2.2 Atomic
 */
public class VolatileDemo2 {

    public static void main(String[] args) {
        atomicByVolatile(); //验证volatile不保证原子性
    }

    /**
     * volatile不保证原子性
     * 以及使用Atomic保证原子性
     */
    public static void atomicByVolatile() {
        MyData2 myData = new MyData2();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addSelf();
                    myData.atomicAddSelf();
                }
            }, "Thread " + i).start();
        }
        //等待上面的线程都计算完成后，再用main线程取得最终结果值
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally num value is " + myData.num);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicnum value is " + myData.atomicInteger);
    }
}

class MyData2 {
    int num = 0;
//    volatile int num = 0;

    public synchronized void addToSixty() {
        this.num = 60;
    }

    public void addSelf() {
        num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void atomicAddSelf() {
        atomicInteger.getAndIncrement();
    }
}
