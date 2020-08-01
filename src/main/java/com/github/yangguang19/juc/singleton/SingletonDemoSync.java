package com.github.yangguang19.juc.singleton;

/**
 * 最初始的版本
 * 单线程下的单例模式代码
 */
public class SingletonDemoSync {

    private static SingletonDemoSync singletonDemo = null;

    private SingletonDemoSync() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo");
    }

    public static synchronized SingletonDemoSync getInstance() {
        if (singletonDemo == null) {
            singletonDemo = new SingletonDemoSync();
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        /** 单机版 只有一个线程调用 这里的 == 是比较内存地址 */
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        /** 多线程下的单例模式并不"单例" */
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemoSync.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
