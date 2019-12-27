package com.github.yangguang19.juc.threadpool;

import java.util.concurrent.*;

/**
 * 自定义线程池,主要学习 拒绝策略
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        //10个顾客请求
        try {
            for (int i = 1; i <= 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +
                            "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}

