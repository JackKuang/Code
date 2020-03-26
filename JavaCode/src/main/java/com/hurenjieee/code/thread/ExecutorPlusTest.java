package com.hurenjieee.code.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author renjie.hu@guuidea.com
 * @date 2020/3/12 20:28
 */
public class ExecutorPlusTest {

    // 线程池工厂类
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
    /**
     * 1. 线程池核心线程数
     * - 核心线程会一直存活，及时没有任务需要执行
     * - 当线程数小于核心线程数时，即使有线程空闲，线程池也会优先创建新线程处理
     * - 设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
     * <p>
     * 2. 线程池最大数
     * - 当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
     * - 当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
     * <p>
     * 3. 空闲线程存活时间
     * <p>
     * 4. 空闲线程存活时间单位
     * <p>
     * 5. 线程池所使用的缓冲队列
     * - 当核心线程数达到最大时，新任务会放在队列中排队等待执行
     * - LinkedBlockingQueue 无须指定长度，放入和取出元素使用不同的锁，互不影响，效率高，通用性强
     * - ArrayBlockingQueue 必须指定长度，大了浪费内存，小了性能不高，使用同一把锁，效率低
     * <p>
     * 6. 线程池创建线程使用的工厂
     * <p>
     * 7. 线程池对拒绝任务的处理策略
     * - AbortPolicy 丢弃任务，抛运行时异常
     * - CallerRunsPolicy 由调用者执行任务
     * - DiscardPolicy 忽视，什么都不会发生
     * - DiscardOldestPolicy 从队列中踢出最先进入队列（最后一个执行）的任务
     * <p>
     * <p>
     * 线程池按以下行为执行任务
     * 1. 当线程数小于核心线程数时，创建线程。
     * 2. 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
     * 3. 当线程数大于等于核心线程数，且任务队列已满
     * - 若线程数小于最大线程数，创建线程
     * - 若线程数等于最大线程数，抛出异常，拒绝任务
     */
    private static ExecutorService service = new ThreadPoolExecutor(
            1,
            1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            namedThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

}
