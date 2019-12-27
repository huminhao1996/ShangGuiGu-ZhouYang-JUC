package com.github.yangguang19.juc.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO          juc之 condition
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
class ShareResource {   //资源类
    /**
     * 使用 Lock 和 Condition ,使3个线程顺序打印公共资源
     */
    private volatile int num = 0;

    //标志位,控制什么时候3个线程打印, flag == 1 , 第一个线程打印 , flag == 2 , 第二个线程打印, flag==3,第三个线程打印
    private volatile int flag = 1;

    private Lock lock = new ReentrantLock();
    //三个线程的condition
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printByFirst() {
        lock.lock();
        try {
            //1.判断
            while (flag != 1) {
                condition1.await();
            }
            //2.操作
            ++num;
            for (int i = 0; i < 5; ++i) {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }

            //3.通知
            //更新标志位,让第二个线程打印
            flag = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printBySecond() {
        lock.lock();
        try {
            while (flag != 2) {
                condition2.await();
            }
            ++num;
            for (int i = 0; i < 5; ++i) {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }
            flag = 3;

            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printByThird() {
        lock.lock();
        try {
            while (flag != 3) {
                condition3.await();
            }
            ++num;
            for (int i = 0; i < 5; ++i) {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }
            flag = 1;

            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程直线顺序调用, 实现 A -> B -> C
 * 三个线程启动,要求如下:
 * 第一个线程打印5次 第二个线程打印10次 ,第三个线程打印15次.. 以此循环
 * 1.高内聚低耦合前提下,线程操作资源类
 * 2.判断/操作/通知
 * 3.多线程交互中,必须防止多线程的虚假唤醒,判断只能用while 不能用 if
 * 4.标志位
 */
public class ConditionDemo {
    public static void main(String[] args) {
        ShareResource conditionDemo = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                conditionDemo.printByFirst();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                conditionDemo.printBySecond();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                conditionDemo.printByThird();
            }
        }, "C").start();
    }
}
