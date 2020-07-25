package com.github.yangguang19.juc.lock.saleTickets;

/**
 * 模拟 多个卖票员同时卖30张票 最初始的版本
 */
// 资源类
class TicketByUnSafe {

    //30张门票
    private int count = 30;

    //售票方法  (对外暴露的调用方法)
    public void sale() {
        if (count > 0)
            System.out.println("当前线程: " + Thread.currentThread().getName() +
                    " 卖出第 " + (count--) + " 张票,还剩: " + count);
    }
}

/**
 * 多个卖票员同时卖30张票
 * 多线程编程的企业级套路 + 模板
 * 1.在搞内聚低耦合的前提下,  线程     操作(对外暴露的调用方法)    资源类
 */
public class SaleTicketsUnSafe {

    public static void main(String[] args) {
        // 1.创建资源类
        TicketByUnSafe ticket = new TicketByUnSafe();

        // 2.创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //3.线程操作资源类
                for (int i = 0; i < 40; ++i) {
                    ticket.sale();
                }
            }
        }, "A").start();
    }
}
