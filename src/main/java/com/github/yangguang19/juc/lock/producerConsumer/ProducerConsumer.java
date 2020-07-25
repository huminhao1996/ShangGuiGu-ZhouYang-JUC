package com.github.yangguang19.juc.lock.producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO       生产者和消费者
 * 1.高内聚低耦合前提下,线程操作资源类
 * 2.判断/操作/通知
 * 3.多线程交互中,必须防止多线程的虚假唤醒,判断只能用while 不能用 if
 */
public class ProducerConsumer {
    //资源
    private volatile int number = 0;

    //增加
    public synchronized void increment() {
        try {
            //1.判断
            while (number > 0) {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2.操作
        ++number;

        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //3.通知
        this.notifyAll();
    }

    //减少
    public synchronized void decrement() {
        try {
            //1.判断
            while (number <= 0) {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2.操作
        --number;
        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //3.通知
        this.notifyAll();
    }

    public static void main(String[] args) {
        //1.资源类
        ProducerConsumer producerConsumer = new ProducerConsumer();
        //2.线程
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                //3.线程操作资源类
                producerConsumer.increment();
            }
        }, "A生产者").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.increment();
            }
        }, "B生产者").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.decrement();
            }
        }, "C消费者").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.decrement();
            }
        }, "D消费者").start();
    }

}

