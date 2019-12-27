package com.github.yangguang19.juc.collections;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 1.   ArrayList线程不安全的demo,
 *      大概率可能会抛出 ConcurrentModificationException 异常
 *      异常是同时对集合进行修改时会抛出的异常
 * 2. 导致原因
 *
 * 3. 解决方案
 *      3.1 Vector  (保证安全,效率低)
 *      3.2 Collections.synchronizedList(new ArrayList<>()) (保证安全,效率低)
 *      3.3 CopyOnWriteArrayList
 *
 * 4. 优化建议(同样的错误,不出现第2次)
 */
public class ListNotSafeDemo {
    //ArrayList线程不安全
    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>();
        Runnable run = () -> {
            list.add(5);
            System.out.println(list);
        };

        //30个线程对list进行修改和读取操作
        for (int i = 0; i < 30; ++i)
            new Thread(run).start();
    }
}
