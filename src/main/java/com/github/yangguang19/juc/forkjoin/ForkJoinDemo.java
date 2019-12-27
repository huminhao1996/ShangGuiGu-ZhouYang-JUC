package com.github.yangguang19.juc.forkjoin;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * @Description : TODO          forkjoin 分割任务并行计算,最后合并
 * @Author :    yangguang
 * @Date :      2019/11/22
 */
public class ForkJoinDemo {

    static class MyTask extends RecursiveTask<Integer> {
        //最小单位
        private static final int MIN_VALUE = 10;

        private int result;
        private int begin;
        private int end;

        public MyTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }


        @Override
        protected Integer compute() {
            //小于最小单位的 直接计算,不用使用ForkJoin
            if (end - begin <= MIN_VALUE) {
                for (int i = begin; i <= end; ++i) {
                    result += i;
                }
            } else {
                //找中间点
                int middle = (begin + end) / 2;
                //拆分任务
                MyTask forkJoinTask1 = new MyTask(begin, middle);
                MyTask forkJoinTask2 = new MyTask(middle + 1, end);
                forkJoinTask1.fork();
                forkJoinTask2.fork();
                //将任务届
                result = forkJoinTask1.join() + forkJoinTask2.join();
            }
            return result;
        }
    }

    public static void main(String[] args) throws Exception {
        //资源类
        MyTask myTask = new MyTask(0, 100);
        //线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //递交任务
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        //获取返回结果
        Integer result = forkJoinTask.get();
        System.out.println(result);

        forkJoinPool.shutdown();
    }
}
