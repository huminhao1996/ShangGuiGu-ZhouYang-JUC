package com.github.yangguang19.jvm.jvmError;

import java.lang.ref.Reference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : TODO
 * @Author :    yangguang
 * @Date :      2019/11/27
 */
public class OutOfMemoryErrorDemo {

    /**
     *
     * OutOfMemoryError:
     * 1: 当Java虚拟机栈允许动态扩容的时候,当前虚拟机栈执行请求的栈的大小仍然超过了扩容之后的最大空间,无法继续为栈分配空间(堆内存分配空间过小)
     *    那么抛出OutOfMemoryError错误
     * 2: Java堆存放对象实例,当需要为对象分配内存时,而堆空间大小已经达到最大值,无法为对象实例继续分配空间时,
     *      抛出 OutOfMemoryError错误
     *
     * 3: GC时间过长可能会抛出OutOfMemoryError.也大部分的时间都用在GC上了,并且每次回收都只回收
     *    一点内存,而清理的一点内存很快又被消耗殆尽,这样就恶性循环,不断长时间的GC,就可能抛出
     *    GC Overhead limit
     *
     * 4: 因为jvm内存依赖于本地物理内存,那么给程序分配超额的物理内存,而堆内存充足,
     *    那么GC就不会执行回收,DirectByteBuffer对象就不会被回收,如果继续分配
     *    直接物理内存,那么可能会出现DirectBufferMemoryError
     *
     * 5: 一个应用程序可以创建的线程数有限,如果创建的线程的数量达到相应平台的上限,
     *    那么可能会出现 unable to create new native thread 错误
     */

    //测试java heap space错误的时候,把堆的max size 设置的小如:-Xmx30m, 会快一点,不然电脑的内存足够大,没有限制,很难出现OutOfMemory
    public static void heapSpace()
    {
        byte[] bytes = new byte[1024 * 1024 * 30];
    }

    //测试第3中情况这里可能与jdk版本或垃圾收集器或Xmx分配的内存有关,我的机器始终没有出现GC overhead limit exceeded
    public static void overHeadLimitExceeded()
    {
        int i = 1;
        List<String> list = new ArrayList<>();
        try
        {
            while (true)
            {
                list.add(String.valueOf(i++).intern());
            }
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    //测试第4种情况
    public static void directBufferMemoryError()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 31);
    }

    //测试第5种情况
    public static void unableCreateNewNativeThread()
    {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        while (true)
        {
            System.out.println("当前线程 : " + atomicInteger.getAndIncrement());
            new Thread(()->{
                try
                {
                    //使当前线程停止,让GC慢点回收此线程,这样使线程停留快点
                    TimeUnit.SECONDS.sleep(5);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }).start();
        }

    }



    public static void main(String[] args) {

//        heapSpace();
//        overHeadLimitExceeded();
//        directBufferMemoryError();
//        unableCreateNewNativeThread();
    }
}
