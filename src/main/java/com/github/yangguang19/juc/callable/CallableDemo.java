package com.github.yangguang19.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @Description : TODO          Callable 接口
 * @Author :    yangguang
 * @Date :      2019/11/19
 */
public class CallableDemo {
    /**
     *
     * 以前总以为 Callable 只能用于线程池
     *
     * 但是后来学习到 Callable 也可以用于Thread
     *
     */



    public static void main(String[] args) throws Exception
    {
        Callable<String> call = ()->{
            System.out.println("Hello Callable");
            return "Java";
        };

        //RunnableFuture接口实现了Runnable接口,代表它可以构造 Thread
        RunnableFuture<String> task = new FutureTask<>(call);



        new Thread(task,"A").start();

        System.out.println(task.get());
    }
}
