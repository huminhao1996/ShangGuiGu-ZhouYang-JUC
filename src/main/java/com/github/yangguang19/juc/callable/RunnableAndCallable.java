package com.github.yangguang19.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @Description : Runnable接口 和 Callable 接口 区别
 * @Author :    yangguang
 * @Date :      2019/11/19
 */
public class RunnableAndCallable {

    class MyThread implements Runnable {
        @Override
        public void run() {

        }
    }

    //    新类MyThread2实现callable接口
    class MyThread2 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return 200;
        }
    }
}
