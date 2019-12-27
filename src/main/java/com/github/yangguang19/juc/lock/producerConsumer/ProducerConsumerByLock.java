package com.github.yangguang19.juc.lock.producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO       生产者和消费者
 * @Author :    yangguang
 * @Date :      2019/11/18
 */


public class ProducerConsumerByLock {
    //资源
    private volatile int number = 0;

    /**
     * 下面生产者和消费者使用JAVA API 层面的 Lock 实现
     */
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment2() {

        lock.lock();
        try {
            //1.判断
            while (number > 0) {
                condition.await();
            }
            //2.操作
            ++number;
            System.out.println(Thread.currentThread().getName() + " --> " + number);
            //3.通知
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement2() {
        lock.lock();
        try {
            while (number <= 0) {
                condition.await();
            }
            --number;
            System.out.println(Thread.currentThread().getName() + " --> " + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        /**
         * 模拟多个线程对number ++ 或--
         * 多轮操作后,number依然为 0
         */
        ProducerConsumerByLock producerConsumer = new ProducerConsumerByLock();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.increment2();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.increment2();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.decrement2();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                producerConsumer.decrement2();
            }
        }, "D").start();
    }



}

