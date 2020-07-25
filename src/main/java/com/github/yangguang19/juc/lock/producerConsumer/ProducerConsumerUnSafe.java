package com.github.yangguang19.juc.lock.producerConsumer;

/**
 * 资源类
 */
class Ticket {
    private int number=0;

    public synchronized void increment() throws InterruptedException {
        // TODO: 2020/7/22 此处 if判断存在线程安全问题
        if (number!=0){
            this.wait();    // TODO: 前面if已经判断过了,所以线程在wait()后被唤醒,所以会直接走下面的程序,而没有再次进行判断,
                            // TODO: 要知道多线程下,资源类的状态时时刻刻在改变,即使你在当时获取了操作资源类的权利
                            //  在wait()阻塞后,资源类的状态极有可能不同,所以此处必须得循环判断 操作资源的条件
        }
        //干活
        number++;
        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //通知其他线程
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        // TODO: 2020/7/22 此处 if判断存在线程安全
        if (number==0){
            this.wait();
        }
        //干活
        number--;
        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //通知其他线程
        this.notifyAll();
    }
}


public class ProducerConsumerUnSafe {

    public static void main(String[] args) {
        //1.资源类
        Ticket producerConsumer = new Ticket();
        //2.线程
        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                //3.线程操作资源类
                try {
                    producerConsumer.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A生产者").start();

        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                //3.线程操作资源类
                try {
                    producerConsumer.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B生产者").start();

        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                try {
                    producerConsumer.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C消费者").start();

        new Thread(() -> {
            for (int i = 0; i < 20; ++i) {
                try {
                    producerConsumer.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D消费者").start();
    }
}
