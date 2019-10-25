package com.hurenjieee.code.thread;

public class ThreadTest {
    
    
    public static void main(String[] args){
        System.out.println(Thread.currentThread().getName()+ "主线程运行开始!"); 
        testRunnable();
        System.out.println(Thread.currentThread().getName()+ "主线程运行结束!"); 
    }
    
    public static void testThread(){
        Thread1 thread1 = new Thread1("Thead1");
        Thread1 thread2 = new Thread1("Thead2");
        thread1.start();
        thread2.start();
    }
    
    public static void testRunnable(){
        Thread thread1 = new Thread(new Thread2("Thead1"));
        Thread thread2 = new Thread(new Thread2("Thead2"));
        thread1.start();
        thread2.start();
        try {
        	thread1.setPriority(Thread.MAX_PRIORITY);
        	thread2.setPriority(Thread.MIN_PRIORITY);
        	//thread1.yield();
            //join是Thread类的一个方法，启动线程后直接调用，即join()的作用是：“等待该线程终止”，
            //这里需要理解的就是该线程是指的主线程等待子线程的终止。也就是在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行。
        	thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Thread.interrupted();
    }
    
    /*
     * 实现Runnable接口比继承Thread类所具有的优势：
     * 1）：适合多个相同的程序代码的线程去处理同一个资源
     * 2）：可以避免java中的单继承的限制
     * 3）：增加程序的健壮性，代码可以被多个线程共享，代码和数据独立
     * 4）：线程池只能放入实现Runable或callable类线程，不能直接放入继承Thread的类
     * 
     * 【线程状态转换】
     * （见线程状态转换.jpg）
     * 1、新建状态（New）：新创建了一个线程对象。
     * 2、就绪状态（Runnable）：线程对象创建后，其他线程调用了该对象的start()方法。该状态的线程位于可运行线程池中，变得可运行，等待获取CPU的使用权。
     * 3、运行状态（Running）：就绪状态的线程获取了CPU，执行程序代码。
     * 4、阻塞状态（Blocked）：阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：
     * （一）、等待阻塞：运行的线程执行wait()方法，JVM会把该线程放入等待池中。(wait会释放持有的锁)
     * （二）、同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
     * （三）、其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。（注意,sleep是不会释放持有的锁）
     * 5、死亡状态（Dead）：线程执行完了或者因异常退出了run()方法，该线程结束生命周期。
     * 
     * 【线程调度】
     * 
     * 1、调整线程优先级：Java线程有优先级，优先级高的线程会获得较多的运行机会。
     * Java线程的优先级用整数表示，取值范围是1~10，Thread类有以下三个静态常量：
     * static int MAX_PRIORITY 线程可以具有的最高优先级，取值为10。
     * static int MIN_PRIORITY 线程可以具有的最低优先级，取值为1。
     * static int NORM_PRIORITY 分配给线程的默认优先级，取值为5。
     * Thread类的setPriority()和getPriority()方法分别用来设置和获取线程的优先级。
     * 每个线程都有默认的优先级。主线程的默认优先级为Thread.NORM_PRIORITY。
     * 线程的优先级有继承关系，比如A线程中创建了B线程，那么B将和A具有相同的优先级。
     * JVM提供了10个线程优先级，但与常见的操作系统都不能很好的映射。
     * 】如果希望程序能移植到各个操作系统中，应该仅仅使用Thread类有以下三个静态常量作为优先级，这样能保证同样的优先级采用了同样的调度方式
     * 
     * 2、线程睡眠：Thread.sleep(long millis)方法，使线程转到阻塞状态。millis参数设定睡眠的时间，以毫秒为单位。当睡眠结束后，就转为就绪（Runnable）状态。sleep()平台移植性好。
     * 
     * 3、线程等待：Object类中的wait()方法，导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 唤醒方法。
     * 这个两个唤醒方法也是Object类中的方法，行为等价于调用 wait(0) 一样。
     * 
     * 4、线程让步：Thread.yield() 方法，暂停当前正在执行的线程对象，把执行机会让给相同或者更高优先级的线程。 
     *  
     * 5、线程加入：join()方法，等待其他线程终止。在当前线程中调用另一个线程的join()方法，则当前线程转入阻塞状态，直到另一个进程运行结束，当前线程再由阻塞转为就绪状态。
     * 
     * 6、线程唤醒：Object类中的notify()方法，唤醒在此对象监视器上等待的单个线程。
     * 如果所有线程都在此对象上等待，则会选择唤醒其中一个线程。
     * 选择是任意性的，并在对实现做出决定时发生。线程通过调用其中一个 wait 方法，在对象的监视器上等待。
     * 直到当前的线程放弃此对象上的锁定，才能继续执行被唤醒的线程。被唤醒的线程将以常规方式与在该对象上主动同步的其他所有线程进行竞争；
     * 例如，唤醒的线程在作为锁定此对象的下一个线程方面没有可靠的特权或劣势。
     * 类似的方法还有一个notifyAll()，唤醒在此对象监视器上等待的所有线程。
     * 
     * 
     * 
     * sleep()和yield()的区别):sleep()使当前线程进入停滞状态，
     * 所以执行sleep()的线程在指定的时间内肯定不会被执行；
     * yield()只是使当前线程重新回到可执行状态，
     * 所以执行yield()的线程有可能在进入到可执行状态后马上又被执行。
     * sleep 方法使当前运行中的线程睡眼一段时间，进入不可运行状态，
     * 这段时间的长短是由程序设定的，yield 方法使当前线程让出 CPU 占有权，但让出的时间是不可设定的。实际上，yield()方法对应了如下操作：
     * 先检测当前是否有相同优先级的线程处于同可运行状态，如有，则把 CPU  的占有权交给此线程，否则，继续运行原来的线程。
     * 所以yield()方法称为“退让”，它把运行机会让给了同等优先级的其他线程
     *
     *
     */
    
    /**
     * 
	   sleep(): 强迫一个线程睡眠Ｎ毫秒。 
	　　isAlive(): 判断一个线程是否存活。 
	　　join(): 等待线程终止。 
	　　activeCount(): 程序中活跃的线程数。 
	　　enumerate(): 枚举程序中的线程。 
	   currentThread(): 得到当前线程。 
	　　isDaemon(): 一个线程是否为守护线程。 
	　　setDaemon(): 设置一个线程为守护线程。(用户线程和守护线程的区别在于，是否等待主线程依赖于主线程结束而结束) 
	　　setName(): 为线程设置一个名称。 
	　　wait(): 强迫一个线程等待。 
	　　notify(): 通知一个线程继续运行。 
	　　setPriority(): 设置一个线程的优先级。
     * 
     */
    
    
    

}
