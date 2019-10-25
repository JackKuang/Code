package com.hurenjieee.code.lambda;

/**
 * @author Jack
 * @date 2019/6/9 21:52
 */
public class LambdaThread {

    /**
     * Lambda的线程创建
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1");
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> System.out.println("thread2"));
        thread2.start();

    }
}