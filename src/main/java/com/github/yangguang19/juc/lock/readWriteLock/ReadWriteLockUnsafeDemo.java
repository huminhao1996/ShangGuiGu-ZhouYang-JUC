package com.github.yangguang19.juc.lock.readWriteLock;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockUnsafeDemo {
    // TODO: 2020/7/25  模拟多线程对公共资源类的读和写操作,没有加锁,不安全
    static class Cache {
        private HashMap<String, Object> cache = new HashMap<>();

        //写入缓存
        public void put(String key, Object val) {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始写入");
                cache.put(key, val);
                System.out.println(Thread.currentThread().getName() + " 写入完成");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

        //从缓存中读取数据
        public void get(String key) {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始读取");
                Object obj = cache.get(key);
                System.out.println(Thread.currentThread().getName() + " 读取完成 : " + obj);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public static void main(String[] args) {
        Cache cache = new Cache();
        for (int i = 0; i < 5; ++i) {
            final int tempI = i;
            new Thread(() -> {
                cache.put(String.valueOf(tempI), tempI);
            }).start();
        }

        for (int i = 0; i < 5; ++i) {
            final int tempI = i;
            new Thread(() -> {
                cache.get(String.valueOf(tempI));
            }).start();
        }
    }
}
