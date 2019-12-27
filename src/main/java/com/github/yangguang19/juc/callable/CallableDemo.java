package com.github.yangguang19.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description :  Callable 接口
 * @Author :    yangguang
 * @Date :      2019/11/19
 */

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable子线程");
        System.out.println("模拟线程阻塞4秒");
        //暂停一会儿线程
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}

/**
 * get 方法一般放在最后一行执行
 */
public class CallableDemo {

    public static void main(String[] args) throws Exception {

        //RunnableFuture接口实现了Runnable接口,代表它可以构造 Thread
        RunnableFuture task = new FutureTask(new MyCallable());

        new Thread(task, "A").start();
        new Thread(task, "B").start();

        System.out.println(Thread.currentThread().getName() + "****计算完成,等待子线程的结果");
        //如果在 FutureTask还没有计算出结果的时候就去get,那么会阻塞线程,直到线程结果返回
        System.out.println(task.get());
    }
}
