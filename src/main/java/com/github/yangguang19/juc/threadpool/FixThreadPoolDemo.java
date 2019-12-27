package com.github.yangguang19.juc.threadpool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @描述: 线程池
 */
public class FixThreadPoolDemo {

    public static void main(String[] args) {

        //一池5个工作线程,类似一个银行有5个办理窗口
        ExecutorService threadpool = Executors.newFixedThreadPool(5);

        try {
            //模拟有10个客户过来办理银行业务,目前池子里面有5个工作人员提供服务
            for (int i = 1; i <= 10; i++) {
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
