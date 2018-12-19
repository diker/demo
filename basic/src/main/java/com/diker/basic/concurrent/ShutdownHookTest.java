package com.diker.basic.concurrent;

/**
 *
 * 1、使用kill pic或者kill -15 pid 或ctrl+c结束进程钩子可以生效,但当采用kill -9 pic杀死进程时钩子不生效.
 * 2、钩子线程在程序关闭时时并发执行的。钩子运行的方法等待钩子线程都结束后才结束，见ApplicationShutdownHooks.runHooks方法
 *
 * @author diker
 * @since 2018/12/6
 */
public class ShutdownHookTest {
    public static void main(String[] args) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        runtime.addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("钩子方法已调用");
            }
        });
        System.out.println(runtime.availableProcessors());
        System.out.println(runtime.totalMemory()/1024/1024);
        System.out.println(runtime.maxMemory()/1024/1024);
        System.out.println(runtime.freeMemory()/1024/1024);

        Thread.sleep(10*1000L);
    }
}
