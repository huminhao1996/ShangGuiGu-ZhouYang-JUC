package com.github.yangguang19.juc.lock.saleTickets;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO      模拟卖票员买票程序
 * 多个卖票员同时卖30张票
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
class Ticket    //资源类
{
    //30张票
    private volatile int count = 30;

    public synchronized void sale() {
        if (count > 0)
            System.out.println("当前线程: " + Thread.currentThread().getName() + " 卖出第 " + (count--) + " 张票,还剩: " + count);
    }
}

/**
 * 多线程编程的企业级套路 + 模板
 * 1.在搞内聚低耦合的前提下,  线程     操作(对外暴露的调用方法)    资源类
 */
public class SaleTickets {
    public static void main(String[] args) {
        // 1. 创建资源类
        Ticket ticket = new Ticket();
        // 2.创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; ++i) {
                    //3.线程操作资源类
                    ticket.sale();
                }
            }
        },"A线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; ++i) {
                    //3.线程操作资源类
                    ticket.sale();
                }
            }
        },"B线程").start();
    }
}
