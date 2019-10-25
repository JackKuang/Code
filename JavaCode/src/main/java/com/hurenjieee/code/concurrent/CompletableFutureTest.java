package com.hurenjieee.code.concurrent;

import java.util.concurrent.*;

/**
 * @author Jack
 * @date 2019/3/8 11:35
 */
public class CompletableFutureTest {
    /**
     * Future接口，负责对线程的控制以及查询。轮询isDone()显然不合适，get()会形成线程堵塞
     * CompletionStage接口，表示完成阶段，运行阶段结束后之要做的东西
     */


    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        thenApply();
        thenAccept();
        thenRun();
        whenComplete();
    }

    public static void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApplyAsync(s -> s + " world,for thenApply").join();
        System.out.println(result);
    }

    public static void thenAccept() {
        System.out.println("--------------1");
        CompletableFuture.supplyAsync(() -> "hello", executorService).thenAcceptAsync(s -> System.out.println(s + " world,for thenAccept.executorService"), executorService);
        System.out.println("--------------2");
        CompletableFuture.supplyAsync(() -> "hello").thenAcceptAsync(s -> System.out.println(s + " world,for thenAccept"));
        System.out.println("--------------3");
    }

    public static void thenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
    }

    public static void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }

    public void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
        while (true){}
    }

    /**
     * thenCombine 组合
     * thenAcceptBoth 两者同时完成
     * applyToEither 取两者之一 先完成 （有结果）
     * acceptEither 取两者之一 先完成 （无结果）runAfterEither 任意一个完成 先完成 （无结果）
     * exceptionally
     */

    /**
     * 异常处理
     */
    public static void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println("【whenComplete】");
            System.out.println(s);
            System.out.println(t.getMessage());
        }).handle((s, t) -> {
            System.out.println("【handle】");
            if (t != null) {
                return "handle";
            }
            return s;
        }).exceptionally(e -> {
            System.out.println("【exceptionally】");
            System.out.println(e.getMessage());
            return "exceptionally";
        }).join();
        // 出现handle处理掉异常之后，exceptionally就抓不到异常了。
        System.out.println(result);
    }
}
