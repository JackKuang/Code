package com.hurenjieee.code.concurrent;

import java.util.concurrent.*;

/**
 * @author jack
 * @date 19-1-15 下午10:14
 */
public class CallableTest {

    //step1:封装一个计算任务，实现Callable接口
    static class Task implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("task......." + Thread.currentThread().getName() + "...i = " + i);
                    //模拟耗时操作
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println(" is interrupted when calculating, will stop...");
                return false; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
            }
            return true;
        }
    }

    public static void main(String[] args) {

//        FutureTask + Thread

        //step2:创建计算任务，作为参数，传入FutureTask
        Task task = new Task();
        FutureTask futureTask = new FutureTask(task);
        //step3:将FutureTask提交给Thread执行
        Thread thread1 = new Thread(futureTask);
        thread1.setName("task thread 1");
        thread1.start();
        //step4:获取执行结果，由于get()方法可能会阻塞当前调用线程，如果子任务执行时间不确定，最好在子线程中获取执行结果
        try {
            // boolean result = (boolean) futureTask.get();
            boolean result = (boolean) futureTask.get(5, TimeUnit.SECONDS);
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            System.out.println("守护线程阻塞被打断...");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("执行任务时出错...");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("执行超时...");
            futureTask.cancel(true);
            e.printStackTrace();
        } catch (CancellationException e) {
            //如果线程已经cancel了，再执行get操作会抛出这个异常
            System.out.println("future已经cancel了...");
            e.printStackTrace();
        }

//        Future + ExecutorService
        //step1 ......
        //step2:创建计算任务
        Task task2 = new Task();
        //step3:创建线程池，将Callable类型的task提交给线程池执行，通过Future获取子任务的执行结果
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Future<Boolean> future = executorService.submit(task);
        //step4：通过future获取执行结果
        try {
            boolean result2 = (boolean) future.get();
            System.out.println("Result2" + result2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        FutureTask + ExecutorService

        //step1 ......
        //step2 ......

        Task task3 = new Task();
        FutureTask futureTask3 = new FutureTask(task);
        //step3:将FutureTask提交给线程池执行
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        executorService3.execute(futureTask3);
        try {
            boolean result3 = (boolean)futureTask3.get();
            System.out.println("Result3" + result3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //step4 ......



    }


}
