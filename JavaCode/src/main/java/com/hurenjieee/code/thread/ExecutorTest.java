package com.hurenjieee.code.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorTest {
	public static void main(String[] args) {
		/**
		 * Executor是一个顶层接口，在它里面只声明了一个方法execute(Runnable)，返回值为void，参数为Runnable类型，
		 * 从字面意思可以理解，就是用来执行传进去的任务的；
		 * 然后ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、
		 * invokeAny以及shutDown等； 抽象类AbstractExecutorService实现了ExecutorService接口，
		 * 基本实现了ExecutorService中声明的所有方法；
		 * 然后ThreadPoolExecutor继承了类AbstractExecutorService。
		 * 
		 * workQueue的类型为BlockingQueue<Runnable>，通常可以取下面三种类型：
		 * 1）ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
		 * 2）LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.
		 * MAX_VALUE；
		 * 3）synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
		 * 
		 */
		//ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
		//new ArrayBlockingQueue<Runnable>(5));
		//Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
		//Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5); //创建固定容量大小的缓冲池
		for (int i = 0; i < 15; i++) {
			MyTask myTask = new MyTask(i);
			executor.execute(myTask);
			System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size()
					+ "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
		}
		executor.shutdown();
	}
}

class MyTask implements Runnable {
	private int taskNum;

	public MyTask(int num) {
		this.taskNum = num;
	}

	public void run() {
		System.out.println("正在执行task " + taskNum);
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task " + taskNum + "执行完毕");
	}
}