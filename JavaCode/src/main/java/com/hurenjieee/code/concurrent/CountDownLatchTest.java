package com.hurenjieee.code.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * 
 * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
 * 
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		
		final CountDownLatch latch = new CountDownLatch(2);

		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					//计数器减1
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");

					Integer i = new Random().nextInt(5000);
					Thread.sleep(i);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					//计数器减1
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		try {
			System.out.println("等待2个子线程执行完毕...");
			//调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
			latch.await();
			System.out.println("2个子线程已经执行完毕");
			System.out.println("继续执行主线程");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/**
		 *public void await() throws InterruptedException { };   //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
		 *public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
		 *public void countDown() { };  //将count值减1 
		 */
	}
}