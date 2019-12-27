package com.github.yangguang19.juc.lock.saleTickets;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO      模拟卖票员买票程序
 *                          多个卖票员同时卖30张票
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
class TicketByLock
{
    //30张票
    private volatile int count = 30;

    //基于Java API层面的可重入锁
    private Lock lock = new ReentrantLock();

    public void sale()
    {

        lock.lock();
        try
        {
            if(count > 0)
            {
                System.out.println("当前线程: " + Thread.currentThread().getName() + " 卖出第 " + (count--) + " 张票,还剩: " + count);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程编程的企业级套路 + 模板
 * 1.在搞内聚低耦合的前提下,  线程     操作(对外暴露的调用方法)    资源类
 */
public class SaleTicketsLock {
    public static void main(String[] args) {
        // 1. 创建资源类
        TicketByLock ticket = new TicketByLock();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"A").start();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"B").start();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"C").start();
    }
}
