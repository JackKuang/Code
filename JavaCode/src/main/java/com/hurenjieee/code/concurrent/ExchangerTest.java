package com.hurenjieee.code.concurrent;

/**
 * @author jack
 * @date 19-1-15 下午9:56
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 线程会阻塞在exchange方法上，直到另一线程调用
 */
public class ExchangerTest {

    public static void main(String[] args) {
        final Exchanger<String> exchanger =new Exchanger<>();
        new Thread(){
            @Override
            public void run() {
                String s = "thread1";
                try {
                    s = exchanger.exchange(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1 run :" + s);
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                String s = "thread2";
                try {
                    s = exchanger.exchange(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2 run :" + s);
            }
        }.start();

    }
}
