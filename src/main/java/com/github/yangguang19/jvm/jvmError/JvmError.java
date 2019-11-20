package com.github.yangguang19.jvm.jvmError;

import java.util.ArrayList;

/**
 * @Description : TODO        测试JVM会抛出的2种错误
 * @Author :    yangguang
 * @Date :      2019/11/20
 */
public class JvmError {
    /**
     * 2种错误均是 VirtualMachineError 的子类
     *
     * StackOverflowError:
     * 当Java虚拟机栈无法动态扩容的时候,当前线程执行或请求的栈的大小超过了Java虚拟机栈的最大空间(比如递归嵌套调用太深),那么抛出StackOverflowError错误
     *
     * OutOfMemoryError:
     * 1: 当Java虚拟机栈允许动态扩容的时候,当前虚拟机栈执行请求的栈的大小仍然超过了扩容之后的最大空间,无法继续为栈分配空间(堆内存分配空间过小)
     *    那么抛出OutOfMemoryError错误
     * 2: Java堆存放对象实例,当需要为对象分配内存时,而堆空间大小已经达到最大值,无法为对象实例继续分配空间时,抛出 OutOfMemoryError错误
     */

    //测试 StackOverflowError 错误
    public static void stackOverflowError()
    {
        stackOverflowError();
    }

    //测试OOM错误
    public static void outOfMemoryError()
    {
        ArrayList<Object> list = new ArrayList<>();
        while (true)
        {
            list.add(new Object());
        }
    }

    public static void main(String[] args) {
//        stackOverflowError();

        //测试oom错误的时候,把堆的max size 设置的小如:-Xmx5m, 会快一点,不然电脑的内存足够大,没有限制,很难出现OutOfMemory
        outOfMemoryError();
    }
}
