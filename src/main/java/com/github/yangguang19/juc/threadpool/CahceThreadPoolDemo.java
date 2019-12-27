package com.github.yangguang19.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @描述: 线程池
 */
public class CahceThreadPoolDemo {

    public static void main(String[] args) {
        //一池N个工作线程,类似一个银行有N个办理窗口
        ExecutorService threadpool = Executors.newCachedThreadPool();

        try {
            //模拟有10个客户过来办理银行业务,目前池子里面有1个工作人员提供服务
            for (int i = 1; i <= 10; i++) {
                try {
                    //模拟任务陆陆续续过来
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadpool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadpool.shutdown();
        }
    }
}
